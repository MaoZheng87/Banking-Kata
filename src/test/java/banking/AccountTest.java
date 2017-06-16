package banking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {

  @Test
  public void defaultStartingBalanceIsZeroDollars() throws Exception {
    Account account = new Account();
    
    assertThat(account.getBalance()).isEqualTo(new Money(new BigDecimal("0.00")));
  }
  
  @Test
  public void canSpecifyAStartingBalance() throws Exception {
    Account account = new Account(new Money(new BigDecimal("65.98")));
    
    assertThat(account.getBalance()).isEqualTo(new Money(new BigDecimal("65.98")));
  }
  
  @Test
  public void depositedFundsAreAddedToAvailableBalance() throws Exception {
    Account account = new Account(new Money(new BigDecimal("52.01")));
    
    account.deposit(new Money(new BigDecimal("12.34")));
    
    assertThat(account.getBalance()).isEqualTo(new Money(new BigDecimal("64.35")));
  }
  
}
