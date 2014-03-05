package GUI.Questionnaire.Draft;

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
 * Created by Faizan on 05/03/14.
 */
public class QuestionnaireTabDraftController implements Initializable {

    // textfield and listview from GUI
    @FXML private TextField questionSearchField;
    @FXML private ListView questionListView;


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

    public void addNewQuestionnaireAction(Event event) {
        System.out.println("Add New Questionnaire");
    }
    public void searchInputChangedAction(Event event) {

    }
}
