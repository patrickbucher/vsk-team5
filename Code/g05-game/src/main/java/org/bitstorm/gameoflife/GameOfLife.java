/**
 * Game of Life v1.4 Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl> version 1.0 online since
 * July 3 1996 Changes: 1.1: Double buffering to screen; faster paint 1.2: Arrowkeys changed; better
 * use of `synchronized' 1.3: Choose speed from drop down menu and draw with mouse 1.4: Use Java 1.1
 * events, remove 13 deprecated methods, some refactoring. 2003-11-08 1.5: Lots of refactoring,
 * zooming, small improvements
 *
 * @author Edwin Martin
 *
 */
package org.bitstorm.gameoflife;

import java.applet.Applet;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import ch.hslu.vsk.logger.Logger;
import ch.hslu.vsk18fs.g05.loggerclient.Logging;

/**
 * The Game Of Life Applet. This is the heart of the program. It initializes everything en put it
 * together.
 *
 * @author Edwin Martin
 */
public class GameOfLife extends Applet implements Runnable, GameOfLifeControlsListener {

  private static Thread gameThread;
  protected int cellCols;
  protected int cellRows;
  protected int cellSize;
  protected GameOfLifeControls controls;
  protected CellGridCanvas gameOfLifeCanvas;
  protected GameOfLifeGrid gameOfLifeGrid;

  protected int genTime;

  final Logger logger = Logging.getLogger();

  /**
   * Shows an alert
   *
   * @param s text to show
   */
  public void alert(final String s) {
    this.showStatus(s);
  }

  /**
   * @see Applet#getAppletInfo()
   */
  @Override
  public String getAppletInfo() {
    return "Game Of Life v. 1.5\nCopyright 1996-2004 Edwin Martin";
  }

  /**
   * Gets cell size.
   *
   * @return size of cell
   */
  public int getCellSize() {
    this.logger.info("cell size: " + this.getCellSize());
    return this.cellSize;
  }

  /**
   * Read applet parameter (int) or, when unavailable, get default value.
   *
   * @param name name of parameter
   * @param defaultParam default when parameter is unavailable
   * @return value of parameter
   */
  protected int getParamInteger(final String name, final int defaultParam) {
    String param;
    int paramInt;

    param = this.getParameter(name);
    if (param == null) {
      paramInt = defaultParam;
    } else {
      paramInt = Integer.parseInt(param);
    }
    return paramInt;
  }

  /**
   * Get params (cellSize, cellCols, cellRows, genTime) from applet-tag
   */
  protected void getParams() {
    this.cellSize = this.getParamInteger("cellsize", 11);
    this.cellCols = this.getParamInteger("cellcols", 50);
    this.cellRows = this.getParamInteger("cellrows", 30);
    this.genTime = this.getParamInteger("gentime", 1000);
  }

  /**
   * Initialize UI.
   *
   * @see Applet#init()
   */
  @Override
  public void init() {
    this.getParams();

    // set background colour
    this.setBackground(new Color(0x999999));

    // create gameOfLifeGrid
    this.gameOfLifeGrid = new GameOfLifeGrid(this.cellCols, this.cellRows);
    this.gameOfLifeGrid.clear();

    this.logger.info("Grid created: " + this.cellCols + " x " + this.cellRows);

    // create GameOfLifeCanvas
    this.gameOfLifeCanvas = new CellGridCanvas(this.gameOfLifeGrid, this.cellSize);
    this.logger
        .info("Canvas created: Grid: " + this.gameOfLifeGrid + " , cellSize: " + this.cellSize);

    // create GameOfLifeControls
    this.controls = new GameOfLifeControls();
    this.controls.addGameOfLifeControlsListener(this);

    // put it all together
    final GridBagLayout gridbag = new GridBagLayout();
    this.setLayout(gridbag);
    final GridBagConstraints canvasContraints = new GridBagConstraints();

    canvasContraints.fill = GridBagConstraints.BOTH;
    canvasContraints.gridx = GridBagConstraints.REMAINDER;
    canvasContraints.gridy = 0;
    canvasContraints.weightx = 1;
    canvasContraints.weighty = 1;
    canvasContraints.anchor = GridBagConstraints.CENTER;
    gridbag.setConstraints(this.gameOfLifeCanvas, canvasContraints);
    this.add(this.gameOfLifeCanvas);

    final GridBagConstraints controlsContraints = new GridBagConstraints();
    canvasContraints.gridy = 1;
    canvasContraints.gridx = 0;
    controlsContraints.gridx = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(this.controls, controlsContraints);
    this.add(this.controls);

    try {
      // Start with a shape (My girlfriend clicked "Start" on a blank screen and wondered why
      // nothing happened).
      this.setShape(ShapeCollection.getShapeByName("Glider"));
      this.logger.info("default shape selected");
    } catch (final ShapeException e) {
      // Ignore. Not going to happen.
      this.logger.info("This did not happen here.");
    }
    this.setVisible(true);
    this.validate();
  }

