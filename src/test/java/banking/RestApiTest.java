package banking;

import static helpers.CustomMatchers.closeToFloat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

import banking.persistence.FakeRepository;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Spark;

public class RestApiTest {

  private static final double ONE_CENT = 0.01;
  private static final String ACCOUNT_BALANCE = "12.34";

  private FakeRepository<Account> accountRepository;
  private Account account;

  @Before
  public void setup() {
    setupAccounts();
    startServer();
  }

  private void setupAccounts() {
    account = new Account(Money.of(ACCOUNT_BALANCE));

    accountRepository = new FakeRepository<>();
    accountRepository.items.add(account);
  }

  public void startServer() {
    new RestApi(accountRepository).run();
  }

  @After
  public void stopServer() {
    Spark.stop();
  }

  @Test
  public void shouldGetTheCorrectAccountBalance() throws Exception {

    given()
        .accept(ContentType.JSON)
        .pathParam("accountId", account.getId().toString())

    .when()
        .get("/account/{accountId}/balance")

    .then()
        .statusCode(200)
        .body(
            "amount", closeToFloat(12.34, ONE_CENT),
            "currency", is("USD"));
  }





}
