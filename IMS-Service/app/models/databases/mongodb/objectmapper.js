var ConnectorMongodb = require('./connectormongodb');

module.exports = function() {
    var connector = ConnectorMongodb.createConnector();  

    return {
        insert(collectionName, documents, success, fail) {
            connector.open(
                (dbo) => {
                    dbo.collection(collectionName).insertMany(documents, function(err, res) {
                        if (err) fail(err);
                        success(res);
                        connector.close();
                    });
                },
                (err) => fail(err)
            );
        },
        update(collectionName, params, updatedDocument, success, fail) {
            connector.open(
                (dbo) => {
                    dbo.collection(collectionName).updateOne(params, updatedDocument, function(err, res) {
                        if (err) fail(err);
                        success(res);
                        connector.close();
                    });
                },
                (err) => fail(err)
            );
        },
        find(collectionName, params, success, fail) {
            connector.open(
                (dbo) => {
                    dbo.collection(collectionName).find(params).toArray(function(err, result) {
                        if (err) fail(err);
                        success(result);
                        connector.close();
                    });
                },
                (err) => fail(err)
            );
        },
        delete(collectionName, params, success, fail) {
            connector.open(
                (dbo) => {
                    dbo.collection(collectionName).deleteOne(params, function(err, res) {
                        if (err) fail(err);
                        success(res);
                        connector.close();
                    });
                },
                (err) => fail(err)
            );
        },
        getConnector() {
            return connector;
        }
    }
}
