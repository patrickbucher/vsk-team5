package ch.hslu.vsk18fs.g05.loggerclient;

import ch.hslu.vsk.logger.Logger;
import ch.hslu.vsk.logger.LoggerManager;

public class Logging {

  private static LoggerManager loggerManager = null;
  private static Logger logger = null;

  public static synchronized Logger getLogger() {
    if (loggerManager == null) {
      Logging.loggerManager = new LoggerManager();
    }
    if (logger == null) {
      logger = loggerManager.getLogger();
    }
    return logger;
  }

}
