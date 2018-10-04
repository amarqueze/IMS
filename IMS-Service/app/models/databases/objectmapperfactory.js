var ProductMapper = require('./objectmapper/productmapper');
var ProviderMapper = require('./objectmapper/providermapper');
var UserMapper = require('./objectmapper/usermapper');
var Connector = require('./connectorFactory');

exports.databases = Connector.databases;

exports.createUserMapper = function(database) {
    return UserMapper(Connector.createConnector(database));
}

exports.createProductMapper = function(database) {
    return ProductMapper(Connector.createConnector(database));
}

exports.createProviderMapper = function(database) {
    return ProviderMapper(Connector.createConnector(database));
}
