var fs = require('fs');

module.exports = function() {
    this.csr = "/ssl/certificate.pem";
    this.key = "/ssl/key.pem";

    return {
        getCertificate() {
            return fs.readFileSync(__dirname + csr, 'utf8');
        },
        getPrivateKey() {
            return fs.readFileSync(__dirname + key, 'utf8');
        }
    }
}
