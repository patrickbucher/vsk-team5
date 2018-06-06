package ch.hslu.vsk18fs.g05.loggercommon.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRegistration extends Remote {

  public void register(RemotePushHandler viewer) throws RemoteException;

}
