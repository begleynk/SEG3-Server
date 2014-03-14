package GUI.MainScene;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 27/02/2014.
 *
 */
public class MainSceneController implements Initializable {

    @FXML private StackPane stackPane;
    @FXML private ChoiceBox<Object> viewChooser;

    private final Object[] menuOptions = {
            "Welcome",
            new Separator(),
            "Patients",
            "Create Groups",
            new Separator(),
            "Questionnaire Builder",
            "Questionnaire Archive",
            new Separator(),
            "Distribute Questionnaires",
            new Separator(),
            "Connect Tablets",
            new Separator(),
            "Change Logs"
    };
    private final String[] viewPaths = {
            "/GUI/Welcome/welcome.fxml",
            null, // Separator
            "/GUI/ManagePatients/patientControls.fxml",
            // "/GUI/CreateCustomGroups/customGroups.fxml", // create groups GUI
            null,
            null, // Separator
            "/GUI/QuestionnaireBuilder/questionnaireBuilder.fxml",
            "/GUI/QuestionnaireArchive/questionnaireArchive.fxml",
            null, // Separator
            "/GUI/QuestionnaireDistribute/questionnaireDistribute.fxml",
            null, // Separator
            "/GUI/ConnectTablets/settingControls.fxml",
            null, // Separator
            null, // Creating Change Logs
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setView(0);
        this.setupMenu();
        this.viewChooser.getSelectionModel().select(0);
    }

    public void setView(int viewIndex) {
        String viewPath = viewPaths[viewIndex];
        if (viewPath != null && viewPath.length() > 0) {
            stackPane.getChildren().clear();
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource(viewPath));
                AnchorPane.setTopAnchor(pane, 0.0);
                AnchorPane.setBottomAnchor(pane, 0.0);
                AnchorPane.setRightAnchor(pane, 0.0);
                AnchorPane.setLeftAnchor(pane, 0.0);
                stackPane.getChildren().add(0, pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupMenu() {
        viewChooser.setItems(FXCollections.observableArrayList(menuOptions));
        viewChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                setView(newNumber.intValue());
            }
        });
    }
}
