package ch.hslu.vsk18fs.g05.stringpersistor;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import ch.hslu.vsk.stringpersistor.api.PersistedString;

public class StringPersistorFileIT {

  private static final int TEST_SIZE = 10;

  @Test
  public void testSimpleWriteReadCycle() {
    final String lineSeparator = "\n";
    System.setProperty("line.seperator", lineSeparator);
    final List<PersistedString> persistedStrings =
        this.generatePersistedStrings(TEST_SIZE, false, lineSeparator);
    final StringPersistorFile spf = this.setupPersistorFile();

    // write some messages
    for (final PersistedString pstr : persistedStrings) {
      spf.save(pstr.getTimestamp(), pstr.getPayload());
    }

    // try to receive the messages
    final List<PersistedString> retrievedStrings = spf.get(TEST_SIZE);
    Assert.assertEquals(persistedStrings, retrievedStrings);
  }

  @Test
  public void testWindowsLineSeparator() {
    final String lineSeperator = "\r\n"; // CRLF
    System.setProperty("line.seperator", lineSeperator);
    final List<PersistedString> persistedStrings =
        this.generatePersistedStrings(TEST_SIZE, false, lineSeperator);
    final StringPersistorFile spf = this.setupPersistorFile();

    // write some messages
    for (final PersistedString pstr : persistedStrings) {
      spf.save(pstr.getTimestamp(), pstr.getPayload());
    }

    // try to receive the messages
    final List<PersistedString> retrievedStrings = spf.get(TEST_SIZE);
    Assert.assertEquals(persistedStrings, retrievedStrings);
  }

  @Test
  public void testMultiLineMessages() {
    final String lineSeparator = "\n";
    System.setProperty("line.seperator", lineSeparator);
    final List<PersistedString> persistedStrings =
        this.generatePersistedStrings(3, true, lineSeparator);
    final StringPersistorFile spf = this.setupPersistorFile();

    // write some messages
    for (final PersistedString pstr : persistedStrings) {
      spf.save(pstr.getTimestamp(), pstr.getPayload());
    }

    // try to receive the messages
    final List<PersistedString> retrievedStrings = spf.get(3);
    Assert.assertEquals(persistedStrings, retrievedStrings);
  }

  @Test
  public void testWriteWithoutFile() {
    final StringPersistorFile spf = new StringPersistorFile();
    try {
      spf.save(Instant.now(), "I am going to nowhere.");
      Assert.fail("writing to no file must cause an exception");
    } catch (final IllegalStateException isEx) {
      Assert.assertNotNull(isEx);
    }
  }

  @Test
  public void testReadWithoutFile() {
    final StringPersistorFile spf = new StringPersistorFile();
    try {
      spf.get(1);
      Assert.fail("reading from no file must cause an exception");
    } catch (final IllegalStateException isEx) {
      Assert.assertNotNull(isEx);
    }
  }

  @Test
  public void testUnwritableFile() throws Exception {
    final File file = File.createTempFile("unwritable", ".txt");
    file.setWritable(false);
    final StringPersistorFile spf = new StringPersistorFile();
    try {
      spf.setFile(file);
      Assert.fail("using an unwritable file must cause an exception");
    } catch (final IllegalStateException isEx) {
      Assert.assertNotNull(isEx);
    }
  }

  @Test
  public void testUnreadableFile() throws Exception {
    final File file = File.createTempFile("unreadable", ".txt");
    file.setReadable(false);
    final StringPersistorFile spf = new StringPersistorFile();
    try {
      spf.setFile(file);
      Assert.fail("using an unreadable file must cause an exception");
    } catch (final IllegalStateException isEx) {
      Assert.assertNotNull(isEx);
    }
  }

  @Test
  public void testNotExistantFile() {
    final File homedir = new File(System.getProperty("user.home"));
    final File notExistant =
        new File(homedir.getAbsolutePath() + File.separator + System.currentTimeMillis());
    if (notExistant.exists()) {
      Assert.fail("make sure to really use a non existant file the next time!");
    }
    final StringPersistorFile spf = new StringPersistorFile();
    try {
      spf.setFile(notExistant);
      Assert.fail("using a non existant file must cause an exception");
    } catch (final IllegalArgumentException iaEx) {
      Assert.assertNotNull(iaEx);
    }
  }

  private List<PersistedString> generatePersistedStrings(final int n, final boolean multiline,
      final String lineSeparator) {
    final List<PersistedString> persistedStrings = new ArrayList<>();
    // write some messages
    for (int i = 0; i < n; i++) {
      final Instant instant = Instant.now();
      String payload;
      if (multiline) {
        payload = this.createMultilineNumberedPayload(n, lineSeparator);
      } else {
        payload = this.createSingleLineNumberedPayload(n);
      }
      persistedStrings.add(new PersistedString(instant, payload));
    }
    return persistedStrings;
  }

  private String createSingleLineNumberedPayload(final int n) {
    return "This is a test: " + n;
  }

  private String createMultilineNumberedPayload(final int n, final String lineSeparator) {
    return String.join(lineSeparator, "This", "is", "a", "test: " + String.valueOf(n));
  }

  private StringPersistorFile setupPersistorFile() {
    final StringPersistorFile spf = new StringPersistorFile();
    try {
      spf.setFile(File.createTempFile("messages", ".txt"));
    } catch (final IOException e) {
      Assert.fail(e.getMessage());
    }
    return spf;
  }
}
