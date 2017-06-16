package banking.persistence;

import banking.model.Identifiable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileRepository<T extends Identifiable> implements Repository<T> {

  private Class<T> type;
  private File file;

  public FileRepository(Class<T> type, File file) {
    this.type = type;
    this.file = file;
  }

  @Override
  public Optional<T> findOne(UUID id) {
    try (FileReader reader = new FileReader(file)) {

      Stream<T> items = readItems(reader);
      return items.filter(item -> id.equals(item.getId())).findFirst();

    } catch (FileNotFoundException e) {
      // TODO need to do something smarter here than bailing
      e.printStackTrace();
    } catch (IOException e) {
      // TODO need to do something smarter here than bailing
      e.printStackTrace();
    }
    return null;
  }

  private Stream<T> readItems(FileReader reader) {
    Gson gson = new Gson();
    JsonArray jsonArray = new JsonParser().parse(reader).getAsJsonArray();
    Stream<T> items = StreamSupport.stream(jsonArray.spliterator(), false)
        .map(obj -> gson.fromJson(obj, type));
    return items;
  }

  @Override
  public void save(T item) {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(item.getId().toString());
    } catch (IOException e) {
      // TODO need to do something smarter here than bailing
      e.printStackTrace();
    }

  }

}
