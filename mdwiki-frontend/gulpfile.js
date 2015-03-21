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
    distDir: './dist',
    buildDir: '../mdwiki-backend/src/main/webapp/resources'
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
    return exec('jspm bundle-sfx app/init '+paths.buildDir+'/scripts/mdwiki.js', handleCallback);
});

gulp.task('cleanJs', function () {
    return gulp.src([paths.distDir+'/**/*.js*'], {read: false})
        .pipe(plugins.clean({force: true}));
});
gulp.task('cleanCss', function () {
    return gulp.src([paths.distDir+'/**/*.css'], {read: false})
        .pipe(plugins.clean({force: true}));
});

gulp.task('copyCss', function () {
    return gulp.src(paths.appCss)
        .pipe(gulp.dest(paths.buildDir));
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

    gulp.watch(paths.appHtml, ['htmlcache']);

    gulp.watch(paths.appJs, ['jspm']);
    gulp.watch(paths.appCss, ['cleanCss', 'copyCss']);
});
