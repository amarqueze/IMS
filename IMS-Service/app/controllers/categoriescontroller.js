var fs = require("fs");
var factory = require("../models/databases/mongodb/objectmapperfactory");

module.exports = function (router, applicationContext) {
    var categorymapper = factory.createCategoryMapper();

    router
        .get("/", function(req, res) {
            fs.readFile(__dirname + '/descriptor/categories.json', 'utf8', (err, data) => {
                if (err) throw err;
                res.set('Content-Type', 'application/json');
                res.send(data);
            });
        })
        .get("/list", function(req, res) {
            categorymapper.find({},
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/find", function(req, res) {
            categorymapper.find(req.query,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/find/:id", function(req, res) {
            categorymapper.find({_id: req.params.id},
                (response) => res.json((response[0]) ? {ok: 1, response: response[0]} : {}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/delete", function(req, res) {
            categorymapper.delete(req.query,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/delete/:id", function(req, res) {
            categorymapper.delete({_id: req.params.id},
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/count", function(req, res) {
            categorymapper.count(req.query,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .post("/create", function(req, res) {
            categorymapper.insert(req.body,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .post("/edit", function(req, res) {
            categorymapper.update(req.query,
                req.body,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })

    return router;
}
