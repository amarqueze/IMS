var fs = require("fs");
/**
* Providers Controller
*
*/
module.exports = function (router, applicationContext) {
    router
        .get("/", function(req, res) {
            fs.readFile(__dirname + '/descriptor/providers.json', 'utf8', (err, data) => {
                if (err) throw err;
                res.json(JSON.parse(data));
            });
        })
        .get("/list", function(req, res) {
            res.json({status: 200, message: "providers List"});
        })
        .get("/find", function(req, res) {
            res.json({status: 200, message: "providers Find params"});
        })
        .get("/find/:id", function(req, res) {
            res.json({status: 200, message: "providers Find id"});
        })
        .get("/delete", function(req, res) {
            res.json({status: 200, message: "providers Delete Params"});
        })
        .get("/delete/:id", function(req, res) {
            res.json({status: 200, message: "providers Delete id"});
        })
        .post("/create", function(req, res) {
            res.json({status: 200, message: "providers create"});
        })
        .post("/edit", function(req, res) {
            res.json({status: 200, message: "providers edit"});
        })

    return router;
}
