import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountBalanceComponent } from './account-balance.component';

describe('AccountBalanceComponent', () => {
  let component: AccountBalanceComponent;
  let fixture: ComponentFixture<AccountBalanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountBalanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should have savings balance', () => {
    fixture.componentInstance.savingsBalance = 5;
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('[name=savingsBalance]').textContent.trim()).toEqual('5');
  });

  it('should have checking balance', () => {
    fixture.componentInstance.checkingBalance = 3;
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('[name=checkingBalance]').textContent.trim()).toEqual('3');
  });

  it('should have savings balance label', () => {
    expect(fixture.nativeElement.querySelector('[name=savingsBalanceLabel]').textContent).toEqual('Savings: ');
  });

  it('should have checking balance label', () => {
    expect(fixture.nativeElement.querySelector('[name=checkingBalanceLabel]').textContent).toEqual('Checking: ');
  });

});
