import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
    items: Array<Object> = [
        {
            title: "Depletion of stock",
            date: "5:41 PM",
            text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            type: "alert",
            icon: "alert"
        },
        {
            title: "Depletion of stock",
            date: "2:40 PM",
            text: "A scelerisque purus semper eget duis.",
            type: "warning",
            icon: "warning"
        },
        {
            title: "Entry of products",
            date: "10:20 AM",
            text: "Tempor id eu nisl nunc mi.",
            type: "secure",
            icon: "cube"
        },
        {
            title: "New login",
            date: "8:40 AM",
            text: "Jane Doe is Connected.",
            type: "connected",
            icon: "person"
        },
        {
            title: "New login",
            date: "7:00 AM",
            text: "John doe is Connected.",
            type: "connected",
            icon: "person"
        }
    ]

    constructor(public navCtrl: NavController) {

    }

}
