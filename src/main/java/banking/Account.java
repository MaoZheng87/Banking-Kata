package banking;

import banking.model.Identifiable;

import java.math.BigDecimal;
import java.util.UUID;

public class Account implements Identifiable {
  
  private UUID id = UUID.randomUUID();
  private Money balance;

  public Account(Money startingBalance) {
    balance = startingBalance;
  }
  
  public Account() {
    this(new Money(BigDecimal.ZERO));
  }

  @Override
  public UUID getId() {
    return id;
  }

  public Money getBalance() {
    return balance;
  }

  public void deposit(Money amount) {
    balance = balance.plus(amount);
  }

  public void withdraw(Money amount) {
    balance = balance.minus(amount);
  }

}
