var factory = require("../models/databases/mongodb/objectmapperfactory");
var jwtAuthentication = require("../models/jwt/authentication");

module.exports = function(router, applicationContext) {
    var usermapper = factory.createUserMapper();

    var unauthorizedObject = {ok:0, message: "unauthorized user"};

    router
        .post("/auth", function(req, res) {
            if(!req.body.email || !req.body.password)
                res.status(403).json(unauthorizedObject);
            else if(req.body.email === '' || req.body.password === '')
                res.status(403).json(unauthorizedObject);
            else {
                usermapper.find({email: req.body.email, password: req.body.password},
                    (response) => {
                        if(response.length === 0) res.status(403).json(unauthorizedObject);
                        else {
                            var token = jwtAuthentication.auth(response[0]);
                            res.json({ok: 1, token, response: response[0]});
                        }
                    },
                    (err) => {
                        applicationContext.getLog().error(err.message);
                        res.json({ok: 0, message: err.message})
                    },
                    true
                );
            }
        })
        .all("*", function(req, res, next) {
            var headerAuthorization = req.get('authorization');
            if(!headerAuthorization) res.status(403).json(unauthorizedObject);
            else {
                var token = headerAuthorization.replace('Bearer ', '');
                jwtAuthentication.verify(token,
                    (decoded) => {
                        next();
                    },
                    (err) => {
                        res.status(403).json({ok:0, message: "invalid token"});
                    }
                );
            }
        })

    return router;
}
