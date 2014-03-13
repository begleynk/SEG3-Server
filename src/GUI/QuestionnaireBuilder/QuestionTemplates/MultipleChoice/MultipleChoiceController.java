package GUI.QuestionnaireBuilder.QuestionTemplates.MultipleChoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan Joya on 09/03/14.
 * SEG3-Server
 */
public class MultipleChoiceController implements Initializable {

    @FXML private TextField questionTitle;
    @FXML private TextField choiceText;
    @FXML private Button clear;
    @FXML private Button save;
    @FXML private Button delete;
    @FXML private Button addChoice;
    @FXML private ListView multipleChoiceListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
