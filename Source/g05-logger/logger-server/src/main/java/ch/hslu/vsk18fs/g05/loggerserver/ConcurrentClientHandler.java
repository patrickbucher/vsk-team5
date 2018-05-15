package ch.hslu.vsk18fs.g05.loggerserver;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Instant;
import ch.hslu.vsk18fs.g05.loggercommon.adapter.StringPersistorAdapter;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

/**
 * Implements a concurrent client handler to be used by {@link ConcurrentLoggerServer}. Every client
 * is handled by one instance of {@link ConcurrentClientHandler}, which implements the
 * {@link Runnable} interface and is supposed to be run in its own {@link Thread}.
 */
public class ConcurrentClientHandler implements Runnable {

  private final Socket socket;
  private final ObjectInputStream input;
  private final StringPersistorAdapter persistorAdapter;
  private final String messageSource;
  private final ConcurrentLoggerServer server;

  /**
   * Creates a new {@link ConcurrentClientHandler} based on the clients {@link Socket}. This is the
   * only way to create a {@link ConcurrentClientHandler}, because its sole constructor
   * {@link #ConcurrentClientHandler(Socket, ObjectInputStream, StringPersistorAdapter, String, ConcurrentLoggerServer)}
   * is private. Incoming {@link Message} instances are persisted using a
   * {@link StringPersistorAdapter}.
   *
   * @param socket a open {@link Socket} to the client
   * @param persistorAdapter a {@link StringPersistorAdapter} to handle incoming messages
   * @param server a {@link ConcurrentLoggerServer} who knows all the viewers to be notified in case
   *        of a message entry
   * @return a instance of {@link ConcurrentClientHandler} to be run in its own {@link Thread}
   * @throws IOException if the socket cannot be decorated by an {@link ObjectOutputStream} and/or
   *         an {@link ObjectInputStream}
   */
  public static ConcurrentClientHandler listen(final Socket socket,
      final StringPersistorAdapter persistorAdapter, final ConcurrentLoggerServer server)
      throws IOException {
    final ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
    final String identifier = socket.getInetAddress().getHostName() + ":" + socket.getPort();
    final ConcurrentClientHandler handler =
        new ConcurrentClientHandler(socket, input, persistorAdapter, identifier, server);
    return handler;
  }

  private ConcurrentClientHandler(final Socket socket, final ObjectInputStream input,
      final StringPersistorAdapter persistorAdapter, final String messageSource,
      final ConcurrentLoggerServer server) {
    this.socket = socket;
    this.input = input;
    this.persistorAdapter = persistorAdapter;
    this.messageSource = messageSource;
    this.server = server;
  }

  @Override
  public void run() {
    if (!this.socket.isBound()) {
      System.err.println("socket was not bound, aborting");
      return;
    }

    Object object = null;
    Message received = null;

    while (true) {
      try {
        object = this.input.readObject();
      } catch (final EOFException eofEx) {
        System.out.println("socket on port " + this.socket.getPort() + " was closed, finished");
        return; // thread is done
      } catch (ClassNotFoundException | IOException e) {
        e.printStackTrace();
      }
      if (object != null || object instanceof Message) {
        received = (Message) object;
        final Message message = new Message(received.getLevel(), received.getCreationTimestamp(),
            Instant.now(), received.getMessage(), this.messageSource);
        this.persistorAdapter.save(message);
        this.server.notifyViewers(message);
      }
    }
  }

}
