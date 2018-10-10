var ObjectMapperFactory = require("../app/models/databases/mongodb/objectmapperfactory");

var productMapper = ObjectMapperFactory.createProductMapper();
productMapper.find(
    {_id: '5bbe29d60175822f308cef0a'},
    (res) => console.log(res),
    (err) => console.log(err)
);
