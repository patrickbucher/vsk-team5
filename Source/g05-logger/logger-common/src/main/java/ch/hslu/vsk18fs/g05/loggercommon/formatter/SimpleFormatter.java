package ch.hslu.vsk18fs.g05.loggercommon.formatter;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

/**
 * Implements {@link LogMessageFormatter} using a very simple format:
 * <code>creation | [level] [server entry] [source] message</code>. Example:
 *
 * <pre>
 * 2018-04-07T07:58:53.391Z | [WARN] [2018-04-07T07:58:55.471Z] [127.0.0.1:4567] The disk is almost full.
 * </pre>
 */
public class SimpleFormatter implements LogMessageFormatter {

  @Override
  public PersistedString format(final LogMessage log) {
    final String payload = String.format("[%s] [%s] [%s] %s", log.getLevel(),
        log.getServerEntryTimestamp(), log.getSource(), log.getMessage());
    return new PersistedString(log.getCreationTimestamp(), payload);
  }

  @Override
  public LogMessage parse(final PersistedString str) {
    final String payload = str.getPayload();
    final Pattern pattern = Pattern.compile("^\\[(.+)\\] \\[(.+)\\] \\[(.*)\\] (.+)$");
    final Matcher matcher = pattern.matcher(payload);
    if (!matcher.matches() || matcher.groupCount() < 4) {
      return null;
    }
    final String level = matcher.group(1);

    Instant serverEntry = null;
    try {
      serverEntry = Instant.parse(matcher.group(2));
    } catch (final DateTimeParseException dtpEx) {
    }

    final String source = matcher.group(3);
    final String message = matcher.group(4);

    final Message parsed = new Message(level, str.getTimestamp(), serverEntry, message, source);
    return parsed;
  }

  @Override
  public PersistedString toPersistedString(final Instant created, final String level,
      final Instant received, final String source, final String message) {
    return new PersistedString(created,
        String.format("[%s] [%s] [%s] %s", level, received, source, message));
  }
}

