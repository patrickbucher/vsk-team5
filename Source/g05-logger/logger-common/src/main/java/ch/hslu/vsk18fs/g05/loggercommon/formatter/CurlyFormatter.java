package ch.hslu.vsk18fs.g05.loggercommon.formatter;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

/**
 * Implements {@link LogMessageFormatter} using curly braces: <code>{created:creation}
 * {received:received} {level:level} {source:source} {message:message}</code>
 *
 * <pre>
 * 2018-04-07T07:58:53.391Z | {received:2018-04-07T07:58:55.471Z} {level:WARN} {source:127.0.0.1:4567} {message:The disk is almost full.}
 * </pre>
 *
 */
public class CurlyFormatter implements LogMessageFormatter {

  @Override
  public PersistedString format(final LogMessage log) {
    final String payload = String.format("{received:%s} {level:%s} {source:%s} {message:%s}",
        log.getServerEntryTimestamp(), log.getLevel(), log.getSource(), log.getMessage());
    return new PersistedString(log.getCreationTimestamp(), payload);
  }

  @Override
  public LogMessage parse(final PersistedString str) {
    final String payload = str.getPayload();
    final Pattern pattern = Pattern
        .compile("^\\{received:(.*)\\} \\{level:(.+)\\} \\{source:(.*)\\} \\{message:(.+)\\}$");
    final Matcher matcher = pattern.matcher(payload);
    if (!matcher.matches() || matcher.groupCount() < 4) {
      return null;
    }

    Instant serverEntry = null;
    try {
      serverEntry = Instant.parse(matcher.group(1));
    } catch (final DateTimeParseException dtpEx) {
    }

    final String level = matcher.group(2);
    final String source = matcher.group(3);
    final String message = matcher.group(4);

    final Message parsed = new Message(level, str.getTimestamp(), serverEntry, message, source);
    return parsed;
  }

  @Override
  public PersistedString toPersistedString(final Instant created, final String level,
      final Instant received, final String source, final String message) {
    final String payload = String.format("{received:%s} {level:%s} {source:%s} {message:%s}",
        received, level, source, message);
    return new PersistedString(created, payload);
  }

}
