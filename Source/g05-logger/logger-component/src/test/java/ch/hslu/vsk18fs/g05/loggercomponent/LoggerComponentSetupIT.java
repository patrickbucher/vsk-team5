package ch.hslu.vsk18fs.g05.loggercomponent;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ch.hslu.vsk.logger.LogLevel;
import ch.hslu.vsk.logger.Logger;
import ch.hslu.vsk.logger.LoggerSetupConfiguration;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

public class LoggerComponentSetupIT {

  private static final String HOST = "localhost";
  private static final LogLevel LEVEL = LogLevel.DEBUG;
  private LoggerSetupConfiguration config;
  private final Object monitor = new Object();
  private Thread serverThread;

  @Before
  public void setupServer() throws Exception {
    this.config = new LoggerSetupConfiguration();
    this.config.setHost(HOST);
    this.config.setLevel(LEVEL);
    this.serverThread = this.createServerThread(this.config);
    this.serverThread.start();
    synchronized (this.monitor) {
      while (this.config.getPort() == 0) {
        this.monitor.wait();
      }
    }
  }

  @Test
  public void testCreateLogger() throws Exception {
    final LoggerComponentSetup setup = new LoggerComponentSetup();
    setup.configure(this.config);
    final Logger logger = setup.getLogger();
    logger.log(LogLevel.DEBUG, "Hello, world!");
    this.serverThread.join();
  }

  @Test
  public void testServerSwitch() throws Exception {
    final LoggerComponentSetup setup = new LoggerComponentSetup();
    setup.configure(this.config);
    Logger logger = setup.getLogger();
    logger.log(LogLevel.DEBUG, "Hello, world!");
    setup.configure(null);

    final LoggerSetupConfiguration alternativeConfig = new LoggerSetupConfiguration();
    final Thread otherServer = this.createServerThread(alternativeConfig);
    otherServer.start();
    synchronized (this.monitor) {
      while (alternativeConfig.getPort() == 0) {
        this.monitor.wait();
      }
    }
    setup.setHostAndPort("localhost", alternativeConfig.getPort());
    logger = setup.getLogger();
    logger.log(LogLevel.DEBUG, "Hello, again!");

    this.serverThread.join();
    otherServer.join();
  }

  private Thread createServerThread(final LoggerSetupConfiguration config) {
    return new Thread(() -> {
      ServerSocket server = null;
      Socket client;
      try {
        server = new ServerSocket(0);
        synchronized (this.monitor) {
          config.setPort(server.getLocalPort());
          this.monitor.notifyAll();
        }
        client = server.accept();
        final ObjectInputStream input = new ObjectInputStream(client.getInputStream());
        Object received = null;
        try {
          received = input.readObject();
        } catch (final EOFException ex) {
          Assert.fail("EOF reached before reading a message: " + ex.getMessage());
        }
        Assert.assertTrue(received != null && received instanceof Message);
      } catch (final Exception ex) {
        Assert.fail(ex.getMessage());
        ex.printStackTrace();
      } finally {
        try {
          if (server != null) {
            server.close();
          }
        } catch (final IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
}

