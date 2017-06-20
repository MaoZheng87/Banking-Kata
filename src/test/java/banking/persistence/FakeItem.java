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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FakeItem other = (FakeItem) obj;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  
}