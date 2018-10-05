var ObjectMapperFactory = require("../app/models/databases/mongodb/objectmapperfactory");

var productMapper = ObjectMapperFactory.createProductMapper();
productMapper.find(
    {description: 'Papas'},
    (res) => console.log(res[0].categorydetail),
    (err) => console.log(err)
);
