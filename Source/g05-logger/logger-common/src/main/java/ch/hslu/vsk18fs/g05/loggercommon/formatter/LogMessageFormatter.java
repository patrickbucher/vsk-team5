package ch.hslu.vsk18fs.g05.loggercommon.formatter;

import java.time.Instant;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;

/**
 * Provides an interface to format a {@link LogMessage} implementing the Strategy pattern (GoF 315).
 */
public interface LogMessageFormatter {

  /**
   * Creates a {@link PersistedString} instance based on a {@link LogMessage}.
   *
   * @param log {@link LogMessage} to be persisted
   * @return {@link PersistedString} containing the information of the log parameter
   */
  public PersistedString format(LogMessage log);

  /**
   * Creates a {@link LogMessage} instance based on a {@link PersistedString}.
   *
   * @param str {@link PersistedString} to be parsed
   * @return {@link LogMessage} containing the information of the persisted string parameter
   */
  public LogMessage parse(PersistedString str);

  /**
   * Creates a {@link PersistedString} instance based on the given parameters, mainly used for test
   * purposes.
   *
   * @param created creation time stamp as {@link Instant}
   * @param level log level
   * @param received received time stamp as {@link Instant}
   * @param source message source
   * @param message the actual log message
   * @return a {@link PersistedString} instance
   */
  public PersistedString toPersistedString(final Instant created, final String level,
      final Instant received, final String source, final String message);
}
