package banking.persistence;

import banking.model.Identifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FakeRepository<T extends Identifiable> implements Repository<T> {

  public List<T> items = new ArrayList<>();

  @Override
  public Optional<T> findOne(final UUID id) {
    return items.stream().filter((T item) -> id.equals(item.getId())).findFirst();
  }

  @Override
  public void save(T item) {
    items.add(item);
  }

}
