var gcm = require('node-gcm');

exports.getInstance = Notification;

function Notification() {
    const API_KEY = '<API_KEY>';
    var sender = new gcm.Sender(API_KEY);

    return {
        send(title, body, data) {
            let message = new gcm.Message({
                priority: 'high',
                restrictedPackageName: "io.amecodelabs.ims",
                timeToLive: 1,
                notification: {
                    title: title,
                    icon: "ic_launcher",
                    body: body
                },
                data: data
            });

            let recipients = { to: "/topics/ims" };

            sender.send(message, recipients, (err, response) => {
                if (err) console.log(err);
            });
        }
    }
}
