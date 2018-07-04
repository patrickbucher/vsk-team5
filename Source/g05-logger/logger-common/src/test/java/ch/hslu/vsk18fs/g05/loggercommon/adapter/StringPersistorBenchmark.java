package ch.hslu.vsk18fs.g05.loggercommon.adapter;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import ch.hslu.vsk18fs.g05.stringpersistor.StringPersistorFile;

public class StringPersistorBenchmark {

  private static final int TIMEOUT_MILLIS = 1000;
  private static final int WRITE_MESSAGES = 1000;
  private static final int READ_MESSAGES = 500;
  private static final int MESSAGE_LENGTH = 100;

  private StringPersistor persistor;
  private List<String> messages;
  private List<PersistedString> retrieved;

  @Before
  public void setUp() throws Exception {
    final File file = File.createTempFile("StringPersistorBenchmarkIT", ".log");
    this.persistor = new StringPersistorFile();
    this.persistor.setFile(file);
    this.messages = new ArrayList<>();
    for (int i = 0; i < WRITE_MESSAGES; i++) {
      this.messages.add(this.randomString(MESSAGE_LENGTH));
    }
  }

  @Test(timeout = TIMEOUT_MILLIS)
  public void testReadWriteCycl() {
    for (int i = 0; i < WRITE_MESSAGES; i++) {
      this.persistor.save(Instant.now(), this.messages.get(i));
    }
    this.retrieved = this.persistor.get(READ_MESSAGES);
  }

  @After
  // The test case is a benchmark for writing and retrieving messages, so the validation of the
  // result is not part of the benchmark, but helpful, nonetheless. The order of the random messages
  // must be maintained!
  public void checkMessags() {
    Assert.assertEquals(READ_MESSAGES, this.retrieved.size());
    for (int i = 0; i < READ_MESSAGES; i++) {
      Assert.assertEquals(this.messages.get(i), this.retrieved.get(i).getPayload());
    }
  }

  private String randomString(final int messageLength) {
    final Random random = new Random(System.currentTimeMillis());
    final StringBuilder str = new StringBuilder();
    for (int i = 0; i < messageLength; i++) {
      final char c = (char) (random.nextInt('z' - 'a' + 1) + 'a');
      str.append(c);
    }
    return str.toString();
  }

}
