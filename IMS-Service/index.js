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

var server = require("./app/server/server");
// logger libreria

/* import controllers */
var UsersController = require('./app/controllers/userscontroller');
var ProductsController = require('./app/controllers/productscontroller');
var ProvidersController = require('./app/controllers/providerscontroller');

//create AplicationContext

var dispatcher = server();

dispatcher.run(80, function(port) {
    console.log(`Server listening port: ${port}`);
});

dispatcher.bind_middleware("/", function(req, res, next) {
    //handle authentication [optional]
    next();
});

dispatcher
    .bind_controller("/users", UsersController)
    .bind_controller("/products", ProductsController)
    .bind_controller("/providers", ProvidersController);

dispatcher.bind_middleware("/", function(req, res, next) {
    //handle 404
    res.status(404).send('Sorry, we cannot find that!');
});

dispatcher.bind_event('connection', (socket) => {
    //handle log
});
