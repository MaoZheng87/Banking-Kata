package banking;

import banking.persistence.Repository;
import com.google.gson.Gson;
import spark.Spark;

import java.util.Optional;
import java.util.UUID;

public class RestApi {

  private static final int PORT = 8080;
  
  private Repository<Account> accountRepository;

  public static void main(String[] strings) {
    Account account = new Account(Money.of("12.34"));
    System.out.println(account.getId());
    
    new RestApi(new Repository<Account>() {
      
      @Override
      public Optional<Account> findOne(UUID id) {
        return Optional.of(account);
      }
    }).run();

  }

  public RestApi(Repository<Account> accountRepository) {
    this.accountRepository = accountRepository;
  }


  public void run() {
    Spark.port(PORT);
    
    Spark.get("/account/:id/balance", (request, response) -> {
      
      UUID id = UUID.fromString(request.params("id"));
      
      return accountRepository
          .findOne(id)
          .map(account -> account.getBalance())
          .orElse(null);
      
    }, new Gson()::toJson);
    
    Spark.after((request, response) -> {
      response.type("application/json");
    });
  }

}
