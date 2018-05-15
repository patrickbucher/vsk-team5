package ch.hslu.vsk18fs.g05.loggerserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistrySetup implements Runnable {

  @Override
  public void run() {
    System.setProperty("java.rmi.server.codebase", "http://localhost:8080/");
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
    try {
      final Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
      synchronized (registry) {
        registry.wait();
      }
    } catch (final RemoteException | InterruptedException e) {
      e.printStackTrace();
    }
  }

}
