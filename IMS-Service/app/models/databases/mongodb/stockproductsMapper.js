module.exports = function(objectmapper) {
    var connect = objectmapper.getConnector();
    var collectionName = "stockproducts";

    return {
        insert(documents, success, fail) {
            objectmapper.insert(collectionName, documents, success, fail);
        },
        update(params, updatedDocument, success, fail) {
            objectmapper.update(collectionName, params, {$set: updatedDocument}, success, fail);
        },
        find(params, success, fail) {
            params.date = new RegExp(params.date + ".*", "g");
            objectmapper.find(collectionName, params, success, fail);
        },
        delete(params, success, fail) {
            objectmapper.delete(collectionName, params, success, fail);
        }
    }
}
