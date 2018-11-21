var Notification = require('../app/models/notification/notification.js');

var date = new Date();
var notification = Notification.getInstance();

notification.send("IMS Notification",
    "New Notification",
    { title: 'Entry Products',
      date: date.toDateString(),
      text: '50 productX added',
      type: 'Product'
    }
);
