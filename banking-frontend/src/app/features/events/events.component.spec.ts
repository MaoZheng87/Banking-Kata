import { TestBed, async } from '@angular/core/testing';

import { EventsComponent } from './events.component';

describe('EventsComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        EventsComponent
      ],
    }).compileComponents();
  }));

  it(`should have as title 'Iowa State Fair'`, async(() => {
    const fixture = TestBed.createComponent(EventsComponent);
    const eventsComponent = fixture.debugElement.componentInstance;
    expect(eventsComponent.title).toEqual('Iowa State Fair');
  }));

it(`should have as dateTime '8/10 - 8/20 9 AM - Midnight'`, async(() => {
    const fixture = TestBed.createComponent(EventsComponent);
    const eventsComponent = fixture.debugElement.componentInstance;
    expect(eventsComponent.eventDateTime).toEqual('8/10 - 8/20 9 AM - Midnight');
  }));

  it(`should have as location 'Iowa State Fairgrounds'`, async(() => {
    const fixture = TestBed.createComponent(EventsComponent);
    const eventsComponent = fixture.debugElement.componentInstance;
    expect(eventsComponent.location).toEqual('Iowa State Fairgrounds');
  }));

});
