var https = require('https');
var http = require('http');
var express = require('express');
var certificate = require('./certificate');

module.exports = function() {
    const portHttps = 443;
    const portHttp = 80;

    var app = express();
    var credentials = certificate();
    var serverHttp;
    var serverHttps;
    var errorHandle;

    return {
        run (port, callback) {
            if(!callback)
                throw Error('No exist function callback');

            sslOptions = {key: credentials.getPrivateKey(), cert: credentials.getCertificate()};
            serverHttp = http.createServer(app).listen(port, () => callback(port));
            serverHttps = https.createServer(sslOptions, app).listen(portHttps, () => callback(portHttps));
        },
        bind_controller(path, controller, applicationContext) {
            let router = express.Router({caseSensitive: true, strict: true, mergeParams: false});
            app.use(path, controller(router, applicationContext));
            return this;
        },
        bind_event() {

        },
        bind_middleware() {

        },
        setErrorHandle(callback) {
            errorHandle = callback;
        }
    }
}