var server = require("./app/server/server");
// logger libreria

/* import controllers */
var UsersController = require('./app/controllers/userscontroller');

var dispatcher = server();
dispatcher.bind_controller("/users", UsersController);

dispatcher.run(80, function(port) {
    console.log(`Server listening port: ${port}`);
});
