package ch.hslu.vsk18fs.g05.loggercommon.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;

public interface RemotePushHandler extends Remote {

  public void push(LogMessage message) throws RemoteException;

}
