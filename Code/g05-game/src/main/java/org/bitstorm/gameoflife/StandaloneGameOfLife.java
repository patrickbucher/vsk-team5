/**
 * Game of Life v1.4 Standalone version The standalone version extends the applet version. Copyright
 * 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 *
 */
package org.bitstorm.gameoflife;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import org.bitstorm.util.AlertBox;
import org.bitstorm.util.EasyFile;
import org.bitstorm.util.LineEnumerator;

/**
 * The window with the applet. Extra is the menu bar.
 *
 * @author Edwin Martin
 */
class AppletFrame extends Frame {

  private final GameOfLife applet;

  /**
   * Constructor.
   *
   * @param title title of window
   * @param applet applet to show
   */
  public AppletFrame(final String title, final StandaloneGameOfLife applet) {
    super(title);
    this.applet = applet;

    final URL iconURL = this.getClass().getResource("icon.gif");
    final Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
    setIconImage(icon);

    enableEvents(Event.WINDOW_DESTROY);

    final MenuBar menubar = new MenuBar();
    final Menu fileMenu = new Menu("File", true);
    final MenuItem readMenuItem = new MenuItem("Open...");
    readMenuItem.addActionListener(new ActionListener() {
      @Override
      public synchronized void actionPerformed(final ActionEvent e) {
        getStandaloneGameOfLife().getGameOfLifeGridIO().openShape();
        getStandaloneGameOfLife().reset();
      }
    });
    final MenuItem writeMenuItem = new MenuItem("Save...");
    writeMenuItem.addActionListener(new ActionListener() {
      @Override
      public synchronized void actionPerformed(final ActionEvent e) {
        getStandaloneGameOfLife().getGameOfLifeGridIO().saveShape();
      }
    });
    final MenuItem quitMenuItem = new MenuItem("Exit");
    quitMenuItem.addActionListener(new ActionListener() {
      @Override
      public synchronized void actionPerformed(final ActionEvent e) {
        dispose();
      }
    });
    fileMenu.add(readMenuItem);
    fileMenu.add(writeMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(quitMenuItem);
    menubar.add(fileMenu);

    final GridBagLayout gridbag = new GridBagLayout();
    final GridBagConstraints appletContraints = new GridBagConstraints();
    setLayout(gridbag);
    appletContraints.fill = GridBagConstraints.BOTH;
    appletContraints.weightx = 1;
    appletContraints.weighty = 1;
    gridbag.setConstraints(applet, appletContraints);
    setMenuBar(menubar);
    setResizable(true);
    add(applet);
    final Toolkit screen = getToolkit();
    final Dimension screenSize = screen.getScreenSize();
    // Java in Windows opens windows in the upper left corner, which is ugly! Center instead.
    if (screenSize.width >= 640 && screenSize.height >= 480) {
      setLocation((screenSize.width - 550) / 2, (screenSize.height - 400) / 2);
    }
    applet.init(this);
    applet.start();
    pack();
    // Read shape after initialization
    applet.readShape();
    // Bring to front. Sometimes it stays behind other windows.
    setVisible(true);
    toFront();
  }

  /**
   * Get StandaloneGameOfLife object.
   *
   * @return StandaloneGameOfLife
   */
  private StandaloneGameOfLife getStandaloneGameOfLife() {
    return (StandaloneGameOfLife) applet;
  }

  /**
   * Process close window button.
   *
   * @see java.awt.Component#processEvent(AWTEvent)
   */
  @Override
  public void processEvent(final AWTEvent e) {
    if (e.getID() == Event.WINDOW_DESTROY) {
      dispose();
    }
  }
}


/**
 * Turns GameOfLife applet into application. It adds a menu, a window, drag-n-drop etc. It can be
 * run stand alone.
 *
 * @author Edwin Martin
 */
public class StandaloneGameOfLife extends GameOfLife {

  /**
   * File open and save operations for GameOfLifeGrid.
   */
  class GameOfLifeGridIO {

    public final String FILE_EXTENSION = ".cells";
    private String filename;
    private final GameOfLifeGrid grid;

    /**
     * Contructor.
     *
     * @param grid grid to read/write files from/to
     */
    public GameOfLifeGridIO(final GameOfLifeGrid grid) {
      this.grid = grid;
    }

