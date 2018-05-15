package ch.hslu.vsk18fs.g05.loggercommon.message;

import java.time.Instant;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import ch.hslu.vsk18fs.g05.loggercommon.adapter.StringPersistorAdapter;

/**
 * Provides an interface for log messages to be exchanged between the logger server and the
 * {@link StringPersistor} over the {@link StringPersistorAdapter}.
 */
public interface LogMessage {

  /**
   * Returns the message level.
   *
   * @return message level as {@link String}
   */
  public String getLevel();

  /**
   * Returns a time stamp of the instant the message was <em>created</em>.
   *
   * @return creation time stamp as {@link Instant}
   */
  public Instant getCreationTimestamp();

  /**
   * Returns a time stamp of the instant the message was <em>received by the server</em>, which must
   * be later than the time stamp returned by getCreationTimestamp().
   *
   * @return server entry time stamp as {@link Instant}
   */
  public Instant getServerEntryTimestamp();

  /**
   * Returns the actual log message received by the server.
   *
   * @return log message as {@link String}
   */
  public String getMessage();

  /**
   * Returns the message source.
   *
   * @return message source as {@link String}
   */
  public String getSource();
}
