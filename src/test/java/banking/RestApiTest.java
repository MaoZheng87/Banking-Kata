package banking;

import static helpers.CustomMatchers.closeToFloat;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import banking.persistence.FakeRepository;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Service;

import java.util.HashMap;
import java.util.Map;

public class RestApiTest {

  private static final double ONE_CENT = 0.01;
  private static final String ACCOUNT_BALANCE = "12.34";

  private FakeRepository<Account> accountRepository;
  private Account account;
  private Service spark;

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
    spark = Service.ignite();
    new RestApi(accountRepository).run(spark);
    spark.awaitInitialization();
  }

  @After
  public void stopServer() {
    spark.stop();
  }

  @Test
  public void shouldGetTheCorrectAccountBalance() throws Exception {

    given()
        .accept(ContentType.JSON)
        .pathParam("accountId", account.getId().toString())

    .when()
        .get("/accounts/{accountId}/balance")

    .then()
        .statusCode(200)
        .headers(corsHeaders())
        .body(
            "amount", closeToFloat(12.34, ONE_CENT),
            "currency", is("USD"));
  }

  private static Map<String, String> corsHeaders() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Access-Control-Allow-Origin", "*");
    headers.put("Access-Control-Request-Method", "*");
    headers.put("Access-Control-Allow-Headers", "*");
    return headers;
  }
  
  @Test
  public void canCreateAnAccountByPostingToAccountsEndpoint() throws Exception {
    Money startingBalance = Money.of("67.98");
    accountRepository.items.clear();
    
    ValidatableResponse responseValidator = given()
      .contentType(ContentType.JSON)
      .body(new Gson().toJson(startingBalance))

  .when()
      .post("/accounts")
  
  .then()
      .headers(corsHeaders())
      .statusCode(200);
    
    assertThat(accountRepository.items).hasSize(1);
    Account savedAccount = accountRepository.items.get(0);
    assertThat(savedAccount.getBalance()).isEqualTo(startingBalance);
    
    responseValidator.body("id", is(savedAccount.getId().toString()));
  }

}
