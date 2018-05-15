package ch.hslu.vsk18fs.g05.loggerclient;

import ch.hslu.vsk.logger.Logger;

public class DemoLoggerClient {

  public static void main(final String[] args) {
    final Logger logger = Logging.getLogger();
    System.out.println(logger.getClass().getName());
    for (int n = 0; n < 100; n++) {
      logger.info("Hello, world! " + (n + 1));
      try {
        Thread.sleep(200);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
