package ch.hslu.vsk18fs.g05.stringpersistor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

/**
 * Implementation of {@link StringPersistor}.
 */
public class StringPersistorFile implements StringPersistor {

  private final String lineSeparator;
  private BufferedWriter writer;
  private BufferedReader reader;

  /**
   * Constructor, sets the line separator to be used according to
   * {@code System.getProperty("line.separator")} or to {@code "\n"} if the former is not set.
   */
  public StringPersistorFile() {
    final String lineSeperatorProperty = System.getProperty("line.separator");
    this.lineSeparator = lineSeperatorProperty != null ? lineSeperatorProperty : "\n";
  }

  /**
   * Sets the given file to be used for saving and retrieving messages.
   *
   * @param file a {@link File} that must either exist (and be both readable and writable) or that
   *        can be created based on its path.
   * @throws IllegalArgumentException if the file cannot be created and/or is not readable or not
   *         writable
   */
  @Override
  public void setFile(final File file) {
    if (file == null) {
      throw new IllegalArgumentException("file must not be null");
    }
    if (!file.exists()) {
      if (!file.getParentFile().mkdirs()) {
        throw new IllegalArgumentException(
            "parent directories for given file neither exist nor could be created");
      }
      try {
        if (!file.createNewFile()) {
          throw new IllegalArgumentException("could not create file");
        }
      } catch (final IOException e) {
        throw new IllegalArgumentException(e);
      }
    }
    if (!file.canRead()) {
      throw new IllegalStateException("the file set is not readable");
    }
    if (!file.canWrite()) {
      throw new IllegalStateException("the file set is not writable");
    }
    try {
      this.writer = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
      this.reader = new BufferedReader(
          new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    } catch (final FileNotFoundException fnfEx) {
      throw new IllegalArgumentException("The file " + file + " does not exist.", fnfEx);
    }
  }

  /**
   * Saves the given pay load (log message) with the given instant to the file specified with
   * {@link #setFile(File)}. The message is stored in the format defined by
   * {@link PersistedString#toString()}.
   *
   * @param timestamp an {@link Instant}
   * @param payload the actual log message {@link String}
   * @throws IllegalStateException if no proper file was set
   */
  @Override
  public void save(final Instant timestamp, final String payload) {
    if (this.writer == null) {
      throw new IllegalStateException("no file has been set yet");
    }
    final PersistedString persistedString = new PersistedString(timestamp, payload);
    try {
      this.writer.write(persistedString.toString().trim() + this.lineSeparator);
      this.writer.flush();
    } catch (final IOException ioEx) {
      throw new IllegalStateException(
          "Writing to  " + this.writer + " is not possible at the moment.", ioEx);
    }
  }

  /**
   * Retrieves a number (given as count) of log messages. A log message can either consist of a
   * single line or encompass multiple lines (for StackTraces and the like). A line for which
   * {@link PersistedStringParser#startsWithIsoDate(String)} holds true is considered the start of a
   * new log message.
   *
   * @param count the number of messages that <em>should</em> be retrieved. If less messages are
   *        available, all the messages from the file specified with {@link #setFile(File)} will be
   *        returned.
   *
   * @return a {@link List} containing the parsed log messages as {@link PersistedString}.
   *         instances. An empty {@link List} will be returned for {@code count < 1}.
   *
   * @throws IllegalStateException if no proper file has been set before using
   *         {@link #setFile(File)}
   */
  @Override
  public List<PersistedString> get(final int count) {
    if (count < 1) {
      return new ArrayList<>();
    }
    if (this.reader == null) {
      throw new IllegalStateException("no file has been set yet");
    }
    final List<PersistedString> result = new ArrayList<>();
    StringBuilder itemBuffer = new StringBuilder(); // for multi-line messages
    String line = null;
    int itemsRead = 0;
    try {
      while ((line = this.reader.readLine()) != null && itemsRead < count) {
        // Start with ISO date indicates a new message
        if (PersistedStringParser.startsWithIsoDate(line)) {
          // If other lines of multi line messages have been buffered already...
          if (itemBuffer != null && itemBuffer.toString().length() > 0) {
            try {
              // ... attach the whole buffer and start a new one
              result.add(PersistedStringParser.parseItem(itemBuffer.toString().trim()));
              itemsRead++;
              itemBuffer = new StringBuilder();
            } catch (final IllegalArgumentException e) {
              System.err.println("cannot parse item, cause: " + e.getMessage());
            }
          }
        }
        // every line must be added to the buffer
        itemBuffer.append(line + this.lineSeparator);
      }
      // make sure to attach the remainder (if any)
      if (itemBuffer.length() > 0 && itemsRead < count) {
        try {
          result.add(PersistedStringParser.parseItem(itemBuffer.toString().trim()));
        } catch (final IllegalArgumentException e) {
          System.err.println("cannot parse item, cause:" + e.getMessage());
        }
      }
    } catch (final IOException ioEx) {
      throw new IllegalStateException("Error reading " + count + " items from " + this.reader + ".",
          ioEx);
    }
    return result;
  }
}
