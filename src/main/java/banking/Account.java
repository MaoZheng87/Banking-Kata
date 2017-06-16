package banking;

import java.math.BigDecimal;

public class Account {
  
  private Money balance;

  public Account(Money startingBalance) {
    balance = startingBalance;
  }
  
  public Account() {
    this(new Money(BigDecimal.ZERO));
  }

  public void deposit(Money amount) {
    balance = balance.plus(amount);
  }

  public Money getBalance() {
    return balance;
  }

  public void withdraw(Money amount) {
    balance = balance.minus(amount);
  }

}
