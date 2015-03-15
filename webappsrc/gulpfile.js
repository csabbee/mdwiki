'use strict';

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')({
    pattern: ['gulp-*']
});

require('require-dir')('./gulp');

var paths = {
    mainFolder: './app',
    appJs: 'app/**/*.js',
    appCss: 'app/**/{*.css, *.css.map}',
    appHtml: 'app/**/*.html',
    buildJs: './build.js*',
    distJs: '../src/main/webapp/resources/js',
    distCss: '../src/main/webapp/resources/css',
    jsp: '../src/main/webapp/WEB-INF/views',
    ignorePath: '../src/main/webapp'
};

var htmlTemplateCacheOptions = {
        standalone: true,
        module: 'htmlTemplates'
    };

var exec = require('child_process').exec;
var templateCache = require('gulp-angular-templatecache');

function handleCallback(err, stdout) {
    if(err) {
        console.error(err);
    }
    if(stdout){
        console.log(stdout);
        gulp.start('copyCss');
    }
}

gulp.task('jspm', function () {
    return exec('jspm bundle-sfx app/init '+paths.distJs+'/mdwiki.js', handleCallback);
});

gulp.task('cleanJs', function () {
    return gulp.src([paths.distJs+'/**/*.js*', paths.buildJs], {read: false})
        .pipe(plugins.clean({force: true}));
});
gulp.task('cleanCss', function () {
    return gulp.src([paths.distCss+'/**/*.css'], {read: false})
        .pipe(plugins.clean({force: true}));
});

gulp.task('copyCss', function () {
    return gulp.src(paths.appCss)
        .pipe(gulp.dest(paths.distCss));
});

gulp.task('indexCss', function () {
    var target = gulp.src(paths.jsp+'/**/header.jsp');
    var sourcesCss = gulp.src(paths.distCss+'/**/*.css', {read: false});

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
    var sourcesJs = gulp.src(paths.distJs+'/**/*.js', {read: false});

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

gulp.task('htmlcache', function () {
    return gulp.src(paths.appHtml)
        .pipe(plugins.minifyHtml({
            empty: true,
            spare: true,
            quotes: true
        }))
        .pipe(templateCache('htmltemplates.js', htmlTemplateCacheOptions))
        .pipe(plugins.wrap('export function caching() { <%= contents %> }'))
        .pipe(gulp.dest(paths.mainFolder));
});

gulp.task('watch', function () {
    gulp.watch('./bower.json', ['bower']);

    gulp.watch(paths.distJs+'/**/*.js', ['indexJs']);
    gulp.watch(paths.distCss+'/**/*.css', ['indexCss']);

    gulp.watch(paths.appHtml, ['htmlcache']);

    gulp.watch(paths.appJs, ['jspm']);
    gulp.watch(paths.appCss, ['cleanCss', 'copyCss']);
});