    /**
     * "Draw" the shape on the grid. (Okay, it's not really drawing). The lines of text represent
     * the cells of the shape.
     *
     * @param name name of shape
     * @param text lines of text
     */
    public Shape makeShape(final String name, final String text) {
      int col = 0;
      int row = 0;
      boolean cell;
      // Cope with different line endings ("\r\n", "\r", "\n")
      Integer[][] cellArray;
      final Vector<Integer[]> cells = new Vector<>();

      if (text.length() == 0) {
        return null;
      }

      grid.clear();

      final Enumeration enumLine = new LineEnumerator(text);
      while (enumLine.hasMoreElements()) {
        final String line = (String) enumLine.nextElement();
        if (line.startsWith("#") || line.startsWith("!")) {
          continue;
        }

        final char[] ca = line.toCharArray();
        for (col = 0; col < ca.length; col++) {
          switch (ca[col]) {
            case '*':
            case 'O':
            case 'o':
            case 'X':
            case 'x':
            case '1':
              cell = true;
              break;
            default:
              cell = false;
              break;
          }
          if (cell) {
            cells.addElement(new Integer[] {col, row});
          }
        }
        row++;
      }

      cellArray = new Integer[cells.size()][];
      for (int i = 0; i < cells.size(); i++) {
        cellArray[i] = cells.get(i);
      }
      return new Shape(name, cellArray);
    }

    /**
     * Load shape from disk
     */
    public void openShape() {
      openShape((String) null);
    }

    /**
     * Use EasyFile object to read GameOfLife-file from.
     *
     * @param file EasyFile-object
     * @throws IOException
     * @see EasyFile
     */
    public void openShape(final EasyFile file) throws IOException {
      final Shape shape = makeShape(file.getFileName(), file.readText());
      setShape(shape);
    }

    /**
     * Load shape from disk
     *
     * @param filename filename to load shape from, or null when no filename given.
     */
    public void openShape(final String filename) {
      EasyFile file;
      try {
        if (filename != null) {
          file = new EasyFile(filename);
        } else {
          file = new EasyFile(appletFrame, "Open Game of Life file");
        }
        openShape(file);
      } catch (final FileNotFoundException e) {
        new AlertBox(appletFrame, "File not found", "Couldn't open this file.\n" + e.getMessage());
      } catch (final IOException e) {
        new AlertBox(appletFrame, "File read error", "Couldn't read this file.\n" + e.getMessage());
      }
    }

    /**
     * Open shape from URL.
     *
     * @param url URL pointing to GameOfLife-file
     */
    public void openShape(final URL url) {
      EasyFile file;
      try {
        if (url != null) {
          file = new EasyFile(url);
          openShape(file);
        }
      } catch (final FileNotFoundException e) {
        new AlertBox(appletFrame, "URL not found", "Couldn't open this URL.\n" + e.getMessage());
      } catch (final IOException e) {
        new AlertBox(appletFrame, "URL read error", "Couldn't read this URL.\n" + e.getMessage());
      }
    }

    /**
     * Write shape to disk.
     */
    public void saveShape() {
      int colEnd = 0;
      int rowEnd = 0;
      final Dimension dim = grid.getDimension();
      int colStart = dim.width;
      int rowStart = dim.height;

      final String lineSeperator = System.getProperty("line.separator");
      final StringBuilder text =
          new StringBuilder("!Generator: Game of Life (http://www.bitstorm.org/gameoflife/)"
              + lineSeperator + "!Variation: 23/3" + lineSeperator + "!" + lineSeperator);

      for (int row = 0; row < dim.height; row++) {
        for (int col = 0; col < dim.width; col++) {
          if (grid.getCell(col, row)) {
            if (row < rowStart) {
              rowStart = row;
            }
            if (col < colStart) {
              colStart = col;
            }
            if (row > rowEnd) {
              rowEnd = row;
            }
            if (col > colEnd) {
              colEnd = col;
            }
          }
        }
      }

      for (int row = rowStart; row <= rowEnd; row++) {
        for (int col = colStart; col <= colEnd; col++) {
          text.append(grid.getCell(col, row) ? 'O' : '-');
        }
        text.append(lineSeperator);
      }
      EasyFile file;
      try {
        file = new EasyFile(appletFrame, "Save Game of Life file");
        file.setFileName(filename);
        file.setFileExtension(FILE_EXTENSION);
        file.writeText(text.toString());
      } catch (final FileNotFoundException e) {
        new AlertBox(appletFrame, "File error", "Couldn't open this file.\n" + e.getMessage());
      } catch (final IOException e) {
        new AlertBox(appletFrame, "File error", "Couldn't write to this file.\n" + e.getMessage());
      }
    }

