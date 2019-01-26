var MongoDriver = require('mongodb').MongoClient;

exports.createConnector = function() {
    return ConnectorMongodb();
}

function ConnectorMongodb() {
    var url = "mongodb://database-demo-mongodb:27017/";
    var client;

    return {
        open(success, fail) {
            MongoDriver.connect(url, { useNewUrlParser: true }, function(err, MongoClient) {
                if (err) fail(err);
                else {
                    client = MongoClient;
                    var dbo = client.db("ims");
                    success(dbo);
                }
            });
        },
        close() {
            if(client) client.close();
        }
    }
}
