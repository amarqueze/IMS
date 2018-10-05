module.exports = function(objectmapper) {
    var connector = objectmapper.getConnector();
    var collectionName = "products";

    return {
        insert(documents, success, fail) {
            objectmapper.insert(collectionName, documents, success, fail);
        },
        update(params, updatedDocument, success, fail) {
            objectmapper.update(collectionName, documents, success, fail);
        },
        find(params, success, fail) {
            connector.open(
                (dbo) => {
                    dbo.collection(collectionName).aggregate([
                        {
                            $match: params
                        },
                        {
                            $lookup: {
                                from: 'categories',
                                localField: 'category',
                                foreignField: 'codigo',
                                as: 'categorydetail'
                            }
                        }
                    ]).toArray(function(err, res) {
                        if (err) fail(err);
                        success(res);
                        connector.close();
                    });
                },
                (err) => fail(err)
            );
        },
        delete(params, success, fail) {
            objectmapper.delete(collectionName, params, success, fail);
        }
    }
}
// sucess(response)
// fail(err)
