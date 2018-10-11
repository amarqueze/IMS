var https = require('https');
var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');

module.exports = function(certificate) {
    var portHttps = 443;
    var portHttp = 80;

    var app = express();

    var serverHttp;
    var serverHttps;

    var onBindController;
    var onBindEventServer;
    var onBindMiddleware;

    (function server() {
        app.use(bodyParser.json());
        app.use(bodyParser.urlencoded({ extended: true }));
    })();

    return {
        run (callback) {
            if(!callback)
                throw Error('No exist function callback');
            if(certificate) {
                sslOptions = {key: certificate.getPrivateKey(), cert: certificate.getCertificate()};
                serverHttps = https.createServer(sslOptions, app).listen(portHttps, () => callback(portHttps));
            }
            serverHttp = http.createServer(app).listen(portHttp, () => callback(portHttp));
        },
        addController(path, controller, applicationContext) {
            let router = express.Router({caseSensitive: true, strict: true, mergeParams: false});
            app.use(path, controller(router, applicationContext));

            if (onBindController) onBindController(path, router);
            return this;
        },
        addEventServer(event, callback) {
            if(serverHttps) serverHttps.on(event, (err, socket) => callback(err, socket));
            else serverHttp.on(event, (err, socket) => callback(err, socket));

            if (onBindEventServer) onBindEventServer(event);
            return this;
        },
        addMiddleware(path, callback) {
            app.use(path, callback);

            if (onBindMiddleware) onBindMiddleware(path);
            return this;
        },
        setControllerListener(callback) {
            onBindController = callback;
            return this;
        },
        setEventListener(callback) {
            onBindEventServer = callback;
            return this;
        },
        setMiddlewareListener(callback) {
            onBindMiddleware = callback;
            return this;
        },
        setPortHttp(port) {
            portHttp = port;
            return this;
        },
        setPortHttps(port) {
            portHttps = port;
            return this;
        }
    }
}
