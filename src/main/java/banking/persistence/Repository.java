package banking.persistence;

import banking.model.Identifiable;

import java.util.Optional;
import java.util.UUID;

public interface Repository<T extends Identifiable> {

  Optional<T> get(UUID id);

  void save(T item);

}
