package ch.hslu.vsk18fs.g05.loggercommon.formatter;

public class MessageFormatter {

  private static final Class<? extends LogMessageFormatter> FORMATTER = CurlyFormatter.class;

  private static LogMessageFormatter instance = null;

  public static synchronized LogMessageFormatter getInstance() {
    if (instance == null) {
      try {
        final Class<?> clazz = Class.forName(FORMATTER.getName());
        instance = (LogMessageFormatter) clazz.newInstance();
      } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
        instance = new SimpleFormatter();
      }
    }
    return instance;
  }
}
