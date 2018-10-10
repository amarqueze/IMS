var fs = require("fs");
var factory = require("../models/databases/mongodb/objectmapperfactory");

module.exports = function (router, applicationContext) {
    var providermapper = factory.createProviderMapper();

    router
        .get("/", function(req, res) {
            fs.readFile(__dirname + '/descriptor/providers.json', 'utf8', (err, data) => {
                if (err) throw err;
                res.set('Content-Type', 'application/json');
                res.send(data);
            });
        })
        .get("/list", function(req, res) {
            providermapper.find({},
                (response) => res.json(response),
                (err) => res.json({ok: 0, message: err.message})
            );
        })
        .get("/find", function(req, res) {
            providermapper.find(req.query,
                (response) => res.json(response),
                (err) => res.json({ok: 0, message: err.message})
            );
        })
        .get("/find/:id", function(req, res) {
            providermapper.find({_id: req.params.id},
                (response) => res.json((response[0]) ? response[0] : {}),
                (err) => res.json({ok: 0, message: err.message})
            );
        })
        .get("/delete", function(req, res) {
            providermapper.delete(req.query,
                (response) => res.json(response),
                (err) => res.json({ok: 0, message: err.message})
            );
        })
        .get("/delete/:id", function(req, res) {
            providermapper.delete({_id: req.params.id},
                (response) => res.json(response),
                (err) => res.json({ok: 0, message: err.message})
            );
        })
        .post("/create", function(req, res) {
            providermapper.insert(req.body,
                (response) => res.json(response),
                (err) => res.json({ok: 0, message: err.message})
            );
        })
        .post("/edit", function(req, res) {
            providermapper.update(req.query,
                req.body,
                (response) => res.json(response),
                (err) => res.json({ok: 0, message: err.message})
            );
        })

    return router;
}
