/**
* Users Controller
*
*/
module.exports = function (router, applicationContext) {
    router
        .get("/", function(req, res) {
            // Home
        })
        .get("/list", function(req, res) {
            res.json({status: 200, message: "Users List"});
        })
        .get("/find", function(req, res) {
            res.json({status: 200, message: "User Find params"});
        })
        .get("/find/:id", function(req, res) {
            res.json({status: 200, message: "User Find id"});
        })
        .get("/delete", function(req, res) {
            res.json({status: 200, message: "User Delete Params"});
        })
        .get("/delete/:id", function(req, res) {
            res.json({status: 200, message: "User Delete id"});
        })
        .post("/create", function(req, res) {
            res.json({status: 200, message: "User create"});
        })
        .post("/edit", function(req, res) {
            res.json({status: 200, message: "User edit"});
        })

    return router;
}
