package banking.persistence;

import banking.model.Identifiable;

import java.util.UUID;

public class FakeItem implements Identifiable {

  private UUID id = UUID.randomUUID();

  @Override
  public UUID getId() {
    return this.id ;
  }
  
  public void setId(UUID id) {
    this.id = id;
  }
  
}