  /**
   * Is the applet running?
   *
   * @return true: applet is running
   */
  public boolean isRunning() {
    return gameThread != null;
  }

  /**
   * Callback from GameOfLifeControlsListener
   *
   * @see GameOfLifeControlsListener#nextButtonClicked(GameOfLifeControlsEvent)
   */
  @Override
  public void nextButtonClicked(final GameOfLifeControlsEvent e) {
    this.nextGeneration();
    this.logger.info("next button clicked");
  }

  /**
   * Go to the next generation.
   */
  public void nextGeneration() {
    this.gameOfLifeGrid.next();
    this.gameOfLifeCanvas.repaint();
    this.showGenerations();
    this.logger.info("one step further");
  }

  /**
   * Resets applet (after loading new shape)
   */
  public void reset() {
    this.stop(); // might otherwise confuse user
    this.gameOfLifeCanvas.repaint();
    this.showGenerations();
    this.showStatus("");

    this.logger.info("reset game");
  }

  /**
   * @see Runnable#run()
   */
  @Override
  public synchronized void run() {
    while (gameThread != null) {
      this.nextGeneration();
      try {
        Thread.sleep(this.genTime);
      } catch (final InterruptedException e) {
        e.printStackTrace();

        this.logger.error("exception: game thread interrupted");

      }
    }
  }

  /**
   * Sets cell size.
   *
   * @param p size of cell in pixels
   */
  public void setCellSize(final int p) {
    this.cellSize = p;
    this.gameOfLifeCanvas.setCellSize(this.cellSize);
    this.logger.info("cell size changed to " + this.cellSize);
  }

  /**
   * Set the new shape
   *
   * @param shape name of shape
   */
  public void setShape(final Shape shape) {
    if (shape == null) {
      this.logger.warning("tried to set empty shape");
      return;
    }

    try {
      this.gameOfLifeCanvas.setShape(shape);
      this.reset();
      this.logger.info("Set shape to: " + shape.getName());
    } catch (final ShapeException e) {
      this.alert(e.getMessage());
      this.logger.error("Something went wrong :( " + e.getMessage());
    }
  }

  /**
   * Set speed of new generations.
   *
   * @param fps generations per second
   */
  public void setSpeed(final int fps) {
    this.genTime = fps;
    this.logger.info("set speed to: " + fps);
  }

  /**
   * Callback from GameOfLifeControlsListener
   *
   * @see GameOfLifeControlsListener#shapeSelected(GameOfLifeControlsEvent)
   */
  @Override
  public void shapeSelected(final GameOfLifeControlsEvent e) {
    final String shapeName = e.getShapeName();
    Shape shape;
    try {
      shape = ShapeCollection.getShapeByName(shapeName);
      this.setShape(shape);
    } catch (final ShapeException e1) {
      // Ignore. Not going to happen.
      this.logger.critical(e1.getMessage());
    }
  }

  /**
   * Show number of generations.
   */
  private void showGenerations() {
    this.controls.setGeneration(this.gameOfLifeGrid.getGenerations());
    this.logger.info("number of generations: " + this.gameOfLifeGrid.getGenerations());
  }

  /**
   * Callback from GameOfLifeControlsListener
   *
   * @see GameOfLifeControlsListener#speedChanged(GameOfLifeControlsEvent)
   */
  @Override
  public void speedChanged(final GameOfLifeControlsEvent e) {
    this.setSpeed(e.getSpeed());
    this.logger.info("speed changed to:" + e.getSpeed());
  }

  /**
   * Starts creating new generations. No start() to prevent starting immediately.
   */
  public synchronized void start2() {
    this.controls.start();
    if (gameThread == null) {
      gameThread = new Thread(this);
      gameThread.start();

      this.logger.debug("game thread started");
    }
  }

  /**
   * Callback from GameOfLifeControlsListener
   *
   * @see GameOfLifeControlsListener#startStopButtonClicked(GameOfLifeControlsEvent)
   */
  @Override
  public void startStopButtonClicked(final GameOfLifeControlsEvent e) {
    if (this.isRunning()) {
      this.stop();
      this.logger.info("stop button clicked");
    } else {
      this.start2();
      this.logger.info("start button clicked");
    }
  }

  /**
   * @see Applet#stop()
   */
  @Override
  public void stop() {
    this.controls.stop();
    gameThread = null;
    this.logger.debug("game thread stopped");
  }

  /**
   * Callback from GameOfLifeControlsListener
   *
   * @see GameOfLifeControlsListener#speedChanged(GameOfLifeControlsEvent)
   */
  @Override
  public void zoomChanged(final GameOfLifeControlsEvent e) {
    this.setCellSize(e.getZoom());
    this.logger.info("zoom changed to: " + e.getZoom());
  }
}
