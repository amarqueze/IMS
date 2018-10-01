/**
* Products Controller
*
*/
module.exports = function (router, applicationContext) {
    router
        .get("/", function(req, res) {
            // Home
        })
        .get("/list", function(req, res) {
            res.json({status: 200, message: "Products List"});
        })
        .get("/find", function(req, res) {
            res.json({status: 200, message: "Products Find params"});
        })
        .get("/find/:id", function(req, res) {
            res.json({status: 200, message: "Products Find id"});
        })
        .get("/delete", function(req, res) {
            res.json({status: 200, message: "Products Delete Params"});
        })
        .get("/delete/:id", function(req, res) {
            res.json({status: 200, message: "Products Delete id"});
        })
        .post("/create", function(req, res) {
            res.json({status: 200, message: "Products create"});
        })
        .post("/edit", function(req, res) {
            res.json({status: 200, message: "Products edit"});
        })

    return router;
}
