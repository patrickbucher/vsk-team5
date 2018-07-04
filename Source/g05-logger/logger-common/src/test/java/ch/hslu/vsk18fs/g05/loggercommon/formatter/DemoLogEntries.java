package ch.hslu.vsk18fs.g05.loggercommon.formatter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

/**
 * {@link DemoLogEntries} is a demo class to demonstrate how the {@link Message} objects are
 * formatted using the two {@link LogMessageFormatter} implementations {@link SimpleFormatter} and
 * {@link CurlyFormatter} for documentation purposes.
 */
public class DemoLogEntries {

  public static void main(final String[] args) {
    final Map<String, String> messages = new LinkedHashMap<>();
    messages.put("TRACE", "Cell at [14;23] died");
    messages.put("DEBUG", "New generation");
    messages.put("INFO", "Window was resized");
    messages.put("WARNING", "Speed 'Hyper' was selected");
    messages.put("ERROR", "Connection to server lost");
    messages.put("CRITICAL", "Unable to log locally");

    Arrays.asList(new SimpleFormatter(), new CurlyFormatter()).stream().forEach(fmt -> {
      messages.keySet().stream().forEach(level -> {
        final String msg = messages.get(level);
        final Instant creation = Instant.now();
        final Instant received = Instant.now().plus(250, ChronoUnit.MILLIS);
        final String source = "192.168.1.42:52413";
        final Message message = new Message(level, creation, received, msg, source);
        System.out.println(fmt.format(message));
      });
    });
  }

}
