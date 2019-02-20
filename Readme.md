# IMS

> Inventory Managament System

### Deployment view 
<p align="center">
    <img src="docs/img/deploymentview.png" alt="deploy" />
</p>

### IMS-Desktop JavaFX
<p align="center">
    <img src="docs/img/imslogindesktop.png" alt="deploy" />
</p>

### IMS-Movil Ionic Framework

<p align="center">
    <img src="docs/img/imslogin.png" alt="ionic Login" />
    <img src="docs/img/imsmain.png" alt="ionic main" />
</p>

### How use/testing

Edit IMS-Service/app/models/notification/notification.js
```js
const API_KEY = '<API_KEY>';
```

Install dependencies IMS-Service

```console
~/Projects/IMS$ cd IMS-Service
~/Projects/IMS/IMS-Service$ npm install
~/Projects/IMS/IMS-Service$ cd ..
~/Projects/IMS$ docker-compose up
```

### Desktop App - Setup (News)
- Windows: [ims-desktop-2.0-windows-x86_64.msi](dist/ims-desktop-2.0-windows-x86_64.msi)
- Windows: [ims-desktop-2.0-windows-x86_64.zip](dist/ims-desktop-2.0-windows-x86_64.zip)
- Linux rpm: [ims-desktop-2.0-linux-x86_64.rpm](dist/ims-desktop-2.0-linux-x86_64.rpm)
- Linux deb: [ims-desktop-2.0-linux-x86_64.deb](dist/ims-desktop-2.0-linux-x86_64.deb)
- Linux: [ims-desktop-2.0-linux-x86_64.zip](dist/ims-desktop-2.0-linux-x86_64.zip)

### Movil App (android)
IMS Notification: [app-debug.apk](dist/app-debug.apk)