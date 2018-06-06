package ch.hslu.vsk18fs.g05.loggerviewer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import ch.hslu.vsk18fs.g05.loggercommon.rmi.RemotePushHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoggerModel implements RemotePushHandler {

  private final List<LogMessage> list = new ArrayList<>();
  private final ObservableList<LogMessage> observableList =
      FXCollections.synchronizedObservableList(FXCollections.observableList(this.list));

  public void addLogMessage(final LogMessage message) {
    this.observableList.add(message);
  }

  public ObservableList<LogMessage> getObservableList() {
    return this.observableList;
  }

  @Override
  public void push(final LogMessage message) throws RemoteException {
    this.observableList.add(message);
  }
}
