package ch.hslu.vsk18fs.g05.loggercomponent;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.rmi.ConnectException;
import java.util.List;
import ch.hslu.vsk.logger.LogLevel;
import ch.hslu.vsk.logger.Logger;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk18fs.g05.loggercommon.formatter.MessageFormatter;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

/**
 * Provides a logger component based on the {@link Logger} interface.
 */
public class LoggerComponent implements Logger {

  private final LogLevel actualLevel;

  private final LoggerComponentSetup setup;

  private ObjectOutputStream output;

  private Boolean localLogging = false;

  private LoggerComponent(final LogLevel level, final ObjectOutputStream output,
      final LoggerComponentSetup setup) {
    this.actualLevel = level;
    this.output = output;
    this.setup = setup;
  }

  /**
   * Creates a new {@link LoggerComponent} instance.
   *
   * @param logLevel {@link LogLevel} denoting a filter: only messages <em>as severe as
   *        logLevel</em> will be effectively logged
   * @param output {@link ObjectOutputStream} to send serialized objects to the logger server
   * @param setup {@link LoggerComponentSetup} to deal with connections
   * @return a {@link LoggerComponent} that is connected to a logger server and ready to log
   *         messages
   */
  public static LoggerComponent create(final LogLevel logLevel, final ObjectOutputStream output,
      final LoggerComponentSetup setup) {
    final LoggerComponent logger = new LoggerComponent(logLevel, output, setup);
    return logger;
  }

  @Override
  public void log(final LogLevel level, final String message) {
    if (level.getPriority() < this.actualLevel.getPriority()) {
      return;
    }
    this.send(new Message(level.name(), message));
  }

  @Override
  public void log(final LogLevel level, final String message, final Object... args) {
    if (level.getPriority() < this.actualLevel.getPriority()) {
      return;
    }
    this.send(new Message(level.name(), String.format(message, args)));
  }

  @Override
  public void log(final LogLevel level, final Throwable throwable, final String message) {
    if (level.getPriority() < this.actualLevel.getPriority()) {
      return;
    }
    final String errorMessage = this.toErrorMessage(throwable, message);
    this.send(new Message(level.name(), errorMessage));
  }

  @Override
  public void log(final LogLevel level, final Throwable throwable, final String message,
      final Object... args) {
    if (level.getPriority() < this.actualLevel.getPriority()) {
      return;
    }
    final String errorMessage = this.toErrorMessage(throwable, String.format(message, args));
    this.send(new Message(level.name(), errorMessage));
  }

  private void send(final Message msg) {
    try {
      if (this.localLogging) {
        this.logLocally(msg);
        try {
          this.setup.openSocket();
          System.out.println("re-established connection to server logger");
          this.output = new ObjectOutputStream(this.setup.socket.getOutputStream());
          final List<PersistedString> locallyLogged = this.setup.localLogger.get(Integer.MAX_VALUE);
          System.out.println("logged " + locallyLogged.size() + " messages locally");
          int n = 0;
          for (final PersistedString str : locallyLogged) {
            this.output.writeObject(MessageFormatter.getInstance().parse(str));
            n++;
          }
          System.out.println("re-sent " + n + " locally logged messages to server");
          this.localLogging = false;
        } catch (final ConnectException cEx) {
          System.err.println("re-establishing connection didn't work, continue logging locally");
        }
      } else {
        try {
          this.output.writeObject(msg);
        } catch (final SocketException sEx) {
          System.err.println("connection to logger server was interrupted");
          if (this.setup != null) {
            this.setup.socket.close();
            this.setup.initializeLocalLogger();
            this.localLogging = true;
            this.logLocally(msg);
          }
        }
      }
    } catch (final EOFException eofEx) {
      eofEx.printStackTrace();
    } catch (final IOException ioEx) {
      ioEx.printStackTrace();
    }
  }

  private void logLocally(final LogMessage message) {
    this.setup.localLogger.save(message.getCreationTimestamp(),
        MessageFormatter.getInstance().format(message).getPayload());
  }

  private String toErrorMessage(final Throwable throwable, final String message) {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      final PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8.name());
      throwable.printStackTrace(ps);
      return message + System.getProperty("line.separator")
          + new String(baos.toByteArray(), StandardCharsets.UTF_8);
    } catch (final UnsupportedEncodingException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }
}
