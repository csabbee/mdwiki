'use strict';

var gulp = require('gulp');
var exec = require('child_process').exec;

var plugins = require('gulp-load-plugins')({
    pattern: ['gulp-*']
});

var paths = {
    dist_Js: '../src/main/webapp/resources/js'
};

function handleCallback(err, stdout) {
    if(err) {
        console.error(err);
    }
    if(stdout){
        console.log(stdout);
        gulp.start('indexJs');
    }
}

gulp.task('build', function () {
    return exec('jspm bundle-sfx app/init ' + paths.dist_Js+'/build.js --minify', handleCallback);
});
