package GUI.Questionnaire.Archive;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan on 04/03/14.
 *
 * work in progress
 */
public class QuestionnaireTabArchiveController implements Initializable{
    // File Paths to tab content views
    @FXML private AnchorPane anchorPaneArchived;

    private String[] views = {"/GUI/QuestionnaireTabArchiveController"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTabViews();
    }

}
