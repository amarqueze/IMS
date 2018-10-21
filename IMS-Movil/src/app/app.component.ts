import { Component, ViewChild } from '@angular/core';
import { Platform, NavController } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { FCM } from '@ionic-native/fcm';

import { HomePage } from '../pages/home/home';

@Component({
    templateUrl: 'app.html'
})
export class MyApp {
    @ViewChild('myNav') navCtrl: NavController;
    rootPage:any = HomePage;

    constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen, public fcm: FCM) {
        platform.ready().then(() => {
            this.fcm.getToken().then(token => {

            });
            fcm.onNotification().subscribe( Notification => {
                if(Notification.wasTapped) {
                    this.navCtrl.setRoot('DetailPage', Notification );
                }
                else {
                    this.navCtrl.push('DetailPage', Notification);
                }
            });
            statusBar.styleDefault();
            splashScreen.hide();
        });
    }
}
