/**
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 */
package org.bitstorm.gameoflife;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;

/**
 * Subclass of Canvas, which makes the cellgrid visible. Communicates via CellGrid interface.
 *
 * @author Edwin Martin
 */
public class CellGridCanvas extends Canvas {

  private final CellGrid cellGrid;
  private int cellSize;
  private boolean cellUnderMouse;
  private int newCellSize;
  private Shape newShape;
  private Graphics offScreenGraphics;
  /**
   * Image, containing the drawed grid.
   */
  private Graphics offScreenGraphicsDrawed;
  /**
   * Image for double buffering, to prevent flickering.
   */
  private Image offScreenImage;
  private Image offScreenImageDrawed;

  /**
   * Constructs a CellGridCanvas.
   *
   * @param cellGrid the GoL cellgrid
   * @param cellSize size of cell in pixels
   */
  public CellGridCanvas(final CellGrid cellGrid, final int cellSize) {
    this.cellGrid = cellGrid;
    this.cellSize = cellSize;

    setBackground(new Color(0x999999));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(final MouseEvent e) {
        saveCellUnderMouse(e.getX(), e.getY());
      }

      @Override
      public void mouseReleased(final MouseEvent e) {
        draw(e.getX(), e.getY());
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(final MouseEvent e) {
        draw(e.getX(), e.getY());
      }
    });
    addComponentListener(new ComponentListener() {
      @Override
      public void componentHidden(final ComponentEvent e) {}

      @Override
      public void componentMoved(final ComponentEvent e) {}

      @Override
      public void componentResized(final ComponentEvent e) {
        resized();
        repaint();
      }

      @Override
      public void componentShown(final ComponentEvent e) {}
    });

  }

  /**
   * Draw in this cell.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public void draw(final int x, final int y) {
    try {
      cellGrid.setCell(x / cellSize, y / cellSize, !cellUnderMouse);
      repaint();
    } catch (final ArrayIndexOutOfBoundsException e) {
      // ignore
    }
  }

  /**
   * This is the minimum size (size of one cell).
   *
   * @see java.awt.Component#getMinimumSize()
   */
  @Override
  public Dimension getMinimumSize() {
    return new Dimension(cellSize, cellSize);
  }

  /**
   * This is the preferred size.
   *
   * @see java.awt.Component#getPreferredSize()
   */
  @Override
  public Dimension getPreferredSize() {
    final Dimension dim = cellGrid.getDimension();
    return new Dimension(cellSize * dim.width, cellSize * dim.height);
  }

  /**
   * Draw this generation.
   *
   * @see java.awt.Component#paint(Graphics)
   */
  @Override
  public void paint(final Graphics g) {
    // Draw grid on background image, which is faster
    if (offScreenImageDrawed == null) {
      final Dimension dim = cellGrid.getDimension();
      final Dimension d = getSize();
      offScreenImageDrawed = createImage(d.width, d.height);
      offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics();
      // draw background (MSIE doesn't do that)
      offScreenGraphicsDrawed.setColor(getBackground());
      offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height);
      offScreenGraphicsDrawed.setColor(Color.gray);
      offScreenGraphicsDrawed.fillRect(0, 0, cellSize * dim.width - 1, cellSize * dim.height - 1);
      offScreenGraphicsDrawed.setColor(getBackground());
      for (int x = 1; x < dim.width; x++) {
        offScreenGraphicsDrawed.drawLine(x * cellSize - 1, 0, x * cellSize - 1,
            cellSize * dim.height - 1);
      }
      for (int y = 1; y < dim.height; y++) {
        offScreenGraphicsDrawed.drawLine(0, y * cellSize - 1, cellSize * dim.width - 1,
            y * cellSize - 1);
      }
    }
    g.drawImage(offScreenImageDrawed, 0, 0, null);
    // draw populated cells
    g.setColor(Color.yellow);
    final Enumeration enumGrid = cellGrid.getEnum();
    Cell c;
    while (enumGrid.hasMoreElements()) {
      c = (Cell) enumGrid.nextElement();
      g.fillRect(c.col * cellSize, c.row * cellSize, cellSize - 1, cellSize - 1);
    }
  }

  /**
   * The grid is resized (by window resize or zooming). Also apply post-resize properties when
   * necessary
   */
  public void resized() {
    if (newCellSize != 0) {
      cellSize = newCellSize;
      newCellSize = 0;
    }
    final Dimension canvasDim = this.getSize();
    offScreenImage = null;
    offScreenImageDrawed = null;
    cellGrid.resize(canvasDim.width / cellSize, canvasDim.height / cellSize);
    if (newShape != null) {
      try {
        setShape(newShape);
      } catch (final ShapeException e) {
        // ignore
      }
    }

  }

  /**
   * Remember state of cell for drawing.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public void saveCellUnderMouse(final int x, final int y) {
    try {
      cellUnderMouse = cellGrid.getCell(x / cellSize, y / cellSize);
    } catch (final ArrayIndexOutOfBoundsException e) {
      // ignore
    }
  }

  /**
   * Settings to appy after a window-resize.
   *
   * @param newShape new shape
   * @param newCellSize new cellSize
   */
  public void setAfterWindowResize(final Shape newShape, final int newCellSize) {
    this.newShape = newShape;
    this.newCellSize = newCellSize;
  }

  /**
   * Set cell size (zoom factor)
   *
   * @param cellSize Size of cell in pixels
   */
  public void setCellSize(final int cellSize) {
    this.cellSize = cellSize;
    resized();
    repaint();
  }

  /**
   * Draws shape in grid.
   *
   * @param shape name of shape
   * @throws ShapeException if the shape does not fit on the canvas
   */
  public synchronized void setShape(final Shape shape) throws ShapeException {
    int xOffset;
    int yOffset;
    Dimension dimShape;
    Dimension dimGrid;
    // get shape properties
    // shapeGrid = shape.getShape();
    dimShape = shape.getDimension();
    dimGrid = cellGrid.getDimension();

    if (dimShape.width > dimGrid.width || dimShape.height > dimGrid.height) {
      throw new ShapeException("Shape doesn't fit on canvas (grid: " + dimGrid.width + "x"
          + dimGrid.height + ", shape: " + dimShape.width + "x" + dimShape.height + ")"); // shape
                                                                                          // doesn't
                                                                                          // fit on
                                                                                          // canvas
    }
    // center the shape
    xOffset = (dimGrid.width - dimShape.width) / 2;
    yOffset = (dimGrid.height - dimShape.height) / 2;
    cellGrid.clear();

    // draw shape
    final Enumeration<Integer[]> cells = shape.getCells();
    while (cells.hasMoreElements()) {
      final Integer[] cell = cells.nextElement();
      cellGrid.setCell(xOffset + cell[0], yOffset + cell[1], true);
    }

  }

  /**
   * Use double buffering.
   *
   * @see java.awt.Component#update(Graphics)
   */
  @Override
  public void update(final Graphics g) {
    final Dimension d = getSize();
    if (offScreenImage == null) {
      offScreenImage = createImage(d.width, d.height);
      offScreenGraphics = offScreenImage.getGraphics();
    }
    paint(offScreenGraphics);
    g.drawImage(offScreenImage, 0, 0, null);
  }
}
