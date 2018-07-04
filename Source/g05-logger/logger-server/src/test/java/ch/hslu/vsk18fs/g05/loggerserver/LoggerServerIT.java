package ch.hslu.vsk18fs.g05.loggerserver;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;

public class LoggerServerIT {


  @Test(timeout = 1000)
  public void test() throws InterruptedException {
    final int port = this.randomPortNumber();
    final ConcurrentLoggerServer server = new ConcurrentLoggerServer(port);
    final Thread serverThread = new Thread(server);
    serverThread.start();
    while (!server.isBound()) {
      Thread.sleep(100);
    }
    final Message message = new Message("WARN", "Hello, world!");
    Socket socket = null;
    try {
      socket = new Socket("localhost", port);
      final ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
      output.writeObject(message);
      serverThread.interrupt();
    } catch (final Exception ex) {
      Assert.fail(ex.getMessage());
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (final Exception ex) {
          Assert.fail(ex.getMessage());
        }
      }
    }
  }

  private int randomPortNumber() {
    final Random random = new Random(System.currentTimeMillis());
    final int min = 1025;
    final int max = 65535;
    return random.nextInt(max - min + 1) + min;
  }
}
