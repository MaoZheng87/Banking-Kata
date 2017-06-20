package banking.persistence;

import banking.model.Identifiable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileRepository<T extends Identifiable> implements Repository<T> {

  private Class<T> type;
  private File file;

  public FileRepository(Class<T> type, File file) {
    this.type = type;
    this.file = file;
  }

  @Override
  public Optional<T> get(UUID id) {
    Map<UUID, T> items = readMapFromFile();
    T value = items.get(id);
    return Optional.ofNullable(value);
  }

  private Map<UUID, T> readMapFromFile() {
    try (FileReader reader = new FileReader(file)) {

      return readAllItems(reader);

    } catch (FileNotFoundException e) {

      return new HashMap<>();

    } catch (IOException e) {
      // TODO need to do something smarter here than bailing
      e.printStackTrace();
    }
    throw new RuntimeException();
  }

  private Map<UUID, T> readAllItems(FileReader reader) {
    Gson gson = new Gson();
    TypeToken<?> typeToken = TypeToken.getParameterized(HashMap.class, UUID.class, this.type);
    Map<UUID, T> items = gson.fromJson(reader, typeToken.getType());
    return items == null ? new HashMap<UUID, T>() : items;
  }

  @Override
  public void save(T item) {
    Map<UUID, T> allItems = readMapFromFile();
    allItems.put(item.getId(), item);

    try (FileWriter fileWriter = new FileWriter(file)) {

      new Gson().toJson(allItems, fileWriter);

    } catch (IOException e) {
      // TODO need to do something smarter here than bailing
      e.printStackTrace();
    }

  }

  @Override
  public Collection<T> getAll() {
    Map<UUID, T> items = readMapFromFile();
    return Collections.unmodifiableCollection(items.values());
  }

  // TODO: what refactoring could be done here?

}
