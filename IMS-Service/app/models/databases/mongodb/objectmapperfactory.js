var ObjectMapper = require('./objectmapper');
var ProductMapper = require('./productmapper');
var ProviderMapper = require('./providermapper');
var CategoryMapper = require('./categorymapper');
var UserMapper = require('./usermapper');
var StockProductsMapper = require('./stockproductsmapper');

exports.createUserMapper = function() {
    return UserMapper(ObjectMapper());
}

exports.createProductMapper = function() {
    return ProductMapper(ObjectMapper());
}

exports.createProviderMapper = function() {
    return ProviderMapper(ObjectMapper());
}

exports.createCategoryMapper = function() {
    return CategoryMapper(ObjectMapper());
}

exports.createStockProductsMapper = function() {
    return StockProductsMapper(ObjectMapper());
}
