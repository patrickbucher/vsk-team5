/**
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 */
package org.bitstorm.gameoflife;

/**
 * Exception for shapes (too big, not found...).
 *
 * @author Edwin Martin
 */
public class ShapeException extends Exception {

  /**
   * Constructs a ShapeException.
   */
  public ShapeException() {}

  /**
   * Constructs a ShapeException with a description.
   *
   * @param s description.
   */
  public ShapeException(final String s) {
    super(s);
  }
}
