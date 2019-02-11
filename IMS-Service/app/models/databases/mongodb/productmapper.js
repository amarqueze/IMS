module.exports = function(objectmapper) {
    var connector = objectmapper.getConnector();
    var collectionName = "products";

    return {
        insert(documents, success, fail) {
            objectmapper.insert(collectionName, documents, success, fail);
        },
        update(params, updatedDocument, success, fail) {
            objectmapper.update(collectionName, params, {$set: updatedDocument}, success, fail);
        },
        find(params, skip, success, fail) {
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
                                foreignField: '_id',
                                as: 'categorydetail'
                            }
                        }
                    ]).skip(skip).limit(11).toArray(function(err, res) {
                        if (err) fail(err);
                        success(res);
                        connector.close();
                    });
                },
                (err) => fail(err)
            );
        },
        findAll(params, sucess, fail) {
            objectmapper.find(collectionName, params, sucess, fail);
        },
        delete(params, success, fail) {
            objectmapper.delete(collectionName, params, success, fail);
        },
        count(params, success, fail) {
            objectmapper.count(collectionName, params, success, fail);
        }
    }
}
// sucess(response)
// fail(err)
