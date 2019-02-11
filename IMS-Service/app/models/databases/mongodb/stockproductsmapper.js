module.exports = function(objectmapper) {
    var connector = objectmapper.getConnector();
    var collectionName = "stockproducts";

    return {
        insert(documents, success, fail) {
            objectmapper.insert(collectionName, documents, success, fail);
        },
        update(params, updatedDocument, success, fail) {
            objectmapper.update(collectionName, params, {$set: updatedDocument}, success, fail);
        },
        find(params, skip, success, fail) {
            if(params.date)
                params.date = new RegExp(params.date + ".*", "g");
            var sort = { date: -1 };

            connector.open(
                (dbo) => {
                    dbo.collection(collectionName).find(params)
                    .skip(skip)
                    .sort(sort)
                    .limit(11).toArray(function(err, result) {
                        if (err) fail(err);
                        else success(result);
                        connector.close();
                    });
                },
                (err) => fail(err)
            );
        },
        delete(params, success, fail) {
            objectmapper.delete(collectionName, params, success, fail);
        },
        count(params, success, fail) {
            objectmapper.count(collectionName, params, success, fail);
        }
    }
}
