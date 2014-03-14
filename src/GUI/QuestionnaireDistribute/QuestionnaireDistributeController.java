package GUI.QuestionnaireDistribute;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmetersahin on 11/03/2014.
 */
public class QuestionnaireDistributeController implements Initializable {

    @FXML private TextField searchQuestionnaire;
    @FXML private TextField searchPatient;
    @FXML private ChoiceBox customGroups;
    @FXML private Button selectAllPatients;
    @FXML private Button Assign;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
