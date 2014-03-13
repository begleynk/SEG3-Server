package GUI.QuestionnaireBuilder.QuestionTemplates.SingleChoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan Joya on 09/03/14.
 * SEG3-Server
 */
public class SingleChoiceController implements Initializable {

    @FXML private TextField questionTitle;
    @FXML private TextField choiceText;
    @FXML private Button clear;
    @FXML private Button save;
    @FXML private Button delete;
    @FXML private Button addChoice;
    @FXML private ListView singleChoiceListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
