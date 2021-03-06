package banking.persistence;

import static java.nio.file.Files.copy;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.util.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class FileRepositoryTest {

  private File dataFile;

  @Before
  public void makeDataFile() throws Exception {
    dataFile = makeTempFile("fileRepository");
  }

  @Test
  public void savesNewItemsToAFile() throws Exception {
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(FakeItem.class, dataFile);

    String uuid = "f3343523-b9b2-4524-a081-f7b06e9e5aae";
    String content = "hello";

    repository.save(new FakeItem(UUID.fromString(uuid), content));

    String fileContents = Files.contentOf(dataFile, "UTF-8");
    assertThat(fileContents).containsSequence(uuid);
    assertThat(fileContents).containsSequence(content);
  }

  @Test
  public void canReadItemsFromPreExistingFile() throws Exception {
    // Arrange
    UUID id = UUID.randomUUID();
    String content = "abc";

    new FileRepository<>(FakeItem.class, dataFile).save(new FakeItem(id, content));
    File copyOfDataFile = makeCopyOf(dataFile);

    // Act
    FileRepository<FakeItem> copyRepository = new FileRepository<>(FakeItem.class, copyOfDataFile);
    Optional<FakeItem> foundItem = copyRepository.get(id);

    // Assert
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().getId()).isEqualTo(id);
    assertThat(foundItem.get().content).isEqualTo(content);
  }

  @Test
  public void canRoundTripItemsToAndFromTheRepository() throws Exception {
    // Arrange
    UUID id = UUID.randomUUID();
    String content = "Happy Fun Times";
    FileRepository<FakeItem> repository = new FileRepository<>(FakeItem.class, dataFile);

    // Act
    repository.save(new FakeItem(id, content));
    Optional<FakeItem> foundItem = repository.get(id);

    // Assert
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().getId()).isEqualTo(id);
    assertThat(foundItem.get().content).isEqualTo(content);
  }

  @Test
  public void canSaveMoreThanOneItemToTheRepository() throws Exception {
    // Arrange
    UUID id = UUID.randomUUID();
    String content = "Second item";
    FileRepository<FakeItem> repository = new FileRepository<>(FakeItem.class, dataFile);

    // Act
    repository.save(new FakeItem());
    repository.save(new FakeItem(id, content));
    repository.save(new FakeItem());
    Optional<FakeItem> foundItem = repository.get(id);

    // Assert
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().getId()).isEqualTo(id);
    assertThat(foundItem.get().content).isEqualTo(content);
  }

  @Test
  public void mutatingAndResavingItemsUpdatesTheirContentsOnDisk() throws Exception {
    // Arrange
    UUID id = UUID.randomUUID();
    FakeItem item = new FakeItem(id, "abc");
    FileRepository<FakeItem> repository = new FileRepository<>(FakeItem.class, dataFile);
    repository.save(item);

    // Act
    item.content = "xyz";
    repository.save(item);

    // Assert
    Optional<FakeItem> foundItem = repository.get(id);
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().content).isEqualTo("xyz");
    assertThat(Files.contentOf(dataFile, "UTF-8")).doesNotContain("abc");
  }

  @Test
  public void savingNewCopiesOfExistingItemsUpdatesTheirContentsOnDisk() throws Exception {
    // Arrange
    UUID id = UUID.randomUUID();
    FakeItem originalItem = new FakeItem(id, "abc");
    FakeItem updatedItem = new FakeItem(id, "xyz");
    FileRepository<FakeItem> repository = new FileRepository<>(FakeItem.class, dataFile);
    repository.save(originalItem);

    // Act
    repository.save(updatedItem);

    // Assert
    Optional<FakeItem> foundItem = repository.get(originalItem.getId());
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().content).isEqualTo("xyz");
    assertThat(Files.contentOf(dataFile, "UTF-8")).doesNotContain("abc");
  }

  @Test
  public void savingShouldCreateTheDataFileIfItDoesntExist() throws Exception {
    // Arrange
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(FakeItem.class, dataFile);
    dataFile.delete();

    // Act
    repository.save(new FakeItem());

    // Assert
    assertThat(dataFile).exists();
  }

  @Test
  public void getAllItemsIsEmptyWhenFileIsEmpty() throws Exception {
    // Arrange
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(FakeItem.class, dataFile);

    // Act
    Collection<FakeItem> items = repository.getAll();

    // Assert
    assertThat(items).isEmpty();
  }

  @Test
  public void gettingAllItemsMeansEverythingThatWasPreviouslySaved() throws Exception {
    // Arrange
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(FakeItem.class, dataFile);
    FakeItem item1 = new FakeItem();
    FakeItem item2 = new FakeItem();
    FakeItem item3 = new FakeItem();
    repository.save(item1);
    repository.save(item2);
    repository.save(item3);

    // Act
    Collection<FakeItem> items = repository.getAll();

    // Assert
    assertThat(items).containsOnly(item1, item2, item3);
  }

  @Test(expected = Exception.class)
  public void cannotAddToItemsCollectionDirectly() throws Exception {
    // Arrange
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(FakeItem.class, dataFile);
    Collection<FakeItem> items = repository.getAll();

    // Act
    items.add(new FakeItem());
  }

  @Test(expected = Exception.class)
  public void cannotRemoveItemsFromCollectionDirectly() throws Exception {
    // Arrange
    FileRepository<FakeItem> repository = new FileRepository<FakeItem>(FakeItem.class, dataFile);
    FakeItem item = new FakeItem();
    repository.save(item);

    Collection<FakeItem> items = repository.getAll();

    // Act
    items.remove(item);
  }

  private static File makeTempFile(String prefix) throws IOException {
    File file = File.createTempFile(prefix, "test");
    file.deleteOnExit();
    return file;
  }

  private static File makeCopyOf(File sourceFile) throws IOException {
    File copyOfData = makeTempFile("copyOfFileRepository");
    copy(sourceFile.toPath(), copyOfData.toPath(), StandardCopyOption.REPLACE_EXISTING);
    return copyOfData;
  }

  // TODO: What if something goes wrong writing or reading from the file (IOException)?
  // TODO: What about reading all items?
  // TODO: What about using a different file format?

}
