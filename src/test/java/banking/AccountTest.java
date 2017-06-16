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
  
}
