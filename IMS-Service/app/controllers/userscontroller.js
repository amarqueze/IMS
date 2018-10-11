var fs = require("fs");
var factory = require("../models/databases/mongodb/objectmapperfactory");

module.exports = function (router, applicationContext) {
    var usermapper = factory.createUserMapper();

    router
        .get("/", function(req, res) {
            fs.readFile(__dirname + '/descriptor/users.json', 'utf8', (err, data) => {
                if (err) throw err;
                res.set('Content-Type', 'application/json');
                res.send(data);
            });
        })
        .get("/list", function(req, res) {
            usermapper.find({},
                (response) => res.json(response),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/find", function(req, res) {
            usermapper.find(req.query,
                (response) => res.json(response),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/find/:id", function(req, res) {
            usermapper.find({dni: req.params.id},
                (response) => res.json((response[0]) ? response[0] : {}),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/delete", function(req, res) {
            usermapper.delete(req.query,
                (response) => res.json(response),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .get("/delete/:id", function(req, res) {
            usermapper.delete({dni: req.params.id},
                (response) => res.json(response),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .post("/create", function(req, res) {
            usermapper.insert(req.body,
                (response) => res.json(response),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })
        .post("/edit", function(req, res) {
            usermapper.update(req.query,
                req.body,
                (response) => res.json(response),
                (err) => {
                    applicationContext.getLog().error(err.message);
                    res.json({ok: 0, message: err.message})
                }
            );
        })

    return router;
}
