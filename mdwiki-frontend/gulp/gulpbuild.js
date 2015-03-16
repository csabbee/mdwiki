'use strict';

var gulp = require('gulp');
var exec = require('child_process').exec;

var plugins = require('gulp-load-plugins')({
    pattern: ['gulp-*']
});

var paths = {
    distDir: './dist'
};

function handleCallback(err, stdout) {
    if(err) {
        console.error(err);
    }
    if(stdout) {
        console.log(stdout);
    }
}

gulp.task('build', ['htmlcache', 'copyCss'], function () {
    return exec('jspm bundle-sfx app/init ' + paths.distDir+'/scripts/mdwiki.js --minify --skip-source-maps', handleCallback);
});
