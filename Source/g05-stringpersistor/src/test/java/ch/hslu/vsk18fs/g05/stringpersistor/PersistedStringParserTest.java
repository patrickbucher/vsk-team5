package ch.hslu.vsk18fs.g05.stringpersistor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk18fs.g05.stringpersistor.PersistedStringParser;

public class PersistedStringParserTest {

  @Test
  public void startsWithIsoDatePositiveTest() {
    final String isoDate = Instant.now().toString();
    final String line = isoDate + " | This is a test";

    Assert.assertTrue(PersistedStringParser.startsWithIsoDate(line));
  }

  @Test
  public void startsWithIsoDateNegativeTest() {
    final String nonIsoDate =
        DateTimeFormatter.RFC_1123_DATE_TIME.format(LocalDateTime.now().atOffset(ZoneOffset.UTC));
    final String line = nonIsoDate + " | This is another test.";

    Assert.assertFalse(PersistedStringParser.startsWithIsoDate(line));
  }

  @Test
  public void parsePersistedString() {
    final PersistedString expected = new PersistedString(Instant.now(), "Hello world!");
    final String expectedString = expected.toString();
    try {
      final PersistedString got = PersistedStringParser.parseItem(expectedString);
      Assert.assertEquals(expected, got);
    } catch (final IllegalArgumentException iaEx) {
      fail(iaEx.getMessage());
    }
  }

  @Test
  public void parseMalformattedPersistedString() {
    // wrong separator
    final String malformattedString = Instant.now().toString() + " ||| Hello world!";
    try {
      PersistedStringParser.parseItem(malformattedString);
      fail("malformatted string must not be parsed correctly");
    } catch (final IllegalArgumentException iaEx) {
      Assert.assertNotNull(iaEx);
    }
  }

  @Test
  public void parsePersistedStringNotStartingWithIsoDate() {
    final String nonIsoDate =
        DateTimeFormatter.RFC_1123_DATE_TIME.format(LocalDateTime.now().atOffset(ZoneOffset.UTC));
    final String line = nonIsoDate + " | This is yet another test.";
    try {
      PersistedStringParser.parseItem(line);
      fail("'" + line + "' must not have been parsed correctly");
    } catch (final IllegalArgumentException iaEx) {
      assertNotNull(iaEx);
    }
  }
}
