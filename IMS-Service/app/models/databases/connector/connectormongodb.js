var MongoClient = require('mongodb').MongoClient;

module.exports = function() {
    var url = "mongodb://localhost:27017/";
    var client;

    return {
        open(operation, success, fail) {
            MongoClient.connect(url, { useNewUrlParser: true }, function(err, clientMongodb) {
                if (err) fail(err);
                client = clientMongodb;
                var dbo = client.db("ims");
                operation(dbo, success, fail);
            });
        },
        close() {
            if(client) client.close();
        }
    }
}
