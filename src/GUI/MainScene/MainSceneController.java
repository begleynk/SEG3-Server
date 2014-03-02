package GUI.MainScene;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
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
 * Created by Me on 27/02/2014.
 */
public class MainSceneController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private ChoiceBox viewChooser;

    private final Object[] menuOptions = {"Welcome", new Separator(), "Questionnaires", "Patients", new Separator(), "Settings"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setView();
        this.setupMenu();
    }

    public void setView() {
        stackPane.getChildren().clear();
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/Pane/test.fxml"));
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            stackPane.getChildren().add(0, pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupMenu() {
        viewChooser.setItems(FXCollections.observableArrayList(menuOptions));
        viewChooser.getSelectionModel().select(0);
        viewChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                System.out.println(menuOptions[newNumber.intValue()]);
            }
        });
    }

    public void handleAction(Event event) {
        System.out.println("Change Views");
    }
}
