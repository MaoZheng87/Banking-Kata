import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { EventsComponent } from './features/events/events.component';

import { ApiService } from './api.service';
import {HomeComponent} from "./features/home/home.component";
import {AccountBalanceComponent} from "./features/account-balance/account-balance.component";

@NgModule({
  declarations: [
    AppComponent,
    EventsComponent,
    HomeComponent,
    AccountBalanceComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
