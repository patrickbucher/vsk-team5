package ch.hslu.vsk18fs.g05.loggercommon.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.junit.Assert;
import org.junit.Test;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk18fs.g05.loggercommon.formatter.MessageFormatter;
import ch.hslu.vsk18fs.g05.loggercommon.message.Message;
import ch.hslu.vsk18fs.g05.stringpersistor.PersistedStringParser;

public class StringPersistorAdapterIT {

  @Test
  public void testAdapter() throws Exception {
    final File logFile = File.createTempFile("StringPersistorAdapterTest", ".txt");
    final LogPersistor persistor =
        StringPersistorAdapter.create(MessageFormatter.getInstance(), logFile.getAbsolutePath());
    final Message logMessage = new Message("DEBUG", "Hello, world!");
    persistor.save(logMessage);

    final BufferedReader reader = new BufferedReader(new FileReader(logFile));
    final String logFileContent = reader.readLine();
    final PersistedString actual = PersistedStringParser.parseItem(logFileContent);
    final PersistedString expected = MessageFormatter.getInstance().format(logMessage);
    Assert.assertEquals(expected, actual);
    reader.close();
  }

}
