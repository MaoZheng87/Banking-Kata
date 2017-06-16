package banking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AccountTest {

  @Test
  public void defaultStartingBalanceIsZeroDollars() throws Exception {
    Account account = new Account();

    assertThat(account.getBalance()).isEqualTo(Money.of("0.00"));
  }

  @Test
  public void canSpecifyAStartingBalance() throws Exception {
    Account account = new Account(Money.of("65.98"));

    assertThat(account.getBalance()).isEqualTo(Money.of("65.98"));
  }

  @Test
  public void depositedFundsAreAddedToAvailableBalance() throws Exception {
    Account account = new Account(Money.of("52.01"));

    account.deposit(Money.of("12.34"));

    assertThat(account.getBalance()).isEqualTo(Money.of("64.35"));
  }

  @Test
  public void withdrawnFundsAreSubtractedFromAvailableBalance() throws Exception {
    Account account = new Account(Money.of("82.00"));

    account.withdraw(Money.of("56.30"));

    assertThat(account.getBalance()).isEqualTo(Money.of("25.70"));
  }

  @Test
  public void balanceGoesNegativeIfOverdrawn() throws Exception {
    Account account = new Account(Money.of("10.00"));

    account.withdraw(Money.of("30.00"));

    assertThat(account.getBalance()).isEqualTo(Money.of("-20.00"));
  }

}
