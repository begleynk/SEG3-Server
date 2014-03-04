package GUI.MainScene;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
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

    private final Object[] menuOptions = {"Welcome", "Questionnaires", "Patients",  "Settings"};
    private final String[] viewPaths = {"/GUI/Pane/test.fxml", null, "/GUI/Patient/patientControls.fxml", null};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setView(0);
        this.setupMenu();
    }

    public void setView(int viewIndex) {
        if (viewPaths[viewIndex] != null) {
            stackPane.getChildren().clear();
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource(viewPaths[viewIndex]));
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
        viewChooser.getSelectionModel().select(0);
        viewChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                System.out.println(menuOptions[newNumber.intValue()]);
                setView(newNumber.intValue());
            }
        });
    }
}
