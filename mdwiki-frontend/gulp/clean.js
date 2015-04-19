var gulp = require('gulp'),
    paths = require('./paths'),
    del = require('del'),
    vinylPaths = require('vinyl-paths');

gulp.task('clean', function() {
    return gulp.src([paths.output])
        .pipe(vinylPaths(del));
});