require('require-dir')('gulp');

var gulp = require('gulp'),
    runSequence = require('run-sequence');

gulp.task('default', function() {
    return runSequence(
        'build',
        'serve',
        'watch'
    )
});