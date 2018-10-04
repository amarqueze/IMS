var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, { useNewUrlParser: true }, function(err, client) {
    if (err) throw err;
    var dbo = client.db("ims");

    // ================== operations ==========================================

    // ================== insert ==========================================
    /*dbo.collection('providers', function (err, collection) {
        if (err) throw err;

        collection.insertOne({ dni: 1, firstName: 'Steve', lastName: 'Jobs' });
        collection.insertOne({ dni: 2, firstName: 'Bill', lastName: 'Gates' });
        collection.insertOne({ dni: 3, firstName: 'James', lastName: 'Bond' });
    });*/

    // ================== query ==========================================
    /*dbo.collection('users', function (err, collection) {
        if (err) throw err;

        var options = {
            projection: {
                firstName: 1,
                lastName: 1
            }
        }
        var letter = 'Ste';
        var query = {
            firstName: {$regex: `^${letter}.*`}
        }

        collection.find(query, options).toArray(function(err, result) {
            if (err) throw err;
            console.log(result);
         });
    });*/

    // ================== delete ==========================================
    /*dbo.collection('users', function (err, collection) {
        if (err) throw err;

        var options = {
            projection: {
                firstName: 1,
                lastName: 1
            }
        }
        var letter = 'Ste';
        var query = {
            firstName: {$regex: `^${letter}.*`}
        }

        collection.deleteOne(query);
        console.log("delete field");
    });*/

    // ================== Update ==========================================
    dbo.collection('users', function (err, collection) {
        if (err) throw err;

        var options = {
            projection: {
                firstName: 1,
                lastName: 1
            }
        }

        var letter = 'Bill';

        var query = {
            firstName: {$regex: `^${letter}.*`}
        }

        var newvalues = {
            $set: {firstName: "Mickey", age: 48}
        }

        collection.updateOne(query, newvalues, function(err, res) {
            if (err) throw err;
            console.log("1 document updated");
        });
    });

    client.close();
});

console.log("execute Script...");
