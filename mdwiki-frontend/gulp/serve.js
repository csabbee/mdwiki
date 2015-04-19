var gulp = require('gulp'),
    browserSync = require('browser-sync'),
    paths = require('./paths');

var host = 'localhost',
    port = 8000;

gulp.task('serve', function(done) {
    browserSync({
        open: true,
        port: port,
        server: {
            baseDir: ['.'],
            middleware: function (req, res, next) {
                res.setHeader('Access-Control-Allow-Origin', '*');
                next();
            }
        }
    }, done);
});