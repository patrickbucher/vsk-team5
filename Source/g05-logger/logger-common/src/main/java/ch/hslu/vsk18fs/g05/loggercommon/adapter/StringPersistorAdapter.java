package ch.hslu.vsk18fs.g05.loggercommon.adapter;

import java.io.File;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import ch.hslu.vsk18fs.g05.loggercommon.formatter.LogMessageFormatter;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import ch.hslu.vsk18fs.g05.stringpersistor.StringPersistorFile;

/**
 * Implements the {@link LogPersistor} using the Adapter pattern (GoF 139).
 */
public class StringPersistorAdapter implements LogPersistor {

  private final StringPersistor stringPersistor;
  private final LogMessageFormatter formatter;

  private StringPersistorAdapter(final LogMessageFormatter formatter,
      final StringPersistor stringPersistor) {
    this.formatter = formatter;
    this.stringPersistor = stringPersistor;
  }

  /**
   * Creates a {@link StringPersistorAdapter} instance using a static factory method (see Bloch:
   * Effective Java, Item 1).
   *
   * @param formatter {@link LogMessageFormatter} strategy to be used
   * @param filename path to the file where the log messages are going to be stored
   * @return {@link StringPersistorAdapter} instance
   */
  public static StringPersistorAdapter create(final LogMessageFormatter formatter,
      final String filename) {
    final StringPersistor persistor = new StringPersistorFile();
    persistor.setFile(new File(filename));
    final StringPersistorAdapter adapter = new StringPersistorAdapter(formatter, persistor);
    return adapter;
  }

  @Override
  public void save(final LogMessage log) {
    this.stringPersistor.save(log.getCreationTimestamp(), this.formatter.format(log).getPayload());
  }

}
