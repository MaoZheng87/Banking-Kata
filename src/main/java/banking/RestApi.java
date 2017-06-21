package banking;

import banking.persistence.FileRepository;
import banking.persistence.Repository;
import com.google.gson.Gson;
import spark.Service;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public class RestApi {

  private static final int PORT = 8080;

  private Repository<Account> accountRepository;

  public static void main(String[] strings) {
    new RestApi(
        new FileRepository<>(Account.class, new File("accounts.json")))
            .run(Service.ignite());
  }

  public RestApi(Repository<Account> accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void run(Service spark) {
    spark.port(PORT);

    spark.before((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Request-Method", "*");
      response.header("Access-Control-Allow-Headers", "*");
    });

    spark.get("/accounts", (request, response) -> {
      Collection<Account> accounts = accountRepository.getAll();
      return accounts;
    }, new Gson()::toJson);

    spark.post("/accounts", (request, response) -> {
      Money startingBalance = new Gson().fromJson(request.body(), Money.class);
      Account account = new Account(startingBalance);
      accountRepository.save(account);
      return account;
    }, new Gson()::toJson);

    spark.post("/accounts/:id/deposit", (request, response) -> {
      Money depositAmount = new Gson().fromJson(request.body(), Money.class);
      UUID id = UUID.fromString(request.params("id"));
      Account account = accountRepository.get(id).get();
      account.deposit(depositAmount);
      accountRepository.save(account);
      return account.getBalance();
    }, new Gson()::toJson);

    spark.get("/accounts/:id/balance", (request, response) -> {

      UUID id = UUID.fromString(request.params("id"));

      return accountRepository
          .get(id)
          .map(account -> account.getBalance())
          .orElse(null);

    }, new Gson()::toJson);


    spark.after((request, response) -> {
      response.type("application/json");

    });
  }

}
