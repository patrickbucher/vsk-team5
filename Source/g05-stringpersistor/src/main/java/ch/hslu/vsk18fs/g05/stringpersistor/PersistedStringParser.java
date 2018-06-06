package ch.hslu.vsk18fs.g05.stringpersistor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import ch.hslu.vsk.stringpersistor.api.PersistedString;

/**
 * Utility class to parse {@link String} instances to {@link PersistedString}.
 */
public class PersistedStringParser {

  /**
   * Format derived from {@link PersistedString#toString()}
   */
  private final static String SEPARATOR = " | ";

  /**
   * Parses the given string into a PersistedString object.
   *
   * @param string String following the pattern produced in {@link PersistedString#toString()}
   * @return {@link PersistedString} instance in case of success
   * @throws IllegalArgumentException if the given {@link String} cannot be parsed into a
   *         {@link PersistedString}.
   */
  public static PersistedString parseItem(final String string) throws IllegalArgumentException {
    if (!startsWithIsoDate(string)) {
      throw new IllegalArgumentException(
          "Cannot parse '" + string + "' as PersistedString (must start with an iso date/time)");
    }
    if (!string.contains(SEPARATOR)) {
      throw new IllegalArgumentException("The string '" + string
          + "' cannot be parsed, it must have been formatted using PersistedString.toString()");
    }
    final int separatorStartsAt = string.indexOf(SEPARATOR);
    final int separatorEndsAt = separatorStartsAt + SEPARATOR.length();
    final String datePart = string.substring(0, separatorStartsAt);
    final String messagePart = string.substring(separatorEndsAt);
    final Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(datePart));
    return new PersistedString(instant, messagePart);
  }

  /**
   * Checks whether or not the given String starts with an ISO date time
   * ({@link DateTimeFormatter#ISO_DATE_TIME}).
   *
   * @param line the line to be checked
   * @return true if the line starts with an ISO date time string, false otherwise
   */
  public static boolean startsWithIsoDate(final String line) {
    if (line == null || line.length() == 0) {
      return false;
    }
    final int firstSpaceAt = line.indexOf(" ");
    if (firstSpaceAt == -1) {
      return false;
    }
    final String datePart = line.substring(0, firstSpaceAt);
    try {
      return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(datePart)) != null;
    } catch (final DateTimeParseException dtpEx) {
      return false;
    }
  }
}
