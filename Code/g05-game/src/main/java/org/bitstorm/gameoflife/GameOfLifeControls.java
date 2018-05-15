/**
 * The control bar at the bottom. Is put in a seperate object, so it can be replaced by another UI,
 * e.g. on a J2ME phone. Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 */

package org.bitstorm.gameoflife;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Label;
import java.awt.Panel;
import java.util.Enumeration;
import java.util.Vector;

/**
 * GUI-controls of the Game of Life. It contains controls like Shape, zoom and speed selector, next
 * and start/stop-button. It is a seperate class, so it can be replaced by another implementation
 * for e.g. mobile phones or PDA's. Communicates via the GameOfLifeControlsListener.
 *
 * @author Edwin Martin
 */
public class GameOfLifeControls extends Panel {
  public static final String BIG = "Big";
  public static final String FAST = "Fast";
  private static final String genLabelText = "Generations: ";
  public static final String HYPER = "Hyper";
  public static final String MEDIUM = "Medium";
  public static final String RAPID = "Rapid";
  public static final int SIZE_BIG = 11;
  public static final int SIZE_MEDIUM = 7;
  public static final int SIZE_SMALL = 3;
  public static final String SLOW = "Slow";

  public static final String SMALL = "Small";
  private static final String stopLabelText = "Stop";

  private final Label genLabel;
  private final Vector listeners;
  private final Button nextButton;
  private final Choice shapesChoice;
  private final Button startStopButton;
  private final Choice zoomChoice;

  /**
   * Contructs the controls.
   */
  public GameOfLifeControls() {
    listeners = new Vector();

    // pulldown menu with shapes
    shapesChoice = new Choice();

    // Put names of shapes in menu
    final Shape[] shapes = ShapeCollection.getShapes();
    for (final Shape shape : shapes) {
      shapesChoice.addItem(shape.getName());
    }

    // when shape is selected
    shapesChoice.addItemListener(e -> shapeSelected((String) e.getItem()));

    // pulldown menu with speeds
    final Choice speedChoice = new Choice();

    // add speeds
    speedChoice.addItem(SLOW);
    speedChoice.addItem(FAST);
    speedChoice.addItem(RAPID);
    speedChoice.addItem(HYPER);

    // when item is selected
    speedChoice.addItemListener(e -> {
      final String arg = (String) e.getItem();
      if (null != arg) {
        switch (arg) {
          // slow
          case SLOW:
            speedChanged(1000);
            break;
          // fast
          case FAST:
            speedChanged(100);
            break;
          // rapid
          case RAPID:
            speedChanged(10);
            break;
          // hyperspeed
          case HYPER:
            speedChanged(1);
            break;
        }
      }
    });

    // pulldown menu with speeds
    zoomChoice = new Choice();

    // add speeds
    zoomChoice.addItem(BIG);
    zoomChoice.addItem(MEDIUM);
    zoomChoice.addItem(SMALL);

    // when item is selected
    zoomChoice.addItemListener(e -> {
      final String arg = (String) e.getItem();
      if (null != arg) {
        switch (arg) {
          case BIG:
            zoomChanged(SIZE_BIG);
            break;
          case MEDIUM:
            zoomChanged(SIZE_MEDIUM);
            break;
          case SMALL:
            zoomChanged(SIZE_SMALL);
            break;
        }
      }
    });

    // number of generations
    genLabel = new Label(genLabelText + "         ");

    // start and stop buttom

    final String startLabelText = "Start";
    startStopButton = new Button(startLabelText);

    // when start/stop button is clicked
    startStopButton.addActionListener(e -> startStopButtonClicked());

    // next generation button
    final String nextLabelText = "Next";
    nextButton = new Button(nextLabelText);

    // when next button is clicked
    nextButton.addActionListener(e -> nextButtonClicked());

    // create panel with controls
    this.add(shapesChoice);
    this.add(nextButton);
    this.add(startStopButton);
    this.add(speedChoice);
    this.add(zoomChoice);
    this.add(genLabel);
    validate();
  }

  /**
   * Add listener for this control
   *
   * @param listener Listener object
   */
  public void addGameOfLifeControlsListener(final GameOfLifeControlsListener listener) {
    listeners.addElement(listener);
  }

  /**
   * Called when the next-button is clicked. Notify event-listeners.
   */
  public void nextButtonClicked() {
    final GameOfLifeControlsEvent event = new GameOfLifeControlsEvent(this);
    for (final Enumeration e = listeners.elements(); e.hasMoreElements();) {
      ((GameOfLifeControlsListener) e.nextElement()).nextButtonClicked(event);
    }
  }

  /**
   * Remove listener from this control
   *
   * @param listener Listener object
   */
  public void removeGameOfLifeControlsListener(final GameOfLifeControlsListener listener) {
    listeners.removeElement(listener);
  }

  /**
   * Set the number of generations in the control bar.
   *
   * @param generations number of generations
   */
  public void setGeneration(final int generations) {
    genLabel.setText(genLabelText + generations + "         ");
  }

  /**
   * Called when a new cell size from the zoom pull down is selected. Notify event-listeners.
   *
   * @param n cell id.
   */
  public void setZoom(final String n) {
    zoomChoice.select(n);
  }

  /**
   * Called when a new shape from the shape pull down is selected. Notify event-listeners.
   *
   * @param shapeName new shape name.
   */
  public void shapeSelected(final String shapeName) {
    final GameOfLifeControlsEvent event =
        GameOfLifeControlsEvent.getShapeSelectedEvent(this, shapeName);
    for (final Enumeration e = listeners.elements(); e.hasMoreElements();) {
      ((GameOfLifeControlsListener) e.nextElement()).shapeSelected(event);
    }
  }

  /**
   * Called when a new speed from the speed pull down is selected. Notify event-listeners.
   *
   * @param speed new speed.
   */
  public void speedChanged(final int speed) {
    final GameOfLifeControlsEvent event = GameOfLifeControlsEvent.getSpeedChangedEvent(this, speed);
    for (final Enumeration e = listeners.elements(); e.hasMoreElements();) {
      ((GameOfLifeControlsListener) e.nextElement()).speedChanged(event);
    }
  }

  /**
   * Start-button is activated.
   */
  public void start() {
    startStopButton.setLabel(stopLabelText);
    nextButton.setEnabled(false);
    shapesChoice.setEnabled(false);
  }

  /**
   * Called when the start/stop-button is clicked. Notify event-listeners.
   */
  public void startStopButtonClicked() {
    final GameOfLifeControlsEvent event = new GameOfLifeControlsEvent(this);
    for (final Enumeration e = listeners.elements(); e.hasMoreElements();) {
      ((GameOfLifeControlsListener) e.nextElement()).startStopButtonClicked(event);
    }
  }

  /**
   * Stop-button is activated.
   */
  public void stop() {
    final String startLabelText = "Start";
    startStopButton.setLabel(startLabelText);
    nextButton.setEnabled(true);
    shapesChoice.setEnabled(true);
  }

  /**
   * Called when a new zoom from the zoom pull down is selected. Notify event-listeners.
   *
   * @param zoom new zoom.
   */
  public void zoomChanged(final int zoom) {
    final GameOfLifeControlsEvent event = GameOfLifeControlsEvent.getZoomChangedEvent(this, zoom);
    for (final Enumeration e = listeners.elements(); e.hasMoreElements();) {
      ((GameOfLifeControlsListener) e.nextElement()).zoomChanged(event);
    }
  }

}
