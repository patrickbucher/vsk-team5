package ch.hslu.vsk18fs.g05.loggercommon.formatter;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

@RunWith(Parameterized.class)
public class LogMessageFormatterTest {

  private final LogMessageFormatter formatter;

  public LogMessageFormatterTest(final LogMessageFormatter formatter) {
    this.formatter = formatter;
  }

  @Test
  public void testFormatFullMessage() {
    final Instant created = Instant.now();
    final String level = "WARN";
    final Instant received = Instant.now();
    final String source = "127.0.0.1:9999";
    final String message = "All your base are belong to us.";
    final LogMessage msg = new Message(level, created, received, message, source);

    final PersistedString expected =
        this.formatter.toPersistedString(created, level, received, source, message);
    final PersistedString actual = this.formatter.format(msg);

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testFormatPartialMessage() {
    final Instant created = Instant.now();
    final String level = "WARN";
    final Instant received = null;
    final String source = "";
    final String message = "All your base are belong to us.";
    final LogMessage msg = new Message(level, created, received, message, source);

    final PersistedString expected =
        this.formatter.toPersistedString(created, level, received, source, message);
    final PersistedString actual = this.formatter.format(msg);

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testParseFullMessage() {
    final Instant created = Instant.now();
    final String level = "DEBUG";
    final Instant received = null;
    final String source = "localhost";
    final String message = "At the end, it is what is is...";

    final LogMessage expected = new Message(level, created, received, message, source);
    final LogMessage actual = this.formatter
        .parse(this.formatter.toPersistedString(created, level, received, source, message));

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testParsePartialMessage() {
    final Instant created = Instant.now();
    final String level = "DEBUG";
    final Instant received = null;
    final String source = "";
    final String message = "At the end, it is what is is...";

    final LogMessage expected = new Message(level, created, received, message, source);
    final LogMessage actual = this.formatter
        .parse(this.formatter.toPersistedString(created, level, received, source, message));

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testParseNastyData() {
    final Instant created = Instant.now();
    final String level = "[ [[DEBUG]]";
    final Instant received = null;
    final String source = "[localhost[.localdomain[:12345]]]]";
    final String message = "[This[is[one[nasty[message]]]]]";

    final LogMessage expected = new Message(level, created, received, message, source);
    final LogMessage actual = this.formatter
        .parse(this.formatter.toPersistedString(created, level, received, source, message));

    Assert.assertEquals(expected, actual);
  }

  @Parameters
  public static Collection<LogMessageFormatter> getParameters() {
    return Arrays.asList(new SimpleFormatter(), new CurlyFormatter());
  }
}