    /**
     * Set a shape and optionally resizes window.
     *
     * @param shape Shape to set
     */
    public void setShape(final Shape shape) {
      int width, height;
      final Dimension shapeDim = shape.getDimension();
      final Dimension gridDim = grid.getDimension();
      if (shapeDim.width > gridDim.width || shapeDim.height > gridDim.height) {
        // Window has to be made larger
        final Toolkit toolkit = getToolkit();
        final Dimension screenDim = toolkit.getScreenSize();
        final Dimension frameDim = appletFrame.getSize();
        final int cellSize = getCellSize();
        // Calculate new window size
        width = frameDim.width + cellSize * (shapeDim.width - gridDim.width);
        height = frameDim.height + cellSize * (shapeDim.height - gridDim.height);
        // Does it fit on the screen?
        if (width > screenDim.width || height > screenDim.height) {
          // With current cellSize, it doesn't fit on the screen
          // GameOfLifeControls.SIZE_SMALL corresponds with GameOfLifeControls.SMALL
          final int newCellSize = GameOfLifeControls.SIZE_SMALL;
          width = frameDim.width + newCellSize * shapeDim.width - cellSize * gridDim.width;
          height = frameDim.height + newCellSize * shapeDim.height - cellSize * gridDim.height;
          // a little kludge to prevent de window from resizing twice
          // setNewCellSize only has effect at the next resize
          gameOfLifeCanvas.setAfterWindowResize(shape, newCellSize);
          // The UI has to be adjusted, too
          controls.setZoom(GameOfLifeControls.SMALL);
        } else {
          // Now resize the window (and optionally set the new cellSize)
          gameOfLifeCanvas.setAfterWindowResize(shape, cellSize);
        }
        if (width < 400) {
          width = 400;
        }
        appletFrame.setSize(width, height);
        return;
      }
      try {
        gameOfLifeCanvas.setShape(shape);
      } catch (final ShapeException e) {
        // ignore
      }
    }
  }
  /**
   * Handles drag and drops to the canvas.
   *
   * This class does handle the dropping of files and URL's to the canvas. The code is based on the
   * dnd-code from the book Professional Java Programming by Brett Spell.
   *
   * @author Edwin Martin
   *
   */
  class MyDropListener implements DropTargetListener {

    private final DataFlavor urlFlavor =
        new DataFlavor("application/x-java-url; class=java.net.URL", "Game of Life URL");

    /**
     * The canvas only supports Files and URL's
     *
     * @see DropTargetListener#dragEnter(DropTargetDragEvent)
     */
    @Override
    public void dragEnter(final DropTargetDragEvent event) {
      if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
          || event.isDataFlavorSupported(urlFlavor)) {
        return;
      }
      event.rejectDrag();
    }

    /**
     * @see DropTargetListener#dragExit(DropTargetEvent)
     */
    @Override
    public void dragExit(final DropTargetEvent event) {}

    /**
     * @see DropTargetListener#dragOver(DropTargetDragEvent)
     */
    @Override
    public void dragOver(final DropTargetDragEvent event) {}

    /**
     * The file or URL has been dropped.
     *
     * @see DropTargetListener#drop(DropTargetDropEvent)
     */
    @Override
    public void drop(final DropTargetDropEvent event) {
      // important to first try urlFlavor
      if (event.isDataFlavorSupported(urlFlavor)) {
        try {
          event.acceptDrop(DnDConstants.ACTION_COPY);
          final Transferable trans = event.getTransferable();
          final URL url = (URL) trans.getTransferData(urlFlavor);
          url.toString();
          gridIO.openShape(url);
          reset();
          event.dropComplete(true);
        } catch (UnsupportedFlavorException | IOException e) {
          event.dropComplete(false);
        }
      } else if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
        try {
          event.acceptDrop(DnDConstants.ACTION_COPY);
          final Transferable trans = event.getTransferable();
          final List list = (List) trans.getTransferData(DataFlavor.javaFileListFlavor);
          final File droppedFile = (File) list.get(0); // More than one file -> get only first file
          gridIO.openShape(droppedFile.getPath());
          reset();
          event.dropComplete(true);
        } catch (UnsupportedFlavorException | IOException e) {
          event.dropComplete(false);
        }
      }
    }

