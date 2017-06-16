package banking.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import banking.model.Identifiable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.assertj.core.util.Files;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileRepositoryTest {
  
  private File tempFile;

  @Before
  public void makeTempFile() throws Exception {
    tempFile = File.createTempFile("banking", "test.json");
    tempFile.deleteOnExit();
  }
  
  @Test
  public void savesNewItemsToAFile() throws Exception {
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(tempFile);
    
    FakeItem item = new FakeItem();
    repository.save(item);
    
    String fileContents = Files.contentOf(tempFile, Charset.defaultCharset());
    assertThat(fileContents).containsSequence(item.getId().toString());
  }
  
  @Test
  @Ignore
  public void findsAnItemFromFileInJSONFormat() throws Exception {
    FakeItem item1 = new FakeItem();
    FakeItem item2 = new FakeItem();
    
    try (FileWriter writer = new FileWriter(tempFile)) {
      List<FakeItem> items = Arrays.asList(item1, item2);
      
      Type type = new TypeToken<ArrayList<Identifiable>>(){}.getType();
      
      new Gson().toJson(items, type, writer);
    }
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(tempFile);

    Optional<FakeItem> foundItem = repository.findOne(item1.getId());
    
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().getId()).isEqualTo(item1.getId());
    
  }

}
