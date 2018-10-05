var ConnectorMongodb = require('../app/models/databases/mongodb/connectormongodb');

var Connector = ConnectorMongodb.createConnector()

Connector.open(
    test,
    (err) => console.log("connection fail")
);

function test(dbo) {
    console.log("connection open");
    Connector.close();
}