    /**
     * @see DropTargetListener#dropActionChanged(DropTargetDragEvent)
     */
    @Override
    public void dropActionChanged(final DropTargetDragEvent event) {}
  }

  /**
   * main() for standalone version.
   *
   * @param args Not used.
   */
  public static void main(final String args[]) {
    final StandaloneGameOfLife gameOfLife = new StandaloneGameOfLife();
    gameOfLife.args = args;
    new AppletFrame("Game of Life", gameOfLife);
  }

  private Frame appletFrame;

  private String[] args;

  private GameOfLifeGridIO gridIO;

  /**
   * Shows an alert
   *
   * @param s text to show
   */
  @Override
  public void alert(final String s) {
    new AlertBox(appletFrame, "Alert", s);
  }

  /**
   * get GameOfLifeGridIO
   *
   * @return GameOfLifeGridIO object
   */
  protected GameOfLifeGridIO getGameOfLifeGridIO() {
    return gridIO;
  }

  /**
   * Override method, called by applet.
   *
   * @see java.applet.Applet#getParameter(String)
   */
  @Override
  public String getParameter(final String parm) {
    return System.getProperty(parm);
  }

  /**
   * Initialize UI.
   *
   * @param parent Parent frame.
   * @see java.applet.Applet#init()
   */
  public void init(final Frame parent) {
    appletFrame = parent;
    getParams();

    // set background colour
    setBackground(new Color(0x999999));

    // TODO: casten naar interface
    // create StandAloneGameOfLifeGrid
    gameOfLifeGrid = new GameOfLifeGrid(cellCols, cellRows);
    gridIO = new GameOfLifeGridIO(gameOfLifeGrid);

    // create GameOfLifeCanvas
    gameOfLifeCanvas = new CellGridCanvas(gameOfLifeGrid, cellSize);

    try {
      // Make GameOfLifeCanvas a drop target
      new DropTarget(gameOfLifeCanvas, DnDConstants.ACTION_COPY_OR_MOVE, new MyDropListener());
    } catch (final NoClassDefFoundError e) {
      e.printStackTrace();
    }

    // create GameOfLifeControls
    controls = new GameOfLifeControls();
    controls.addGameOfLifeControlsListener(this);

    // put it all together
    final GridBagLayout gridBag = new GridBagLayout();
    final GridBagConstraints canvasConstraints = new GridBagConstraints();
    setLayout(gridBag);
    canvasConstraints.fill = GridBagConstraints.BOTH;
    canvasConstraints.weightx = 1;
    canvasConstraints.weighty = 1;
    canvasConstraints.gridx = GridBagConstraints.REMAINDER;
    canvasConstraints.gridy = 0;
    canvasConstraints.anchor = GridBagConstraints.CENTER;
    gridBag.setConstraints(gameOfLifeCanvas, canvasConstraints);
    add(gameOfLifeCanvas);
    final GridBagConstraints controlsContraints = new GridBagConstraints();
    canvasConstraints.gridx = GridBagConstraints.REMAINDER;
    canvasConstraints.gridy = 1;
    controlsContraints.gridx = GridBagConstraints.REMAINDER;
    gridBag.setConstraints(controls, controlsContraints);
    add(controls);
    setVisible(true);
    validate();
  }

  /**
   * Set the shape.
   *
   * This is not done in init(), because the window resize in GameOfLifeGridIO.setShape(Shape) needs
   * a fully opened window to do new size calculations.
   */
  public void readShape() {
    if (args.length > 0) {
      gridIO.openShape(args[0]);
      reset();
    } else {
      try {
        setShape(ShapeCollection.getShapeByName("Glider"));
      } catch (final ShapeException e) {
        // Ignore. It's not going to happen here.
      }
    }
  }

  /**
   * Do not use showStatus() of the applet.
   *
   * @see java.applet.Applet#showStatus(String)
   */
  @Override
  public void showStatus(final String s) {
    // do nothing
  }
}
