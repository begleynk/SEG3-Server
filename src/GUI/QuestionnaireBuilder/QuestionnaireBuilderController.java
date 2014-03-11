package GUI.QuestionnaireBuilder;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 04/03/2014.
 *
 */
public class QuestionnaireBuilderController implements Initializable {

    @FXML private Parent root;

    @FXML private TextField questionnaireSearchField;
    @FXML private ListView questionnaireListView;

    @FXML private TextField questionnaireTitleField;
    @FXML private Button deployButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setupViewForBuildingNewQuestionnaireAction(Event event) {

    }

}
