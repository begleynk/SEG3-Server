package GUI.QuestionnaireBuilder.QuestionTemplates.Range;

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
public class RangeController implements Initializable {
    @FXML private TextField questionTitle;
    @FXML private TextField lowerBound;
    @FXML private TextField upperbound;
    @FXML private TextField lowerMessage;
    @FXML private TextField upperMessage;
    @FXML private Button clear;
    @FXML private Button save;
    @FXML private Button delete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
