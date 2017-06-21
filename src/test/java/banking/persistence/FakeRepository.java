package banking.persistence;

import banking.model.Identifiable;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;

public class FakeRepository<T extends Identifiable> implements Repository<T> {

  public Map<UUID, T> items = new LinkedHashMap<>();
  public List<T> savedItems = new ArrayList<>();

  @Override
  public Optional<T> get(final UUID id) {
    T item = items.get(id);
    return Optional.ofNullable(item);
  }

  @Override
  public void save(T item) {
    savedItems.add(item);
    items.put(item.getId(), item);
  }

  @Override
  public Collection<T> getAll() {
    return items.values();
  }

}
