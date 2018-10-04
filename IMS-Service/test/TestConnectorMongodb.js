var Connector = require('../app/models/databases/connectorfactory');


var ConnectorMongodb = Connector.createConnector(Connector.databases.mongodb);

ConnectorMongodb.open(
    test,
    (result) => console.log(result),
    (err) => console.log("connection fail")
);

function test(dbo, success, fail) {
    success("connection open");
    ConnectorMongodb.close();
}
