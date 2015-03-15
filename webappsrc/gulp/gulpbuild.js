'use strict';

var gulp = require('gulp');
var exec = require('child_process').exec;

var plugins = require('gulp-load-plugins')({
    pattern: ['gulp-*']
});

var paths = {
    distJs: '../src/main/webapp/resources/js'
};

function handleCallback(err, stdout) {
    if(err) {
        console.error(err);
    }
    if(stdout) {
        console.log(stdout);
        gulp.start('indexJs');
        gulp.start('indexCss');
    }
}

gulp.task('build', ['htmlcache', 'copyCss'], function () {
    return exec('jspm bundle-sfx app/init ' + paths.distJs+'/mdwiki.js --minify --skip-source-maps', handleCallback);
});
