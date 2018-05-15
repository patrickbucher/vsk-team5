package ch.hslu.vsk18fs.g05.loggerserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import ch.hslu.vsk18fs.g05.loggercommon.adapter.StringPersistorAdapter;
import ch.hslu.vsk18fs.g05.loggercommon.formatter.MessageFormatter;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;
import ch.hslu.vsk18fs.g05.loggercommon.rmi.RemotePushHandler;
import ch.hslu.vsk18fs.g05.loggercommon.rmi.RemoteRegistration;

/**
 * Implements a concurrent logger TCP server that accepts {@link Message} instances, stores the
 * messages <em>in a file in the user's temporary folder</em>. The server runs on port 1234 on
 * default (when executing it from the {@link #main(String[])} method, that is). The port number can
 * also be specified using the constructor {@link #ConcurrentLoggerServer(int)} <strong>The server
 * is not production ready, because files stored in a temporary folder don't persist a restart of
 * the system.</strong>
 *
 * The server implements the {@link Runnable} interface and hence should be run using a
 * {@link Thread} (see {@link #main(String[])} as an example). Running the server by directly
 * calling {@link #run()} has not been tested.
 */
public class ConcurrentLoggerServer implements Runnable, RemoteRegistration {

  public final static int LOGGING_PORT = 1234;

  private final int port;

  private boolean bound = false;

  private final Collection<RemotePushHandler> viewers;

  private Remote registrationServer;

  /**
   * Constructor for {@link ConcurrentLoggerServer} that allows the client to specify the port
   * number. {@link #LOGGING_PORT} can be used for the lack of a better idea.
   *
   * @param port the port number to run the server socket on
   */
  public ConcurrentLoggerServer(final int port) {
    this.port = port;
    this.viewers = new ArrayList<>();
  }

  /**
   * Runs a stand-alone {@link ConcurrentLoggerServer} on {@link #LOGGING_PORT}.
   *
   * @param args command line arguments (ignored)
   */
  public static void main(final String args[]) {
    new Thread(new RegistrySetup()).start();
    final ConcurrentLoggerServer server = new ConcurrentLoggerServer(LOGGING_PORT);
    try {
      server.registerPusher();
      new Thread(server).start();
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void run() {
    ServerSocket server = null;
    try {
      server = new ServerSocket(this.port);
      this.bound = true;
      final StringPersistorAdapter adapter =
          StringPersistorAdapter.create(MessageFormatter.getInstance(),
              this.createLoggerFile(System.getProperty("user.home")).getAbsolutePath());
      while (!Thread.currentThread().isInterrupted()) {
        final Socket socket = server.accept();
        final ConcurrentClientHandler handler =
            ConcurrentClientHandler.listen(socket, adapter, this);
        final Thread thread = new Thread(handler);
        thread.start();
      }
    } catch (final IOException ex) {
      ex.printStackTrace();
    } finally {
      if (server != null && !server.isClosed()) {
        try {
          server.close();
        } catch (final IOException ioEx) {
          ioEx.printStackTrace();
        }
      }
    }
  }

  @Override
  public void register(final RemotePushHandler viewer) throws RemoteException {
    this.viewers.add(viewer);
  }

  public synchronized void notifyViewers(final LogMessage message) {
    this.viewers.stream().forEach(v -> {
      try {
        v.push(message);
      } catch (final RemoteException e) {
        e.printStackTrace();
      }
    });
  }

  public boolean isBound() {
    return this.bound;
  }

  private void registerPusher() throws RemoteException, AlreadyBoundException {
    final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
    this.registrationServer = UnicastRemoteObject.exportObject(this, 0);
    reg.bind("loggerServer", this.registrationServer);
  }

  private File createLoggerFile(final String directory) throws IOException {
    final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss.SSS");
    final String timestamp = fmt.format(Instant.now().atZone(ZoneId.systemDefault()));
    final String path = directory + File.separator + "vsk.g05." + timestamp + ".log";
    final File logFile = new File(path);
    logFile.createNewFile();
    return logFile;
  }
}
