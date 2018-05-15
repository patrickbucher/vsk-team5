/*
 * Easily show an alert. Copyright 2003 Edwin Martin <edwin@bitstorm.org>
 *
 */
package org.bitstorm.util;

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.util.StringTokenizer;

/**
 * AlertBox shows a message besides a warning symbol.
 *
 * @author Edwin Martin
 */
public class AlertBox extends Dialog {

  /**
   * Contructs a AlertBox.
   * <p>
   * Use the newline character '\n' in the message to seperate lines.
   *
   * @param parent parent frame
   * @param title title of the dialog box
   * @param message the message to show
   */
  public AlertBox(final Frame parent, final String title, final String message) {
    super(parent, title, false);

    final Button okButton;
    okButton = new Button(" OK ");
    okButton.addActionListener(e -> close());
    final Panel buttonPanel = new Panel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(okButton);
    final StringTokenizer st = new StringTokenizer(message, "\n");
    final Panel messagePanel = new Panel(new GridLayout(st.countTokens(), 1));
    while (st.hasMoreTokens()) {
      messagePanel.add(new Label(st.nextToken()));
    }
    add("Center", messagePanel);
    add("South", buttonPanel);
    enableEvents(Event.WINDOW_DESTROY);
    setResizable(false);
    setModal(true);
    pack();
    final Point p = parent.getLocation();
    final Dimension dim = parent.getSize();
    setLocation(p.x + dim.width / 2 - 150, p.y + dim.height / 2 - 75);
    setVisible(true);
  }

  /**
   * Close dialog box.
   */
  private void close() {
    setVisible(true);
    dispose();
  }

  /**
   * Process close window button.
   *
   * @see java.awt.Component#processEvent(AWTEvent)
   */
  @Override
  public void processEvent(final AWTEvent e) {
    if (e.getID() == Event.WINDOW_DESTROY) {
      close();
    }
  }
}
