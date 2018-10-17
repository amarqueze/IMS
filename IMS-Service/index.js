/*
Copyright 2018 Alan M.E

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

var Server = require("./app/server/server");
var Certificate = require("./app/server/certificate");
var Log = require("./app/models/log/logger");
var ApplicationContext = require('./app/models/applicationcontext');

/* import controllers */
var UsersController = require('./app/controllers/userscontroller');
var ProductsController = require('./app/controllers/productscontroller');
var ProvidersController = require('./app/controllers/providerscontroller');
var CategoriesController = require('./app/controllers/categoriescontroller');
var StockProductsController = require('./app/controllers/stockproductscontroller');
var AuthenticationController = require('./app/controllers/authenticationcontroller');

var dispatcher = Server(Certificate());

var log = Log.Logger('Server Nodejs', [
    {filename: "temp/error", level: "error"},
    {filename: "temp/notice", level: "notice"},
    {filename: "temp/server"}
])
var applicationContext = ApplicationContext(log);

dispatcher
    .setControllerListener((path, router) => applicationContext.getLog().info(`Controller linked to '${path}'`))
    .setEventListener((event) => applicationContext.getLog().info(`EventListener linked to '${event}'`));

dispatcher
    .addController("/", AuthenticationController, applicationContext)
    .addController("/users", UsersController, applicationContext)
    .addController("/products", ProductsController, applicationContext)
    .addController("/providers", ProvidersController, applicationContext)
    .addController("/categories", CategoriesController, applicationContext)
    .addController("/stock", StockProductsController, applicationContext)
    .addMiddleware("/", (req, res, next) => res.status(404).send('Sorry, we cannot find that service!'));

dispatcher.run(function(port) {
    applicationContext.getLog().info(`Server listening port: ${port}`);
});

dispatcher.addEventServer('request', (request, response) => {
    applicationContext.getLog().info(`incoming request to server: ${request.socket.remoteAddress}`);
});
