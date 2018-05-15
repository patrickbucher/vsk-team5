/**
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 */
package org.bitstorm.gameoflife;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;
import ch.hslu.vsk.logger.Logger;
import ch.hslu.vsk18fs.g05.loggerclient.Logging;

/**
 * Contains the cellgrid, the current shape and the Game Of Life algorithm that changes it.
 *
 * @author Edwin Martin
 */
public class GameOfLifeGrid implements CellGrid {

  private int cellCols;
  private int cellRows;
  /**
   * Contains the current, living shape. It's implemented as a hashtable. Tests showed this is 70%
   * faster than Vector.
   */
  private final Hashtable<Cell, Cell> currentShape;
  private int generations;
  /**
   * Every cell on the grid is a Cell object. This object can become quite large.
   */
  private Cell[][] grid;
  private final Hashtable<Cell, Cell> nextShape;

  final Logger logger = Logging.getLogger();

  /**
   * Contructs a GameOfLifeGrid.
   *
   * @param cellCols number of columns
   * @param cellRows number of rows
   */
  public GameOfLifeGrid(final int cellCols, final int cellRows) {
    this.cellCols = cellCols;
    this.cellRows = cellRows;
    this.currentShape = new Hashtable<>();
    this.nextShape = new Hashtable<>();

    this.grid = new Cell[cellCols][cellRows];
    for (int c = 0; c < cellCols; c++) {
      for (int r = 0; r < cellRows; r++) {
        this.grid[c][r] = new Cell(c, r);
      }
    }
  }

  /**
   * Adds a new neighbour to a cell.
   *
   * @param col Cell-column
   * @param row Cell-row
   */
  public synchronized void addNeighbour(final int col, final int row) {
    try {
      final Cell cell = this.nextShape.get(this.grid[col][row]);
      if (cell == null) {
        // Cell is not in hashtable, then add it
        final Cell c = this.grid[col][row];
        c.neighbour = 1;
        this.nextShape.put(c, c);
      } else {
        // Else, increments neighbour count
        cell.neighbour++;
      }
    } catch (final ArrayIndexOutOfBoundsException e) {
      // ignore
    }
  }

  /**
   * Clears grid.
   */
  @Override
  public synchronized void clear() {
    this.generations = 0;
    this.currentShape.clear();
    this.nextShape.clear();
    this.logger.debug("grid cleared");
  }

  /**
   * Get value of cell.
   *
   * @param col x-coordinate of cell
   * @param row y-coordinate of cell
   * @return value of cell
   */
  @Override
  public synchronized boolean getCell(final int col, final int row) {
    try {
      this.logger.trace("get cell: col " + col + ", row " + row);
      return this.currentShape.containsKey(this.grid[col][row]);
    } catch (final ArrayIndexOutOfBoundsException e) {
      // ignore
    }
    return false;
  }

  /**
   * Get dimension of grid.
   *
   * @return dimension of grid
   */
  @Override
  public Dimension getDimension() {
    this.logger.debug(
        "dimensions: number of columns: " + this.cellCols + ", number of rows " + this.cellRows);
    return new Dimension(this.cellCols, this.cellRows);
  }

  /**
   * Get enumeration of Cell's
   *
   * @see CellGrid#getEnum()
   */
  @Override
  public Enumeration getEnum() {
    return this.currentShape.keys();
  }

  /**
   * Get number of generations.
   *
   * @return number of generations
   */
  public int getGenerations() {
    return this.generations;
  }

