
import { Component } from "@angular/core";

// font-family: Proxima Nova

@Component({
  selector: 'events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent {

    title = 'Iowa State Fair';
    eventDateTime = '8/10 - 8/20 9 AM - Midnight';
    location = 'Iowa State Fairgrounds';

}
