package org.bitstorm.gameoflife;

/**
 * Every cell in the grid is a Cell-object. So it must be as small as possible. Because every cell
 * is pre-generated, no cells have to be generated when the Game Of Life playw. Whether a cell is
 * alive or not, is not part of the Cell-object.
 *
 * @author Edwin Martin
 *
 */
public class Cell {

  /**
   * HASHFACTOR must be larger than the maximum number of columns (that is: the max width of a
   * monitor in pixels). It should also be smaller than 65536. (sqrt(MAXINT)).
   */
  private static final int HASHFACTOR = 5000;
  public final short col;
  /**
   * Number of neighbours of this cell.
   *
   * Determines the next state.
   */
  public byte neighbour; // Neighbour is International English

  public final short row;

  /**
   * Constructor
   *
   * @param col column of cell
   * @param row row or cell
   */
  public Cell(final int col, final int row) {
    this.col = (short) col;
    this.row = (short) row;
    neighbour = 0;
  }

  /**
   * Compare cell-objects for use in hashtables
   *
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof Cell)) {
      return false;
    }
    return col == ((Cell) o).col && row == ((Cell) o).row;
  }

  /**
   * Calculate hash for use in hashtables
   *
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HASHFACTOR * row + col;
  }

  /**
   * @see Object#toString()
   */
  @Override
  public String toString() {
    return "Cell at (" + col + ", " + row + ") with " + neighbour + " neighbour"
        + (neighbour == 1 ? "" : "s");
  }
}
