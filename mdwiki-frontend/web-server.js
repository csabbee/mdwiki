#!/usr/bin/env node

var express = require('express'),
    ejs = require('ejs').renderFile,
    app = express();

app.use('/dist',express.static('dist'));
app.use('/jspm_packages',express.static('jspm_packages'));
app.use('/config.js',express.static('./config.js'));
app.use('/styles',express.static('styles'));
app.engine('html', ejs);


app.set('view engine', 'html');
app.set('views', './');

var DEFAULT_PORT = 18000;

app.get('/', function(req, res){
    res.render('index', {title: 'ejs'});
});

var server = app.listen(DEFAULT_PORT, function() {
    var host = server.address().address;
    var port = server.address().port;

    console.log('MarkDown Wiki is listening at http://%s:%s', host, port);
});