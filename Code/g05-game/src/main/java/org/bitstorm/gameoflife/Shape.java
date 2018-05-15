/**
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 */
package org.bitstorm.gameoflife;

import java.awt.Dimension;
import java.util.Enumeration;

/**
 * Shape contains data of one (predefined) shape.
 *
 * @author Edwin Martin
 */
public class Shape {

  private final String name;
  private final Integer[][] shape;

  /**
   * Constructa a Shape.
   *
   * @param name name of shape
   * @param shape shape data
   */
  public Shape(final String name, final Integer[][] shape) {
    this.name = name;
    this.shape = shape;
  }

  /**
   * Get shape data. Hide the shape implementation. Returns a anonymous Enumerator object.
   *
   * @return enumerated shape data
   */
  public Enumeration getCells() {
    return new Enumeration() {
      private int index;

      @Override
      public boolean hasMoreElements() {
        return index < shape.length;
      }

      @Override
      public Object nextElement() {
        return shape[index++];
      }
    };
  }

  /**
   * Get dimension of shape.
   *
   * @return dimension of the shape in cells
   */
  public Dimension getDimension() {
    int shapeWidth = 0;
    int shapeHeight = 0;
    for (final Integer[] element : shape) {
      if (element[0] > shapeWidth) {
        shapeWidth = element[0];
      }
      if (element[1] > shapeHeight) {
        shapeHeight = element[1];
      }
    }
    shapeWidth++;
    shapeHeight++;
    return new Dimension(shapeWidth, shapeHeight);
  }

  /**
   * Get name of shape.
   *
   * @return name of shape
   */
  public String getName() {
    return name;
  }

  /**
   * @see Object#toString()
   */
  @Override
  public String toString() {
    return name + " (" + shape.length + " cell" + (shape.length == 1 ? "" : "s") + ")";
  }
}
