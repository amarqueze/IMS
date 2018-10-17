var jwt = require("jsonwebtoken");
var keyServer = `49~AOMR"k2bZRJv-1o{1`;

exports.auth = function(object) {
    return  jwt.sign({ foo: 'bar' }, keyServer);
}

exports.verify = function(token, success, fail) {
    jwt.verify(token, keyServer, function(err, decoded) {
        if(err) fail(err);
        else success(decoded);
    });
}
