/**
* Providers Controller
*
*/
module.exports = function (router, applicationContext) {
    router
        .get("/", function(req, res) {
            // Home
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
