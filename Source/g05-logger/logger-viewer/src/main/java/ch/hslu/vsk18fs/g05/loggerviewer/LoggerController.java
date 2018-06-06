package ch.hslu.vsk18fs.g05.loggerviewer;

import java.time.Instant;
import ch.hslu.vsk18fs.g05.loggercommon.message.LogMessage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LoggerController {

  private LoggerModel model;

  @FXML
  private TableView<LogMessage> logTable;

  @FXML
  private TableColumn<LogMessage, String> level;
  @FXML
  private TableColumn<LogMessage, String> message;
  @FXML
  private TableColumn<LogMessage, String> source;
  @FXML
  private TableColumn<LogMessage, Instant> serverEntryTimestamp;
  @FXML
  private TableColumn<LogMessage, Instant> creationTimestamp;

  public void initializeModel(final LoggerModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model was already initialized.");
    }
    this.model = model;

    this.level.setCellValueFactory(new PropertyValueFactory<>("level"));
    this.message.setCellValueFactory(new PropertyValueFactory<>("message"));
    this.creationTimestamp.setCellValueFactory(new PropertyValueFactory<>("creationTimestamp"));
    this.serverEntryTimestamp
        .setCellValueFactory(new PropertyValueFactory<>("serverEntryTimestamp"));
    this.source.setCellValueFactory(new PropertyValueFactory<>("source"));
  }

  public void updateLogTable() {
    ObservableList<LogMessage> observableList;
    observableList = this.model.getObservableList();

    this.logTable.setItems(observableList);
  }
}
