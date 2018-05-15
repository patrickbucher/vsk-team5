package ch.hslu.vsk18fs.g05.loggercomponent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ch.hslu.vsk.logger.LogLevel;
import ch.hslu.vsk.logger.Logger;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

public class LoggerComponentTest {

  private static final LogLevel FILTER_LEVEL = LogLevel.WARNING;
  private static final int BUFFER_SIZE = 1024 * 1024; // one megabyte, increase if needed
  private static final String lineSeparator = System.getProperty("line.separator");

  private Logger logger;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  @Before
  public void setUpMessageLoop() throws Exception {
    final PipedInputStream input = new PipedInputStream(BUFFER_SIZE);
    final PipedOutputStream output = new PipedOutputStream(input);

    final ObjectOutputStream objectOutput = new ObjectOutputStream(output);
    final ObjectInputStream objectInput = new ObjectInputStream(input);
    this.logger = LoggerComponent.create(FILTER_LEVEL, objectOutput, null);
    this.input = objectInput;
    this.output = objectOutput;
  }

  @Test
  public void testMessageLevelFiltering() throws Exception {
    this.logger.error("error");
    final Message errorMessage = (Message) this.input.readObject();
    Assert.assertNotNull(errorMessage);
    Assert.assertEquals("error", errorMessage.getMessage());

    this.logger.trace("whatever"); // omitted
    this.logger.debug("however"); // omitted
    this.logger.info("and ever"); // omitted
    this.logger.warning("seriously"); // transmitted
    final Message warningMessage = (Message) this.input.readObject();
    Assert.assertNotNull(warningMessage);
    Assert.assertEquals("seriously", warningMessage.getMessage());
  }

  @Test
  public void testFormattedMessageLogging() throws Exception {
    this.logger.error("%d chickens weigh %.3f kilograms each", 4, 10 / 3.0f);
    final Message errorMessage = (Message) this.input.readObject();
    final String expected = String.format("%d chickens weigh %.3f kilograms each", 4, 10 / 3.0f);
    Assert.assertEquals(expected, errorMessage.getMessage());
  }

  @Test
  public void testStackTraceLogging() throws Exception {
    Exception thrown = null;
    try {
      throw new ArithmeticException("What is three divided by zero?");
    } catch (final ArithmeticException aEx) {
      thrown = aEx;
    }
    this.logger.error(thrown, "error dividing 3 by 0");
    final Message errorMessage = (Message) this.input.readObject();
    Assert.assertTrue(errorMessage.getMessage().contains(thrown.getMessage()));
    Assert.assertTrue(errorMessage.getMessage().split(lineSeparator).length > 1);
  }

  @Test
  public void testFormattedStackTraceLogging() throws Exception {
    Exception thrown = null;
    try {
      throw new ArithmeticException("What is three divided by zero?");
    } catch (final ArithmeticException aEx) {
      thrown = aEx;
    }
    this.logger.error(thrown, "error dividing %d by %d", 3, 0);
    final Message errorMessage = (Message) this.input.readObject();
    Assert.assertTrue(errorMessage.getMessage().contains(thrown.getMessage()));
    Assert.assertTrue(errorMessage.getMessage().split(lineSeparator).length > 1);
  }

  @After
  public void cleanup() throws Exception {
    this.input.close();
    this.output.close();
  }
}
