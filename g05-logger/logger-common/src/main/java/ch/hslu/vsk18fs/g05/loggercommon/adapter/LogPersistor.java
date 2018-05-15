package ch.hslu.vsk18fs.g05.loggercommon.adapter;

import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;

/**
 * Provides an interface for the logger component to persist {@link LogMessage} instances.
 */
public interface LogPersistor {

  /**
   * Saves the given {@link LogMessage} instance.
   *
   * @param log {@link LogMessage} to be persisted
   */
  public void save(LogMessage log);
}
