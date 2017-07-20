import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ApiService } from './api.service';
import {HomeComponent} from "./features/home/home.component";
import {AccountBalanceComponent} from "./features/account-balance/account-balance.component";

@NgModule({
  declarations: [
    AppComponent,
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
