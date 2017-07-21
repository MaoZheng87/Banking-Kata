import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account-balance',
  templateUrl: './account-balance.component.html',
  styleUrls: ['./account-balance.component.css']
})
export class AccountBalanceComponent implements OnInit {
  savingsBalance: number;
  checkingBalance: number;

  constructor() { }

  ngOnInit() {
    this.savingsBalance = 5000;
    this.checkingBalance = 2000;
  }

}
