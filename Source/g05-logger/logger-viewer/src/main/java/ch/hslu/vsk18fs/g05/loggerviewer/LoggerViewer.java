package ch.hslu.vsk18fs.g05.loggerviewer;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import ch.hslu.vsk18fs.g05.loggercommon.rmi.RemotePushHandler;
import ch.hslu.vsk18fs.g05.loggercommon.rmi.RemoteRegistration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoggerViewer extends Application {

  private Remote handler;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    final BorderPane root = new BorderPane();

    // Load resources
    final FXMLLoader tableLoader = new FXMLLoader(this.getClass().getResource("/LogTable.fxml"));
    root.setCenter((Node) tableLoader.load());

    final LoggerController controller = tableLoader.getController();
    final LoggerModel model = new LoggerModel();
    controller.initializeModel(model);

    controller.updateLogTable();

    // TODO: re-activate for test
    // model.addLogMessage(new Message("TRACE", Instant.now(), Instant.now(), "message", "sauce"));

    // Add a Scene to the Stage
    primaryStage.setTitle("I.BA_VSK.F1801 - Gruppe 05");
    primaryStage.setScene(new Scene(root, 1024, 512));
    primaryStage.show();

    this.registerViewer(model, this.getParameters().getRaw());
  }

  public static void main(final String[] args) {
    launch(args);
  }

  private void registerViewer(final LoggerModel model, final List<String> args) throws Exception {
    String host = "localhost";
    if (args.size() > 0) {
      host = args.get(0);
    }
    System.setProperty("java.rmi.server.codebase", "http://localhost:8080/");
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
    final Registry reg = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
    final RemoteRegistration registration = (RemoteRegistration) reg.lookup("loggerServer");
    this.handler = UnicastRemoteObject.exportObject(model, 0);
    registration.register((RemotePushHandler) this.handler);
  }
}
