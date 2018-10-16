var fs = require("fs");
var factory = require("../models/databases/mongodb/objectmapperfactory");

module.exports = function (router, applicationContext) {
    var productmapper = factory.createProductMapper();

    router
        .get("/", function(req, res) {
            fs.readFile(__dirname + '/descriptor/products.json', 'utf8', (err, data) => {
                if (err) throw err;
                res.set('Content-Type', 'application/json');
                res.send(data);
            });
        })
        .get("/list", function(req, res) {
            productmapper.find({},
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/find", function(req, res) {
            productmapper.find(req.query,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/find/:id", function(req, res) {
            productmapper.find({_id: req.params.id},
                (response) => res.json((response[0]) ? {ok: 1, response: response[0]} : {}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/delete", function(req, res) {
            productmapper.delete(req.query,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/delete/:id", function(req, res) {
            productmapper.delete({_id: req.params.id},
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .post("/create", function(req, res) {
            productmapper.insert(req.body,
                (response) => res.json({ok: 1, response}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .post("/edit", function(req, res) {
            productmapper.update(req.query,
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
