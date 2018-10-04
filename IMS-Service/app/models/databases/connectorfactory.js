var ConnectorMongodb = require('./connector/connectormongodb.js');

exports.databases = {
    mongodb: 1
}

exports.createConnector = function(database) {
    switch(database) {
        default: return ConnectorMongodb();
    }
}
