module.exports = function(objectmapper) {
    var connect = objectmapper.getConnector();
    var collectionName = "stockproducts";

    return {
        insert(documents, success, fail) {
            objectmapper.insert(collectionName, documents, success, fail);
        },
        update(params, updatedDocument, success, fail) {
            objectmapper.update(collectionName, documents, success, fail);
        },
        find(params, success, fail) {
            objectmapper.find(collectionName, params, success, fail);
        },
        delete(params, success, fail) {
            objectmapper.delete(collectionName, params, success, fail);
        }
    }
}
