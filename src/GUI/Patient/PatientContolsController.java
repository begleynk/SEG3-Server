package GUI.Patient;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Me on 04/03/2014.
 */
public class PatientContolsController implements Initializable {

    @FXML private TextField patientSearchField;
    @FXML private ListView patientListView;

    @FXML private ToolBar rightPaneToolBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 4; i++) {
            rightPaneToolBar.getItems().add(new Button("ToolBarButton " + i));
        }
        Region spring = new Region();
        HBox.setHgrow(spring, Priority.ALWAYS);
        rightPaneToolBar.getItems().add(spring);
        rightPaneToolBar.getItems().add(new Button("Right ToolBarButton"));
    }

    public void addNewPatientAction(Event event) {
        System.out.println("Add New");
    }
}
