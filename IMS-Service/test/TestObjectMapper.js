var ConnectorMongodb =  require('../app/models/databases/mongodb/connectormongodb');
var ObjectMapper =  require('../app/models/databases/mongodb/objectmapper');

var Connector = ConnectorMongodb.createConnector();
var objectMapper = ObjectMapper(Connector);

objectMapper.insert(
    'products',
    [
        { description: 'Papas', price: '$12' },
        {  description: 'Platanos', price: '$10' }
    ],
    (response) => console.log("success: " + response.insertedCount),
    (err) => console.log(err)
);

objectMapper.find(
    'products',
    {},
    (response) => console.log(response),
    (err) => console.log(err)
);

objectMapper.update(
    'products',
    {description: 'apple'},
    {$set: {price: '$7'}},
    (response) => console.log("success: " + response.insertedCount),
    (err) => console.log(err)
);

objectMapper.delete(
    'products',
    {description: 'apple'},
    (response) => console.log("success"),
    (err) => console.log(err)
);
