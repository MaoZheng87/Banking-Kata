package banking.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import org.assertj.core.util.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
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
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(FakeItem.class, tempFile);
    
    FakeItem item = new FakeItem();
    repository.save(item);
    
    String fileContents = Files.contentOf(tempFile, Charset.defaultCharset());
    assertThat(fileContents).containsSequence(item.getId().toString());
  }
  
  @Test
  public void findsAnItemFromFileInJSONFormat() throws Exception {
    FakeItem item1 = new FakeItem();
    FakeItem item2 = new FakeItem();
    
    try (FileWriter writer = new FileWriter(tempFile)) {
      FakeItem[] items = new FakeItem[] { item1, item2 };
      new Gson().toJson(items, writer);
    }
    FileRepository<FakeItem> repository = new FileRepository<>(FakeItem.class, tempFile);

    Optional<FakeItem> foundItem = repository.findOne(item1.getId());
    
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().getId()).isEqualTo(item1.getId());
    
  }
  
  @Test
  public void canRoundTripItemsToAndFromTheRepository() throws Exception {
    FakeItem item1 = new FakeItem();
    FakeItem item2 = new FakeItem();
    
    FileRepository<FakeItem> repository = new FileRepository<>(FakeItem.class, tempFile);
    
    repository.save(item1);
    repository.save(item2);
    
    Optional<FakeItem> foundItem = repository.findOne(item1.getId());
    
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().getId()).isEqualTo(item1.getId());
  }
  
  // TODO: What if something goes wrong?
  // TODO: What about reading all items?

}
