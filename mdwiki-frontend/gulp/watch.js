var gulp = require('gulp'),
    paths = require('./paths'),
    browserSync = require('browser-sync');

function reportChange(event){
    console.log('File ' + event.path + ' was ' + event.type + ', running tasks...');
}

gulp.task('watch', function() {

    gulp.watch(paths.source, ['build-js', browserSync.reload]).on('change', reportChange);
    gulp.watch([paths.html, './index.html'], ['build-html', browserSync.reload]).on('change', reportChange);
});