package banking.persistence;

import banking.model.Identifiable;

import java.util.UUID;

public class FakeItem implements Identifiable {

  private final UUID id;
  public String content;

  public FakeItem(UUID id, String content) {
    this.id = id;
    this.content = content;
  }

  public FakeItem() {
    this(UUID.randomUUID(), "fake");
  }

  @Override
  public UUID getId() {
    return this.id ;
  }
  
}