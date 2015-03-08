'use strict';

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({
    pattern: ['gulp-*']
});

require('require-dir')('./gulp');

var paths = {
    appJs: 'app/**/*.js',
    appCss: 'app/**/*.css',
    buildJs: './build.js*',
    dist_Js: '../src/main/webapp/resources/js/app',
    dist_Css: '../src/main/webapp/resources/css/app',
    jsp: '../src/main/webapp/WEB-INF/views',
    ignorePath: '../src/main/webapp'
};

var exec = require('child_process').exec;


gulp.task('jspm', function () {
    exec('jspm bundle-sfx app/init', function(error, stdout, stderr) {
        console.log('stdout: ' + stdout);
        if (error !== null) {
            console.log('exec error: ' + error);
        }
    });
});

gulp.task('cleanJs', function () {
    return gulp.src([paths.dist_Js+'/**/*.js'], {read: false})
        .pipe(clean({force: true}));
});
gulp.task('cleanCss', function () {
    return gulp.src([paths.dist_Css+'/**/*.css'], {read: false})
        .pipe(clean({force: true}));
});

gulp.task('copyJs', function () {
    return gulp.src(paths.buildJs)
        .pipe(gulp.dest(paths.dist_Js));
});
gulp.task('copyCss', function () {
    return gulp.src(paths.appCss)
        .pipe(gulp.dest(paths.dist_Css));
});

gulp.task('indexCss', function () {
    var target = gulp.src(paths.jsp+'/**/header.jsp');
    var sourcesCss = gulp.src(paths.dist_Css+'/**/*.css', {read: false});

    return target
        .pipe(plugins.inject(sourcesCss, {
            ignorePath: paths.ignorePath,
            starttag: '<!-- myInject:css -->',
            endtag: '<!-- endMyInject -->',
            transform: function (filepath) {
                return '<link rel="stylesheet" href="<c:url value=\"' + filepath + '\"/>"/>';
            }
        }))
        .pipe(gulp.dest(paths.jsp));
});

gulp.task('indexJs', function () {
    var target = gulp.src(paths.jsp+'/**/*.jsp');
    var sourcesJs = gulp.src(paths.dist_Js+'/**/*.js', {read: false});

    return target
        .pipe(plugins.inject(sourcesJs, {
            ignorePath: paths.ignorePath,
            starttag: '<!-- myInject:js -->',
            endtag: '<!-- endMyInject -->',
            transform: function (filepath) {
                return '<script language="JavaScript" src="<c:url value=\"' + filepath + '\"/>"></script>';
            }
        }))
        .pipe(gulp.dest(paths.jsp));
});

gulp.task('watch', function () {
    gulp.watch('./bower.json', ['bower']);

    gulp.watch(paths.dist_Js+'/**/*.js', ['indexJs']);
    gulp.watch(paths.dist_Css+'/**/*.css', ['indexCss']);

    gulp.watch(paths.buildJs, ['cleanJs', 'copyJs']);

    gulp.watch(paths.appJs, ['jspm']);
    gulp.watch(paths.appCss, ['cleanCss', 'copyCss']);
});
