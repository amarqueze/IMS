var ObjectMapperFactory = require("../app/models/databases/objectmapperfactory");

var databases = ObjectMapperFactory.databases;

var ObjectMapper = ObjectMapperFactory.createUserMapper(databases.mongodb);

console.log(ObjectMapper.getObject());

ObjectMapper = ObjectMapperFactory.createProductMapper(databases.mongodb);

console.log(ObjectMapper.getObject());

ObjectMapper = ObjectMapperFactory.createProviderMapper(databases.mongodb);

console.log(ObjectMapper.getObject());