  /**
   * Create next generation of shape.
   */
  public synchronized void next() {
    Cell cell;
    int col, row;
    Enumeration enumShape;

    this.generations++;
    this.nextShape.clear();

    // Reset cells
    enumShape = this.currentShape.keys();
    while (enumShape.hasMoreElements()) {
      cell = (Cell) enumShape.nextElement();
      cell.neighbour = 0;
    }
    // Add neighbours
    // You can't walk through an hashtable and also add elements. Took me a couple of ours to figure
    // out. Argh!
    // That's why we have a hashNew hashtable.
    enumShape = this.currentShape.keys();
    while (enumShape.hasMoreElements()) {
      cell = (Cell) enumShape.nextElement();
      col = cell.col;
      row = cell.row;
      this.addNeighbour(col - 1, row - 1);
      this.addNeighbour(col, row - 1);
      this.addNeighbour(col + 1, row - 1);
      this.addNeighbour(col - 1, row);
      this.addNeighbour(col + 1, row);
      this.addNeighbour(col - 1, row + 1);
      this.addNeighbour(col, row + 1);
      this.addNeighbour(col + 1, row + 1);
    }

    // Bury the dead
    // We are walking through an enum from we are also removing elements. Can be tricky.
    enumShape = this.currentShape.keys();
    while (enumShape.hasMoreElements()) {
      cell = (Cell) enumShape.nextElement();
      // Here is the Game Of Life rule (1):
      if (cell.neighbour != 3 && cell.neighbour != 2) {
        this.currentShape.remove(cell);
        this.logger.trace("cell " + this.currentShape + " removed");
      }
    }
    // Bring out the new borns
    enumShape = this.nextShape.keys();
    while (enumShape.hasMoreElements()) {
      cell = (Cell) enumShape.nextElement();
      // Here is the Game Of Life rule (2):
      if (cell.neighbour == 3) {
        this.setCell(cell.col, cell.row, true);
        this.logger.trace("cell: col " + cell.col + ", row " + cell.row + " generated");
      }
    }
    this.logger.trace("next generation generated!");
  }

  /**
   * Resize grid. Reuse existing cells.
   *
   * @see CellGrid#resize(int, int)
   */
  @Override
  public synchronized void resize(final int cellColsNew, final int cellRowsNew) {
    if (this.cellCols == cellColsNew && this.cellRows == cellRowsNew) {
      return; // Not really a resize
    }
    // Create a new grid, reusing existing Cell's
    final Cell[][] gridNew = new Cell[cellColsNew][cellRowsNew];
    for (int c = 0; c < cellColsNew; c++) {
      for (int r = 0; r < cellRowsNew; r++) {
        if (c < this.cellCols && r < this.cellRows) {
          gridNew[c][r] = this.grid[c][r];
          this.logger.trace("new cell " + gridNew[c][r] + " reused from former grid");

        } else {
          gridNew[c][r] = new Cell(c, r);
          this.logger.trace("new cell " + gridNew[c][r] + " appended");
        }
      }
    }

    // Copy existing shape to center of new shape
    final int colOffset = (cellColsNew - this.cellCols) / 2;
    final int rowOffset = (cellRowsNew - this.cellRows) / 2;
    Cell cell;
    Enumeration enumShape;
    this.nextShape.clear();
    enumShape = this.currentShape.keys();
    while (enumShape.hasMoreElements()) {
      cell = (Cell) enumShape.nextElement();
      final int colNew = cell.col + colOffset;
      final int rowNew = cell.row + rowOffset;
      try {
        this.nextShape.put(gridNew[colNew][rowNew], gridNew[colNew][rowNew]);
      } catch (final ArrayIndexOutOfBoundsException e) {
        // ignore
      }
    }

    // Copy new grid and hashtable to working grid/hashtable
    this.grid = gridNew;
    this.currentShape.clear();
    enumShape = this.nextShape.keys();
    while (enumShape.hasMoreElements()) {
      cell = (Cell) enumShape.nextElement();
      this.currentShape.put(cell, cell);
    }

    this.cellCols = cellColsNew;
    this.cellRows = cellRowsNew;
  }

  /**
   * Set value of cell.
   *
   * @param col x-coordinate of cell
   * @param row y-coordinate of cell
   * @param c value of cell
   */
  @Override
  public synchronized void setCell(final int col, final int row, final boolean c) {
    try {
      final Cell cell = this.grid[col][row];
      if (c) {
        this.currentShape.put(cell, cell);
        this.logger.debug("set new cell: col " + col + ", row " + row);
      } else {
        this.currentShape.remove(cell);
      }
    } catch (final ArrayIndexOutOfBoundsException e) {
      // ignore
    }
  }
}
