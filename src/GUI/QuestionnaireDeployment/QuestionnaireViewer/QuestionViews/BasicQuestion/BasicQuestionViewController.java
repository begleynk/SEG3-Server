package GUI.QuestionnaireDeployment.QuestionnaireViewer.QuestionViews.BasicQuestion;

import GUI.QuestionnaireDeployment.QuestionnaireViewer.QuestionViews.QuestionTypeViewController;
import ModelObjects.Questions.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 24/03/2014.
 *
 */
public class BasicQuestionViewController extends QuestionTypeViewController implements Initializable {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setQuestion(Question question) {
        if (question != null) {
            titleField.setText(question.getTitle());
            descriptionField.setText(question.getDescription());
        }
    }
}
