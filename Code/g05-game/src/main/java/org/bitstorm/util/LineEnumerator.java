/*
 * Enumerates over a string containing a text file, returning lines.
 */
package org.bitstorm.util;

import java.util.Enumeration;

/**
 * Enumerates over a string containing a text file, returning lines. Line endings in the text can be
 * "\r\n", "\r" or "\n".
 *
 * @author Edwin Martin
 */
// But nothing beats Python ;-)
// for line in file("file.txt"):
// # Process line
public class LineEnumerator implements Enumeration {

  public static final String CR = "\r";
  public static final String CRLF = "\r\n";
  public static final String LF = "\n";

  int eolOffset;
  int offset;

  private final String s;
  private final String separator;

  /**
   * Constructs a TextEnumerator.
   *
   * @param s String with text
   */
  public LineEnumerator(final String s) {
    this.s = s;
    // find out the seperator
    if (s.contains(CR)) {
      if (s.contains(CRLF)) {
        separator = CRLF;
      } else {
        separator = CR;
      }
    } else {
      separator = LF;
    }
    eolOffset = -separator.length();
  }

  /**
   * @see Enumeration#hasMoreElements()
   */
  @Override
  public boolean hasMoreElements() {
    return eolOffset != s.length();
  }

  /**
   * When the "last line" ends with a return, the next empty line will also be returned, as it
   * should. Returned lines do not end with return chars (LF, CR or CRLF).
   *
   * @see Enumeration#nextElement()
   */
  @Override
  public Object nextElement() {
    // skip to next line
    offset = eolOffset + separator.length();
    // find the next seperator
    eolOffset = s.indexOf(separator, offset);
    // not found, set to last char (the last line doesn't need have a \n or \r)
    if (eolOffset == -1) {
      eolOffset = s.length();
    }
    return s.substring(offset, eolOffset);
  }
}
