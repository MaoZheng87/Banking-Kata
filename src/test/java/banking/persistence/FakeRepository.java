package banking.persistence;

import banking.model.Identifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeRepository<T extends Identifiable> implements Repository<T> {

  public Map<UUID, T> items = new HashMap<>();

  @Override
  public Optional<T> get(final UUID id) {
    return Optional.ofNullable(items.get(id));
  }

  @Override
  public void save(T item) {
    items.put(item.getId(), item);
  }

  @Override
  public Collection<T> getAll() {
    return items.values();
  }

}
