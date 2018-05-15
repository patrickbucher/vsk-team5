package ch.hslu.vsk18fs.g05.loggercomponent;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.ConnectException;
import ch.hslu.vsk.logger.LogLevel;
import ch.hslu.vsk.logger.Logger;
import ch.hslu.vsk.logger.LoggerSetup;
import ch.hslu.vsk.logger.LoggerSetupConfiguration;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import ch.hslu.vsk18fs.g05.stringpersistor.StringPersistorFile;

/**
 * Provides a logger component setup based on the {@link LoggerSetup} interface.
 */
public class LoggerComponentSetup implements LoggerSetup {

  Socket socket;
  StringPersistor localLogger;

  private File localLogFile;
  private LoggerSetupConfiguration config;


  @Override
  public synchronized Logger getLogger() {
    LoggerComponent logger = null;
    try {
      logger = LoggerComponent.create(this.config.getLevel(),
          new ObjectOutputStream(this.socket.getOutputStream()), this);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    return logger;
  }

  @Override
  public synchronized void configure(final LoggerSetupConfiguration config) {
    if (config == null) {
      this.closeSocket();
      return;
    }
    this.config = config;
    try {
      this.openSocket();
    } catch (final ConnectException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public synchronized void setLogLevel(final LogLevel logLevel) {
    this.config.setLevel(logLevel);
  }

  @Override
  public synchronized LogLevel getLogLevel() {
    return this.config.getLevel();
  }

  @Override
  public synchronized void setHostAndPort(final String host, final int port) {
    this.config.setHost(host);
    this.config.setPort(port);
    this.closeSocket();
    try {
      this.openSocket();
    } catch (final ConnectException e) {
      e.printStackTrace();
    }
  }

  synchronized void openSocket() throws ConnectException {
    try {
      this.socket = new Socket(this.config.getHost(), this.config.getPort());
      System.out.println("established connection to logger server " + this.config.getHost() + ":"
          + this.config.getPort());
    } catch (final IOException e) {
      throw new ConnectException("Cannot connect to logger server on " + this.config.getHost() + ":"
          + this.config.getPort() + ".");
    }
  }

  synchronized void closeSocket() {
    if (this.socket != null && !this.socket.isClosed()) {
      try {
        this.socket.close();
      } catch (final IOException e) {
        throw new RuntimeException(e);
      } finally {
        this.socket = null;
      }
    }
  }

  void initializeLocalLogger() {
    try {
      this.localLogFile = File.createTempFile("vsk.g05.", ".log");
      this.localLogger = new StringPersistorFile();
      this.localLogger.setFile(this.localLogFile);
    } catch (final IOException e) {
      System.err.println("Error creating local log file '" + this.localLogFile.getAbsolutePath()
          + "', cause: " + e.getMessage());
    }
  }

}
