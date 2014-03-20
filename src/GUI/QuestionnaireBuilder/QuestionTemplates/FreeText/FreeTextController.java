package GUI.QuestionnaireBuilder.QuestionTemplates.FreeText;

import GUI.QuestionnaireBuilder.QuestionTemplates.QuestionTypeController;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.TextQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan Joya on 09/03/14.
 *
 */
public class FreeTextController extends QuestionTypeController implements Initializable {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionTypeIdentifier = "Free Text Question: ";
    }

    @Override
    public Question getConstructedQuestion(String Id, boolean required) {
        if (isQuestionDefined()) {
            return new TextQuestion(Id, titleField.getText(), descriptionField.getText(), required);
        } else {
            return null;
        }
    }

    @Override
    public void clearInputFields() {
        titleField.setText("");
        descriptionField.setText("");
    }

    @Override
    public boolean isQuestionDefined() {
        return (titleField.getText().length() > 0 && descriptionField.getText().length() > 0);
    }

    @Override
    public void populateWithExistingQuestion(Question existingQuestion) {
        TextQuestion textQuestion = (TextQuestion) existingQuestion;
        titleField.setText(textQuestion.getTitle());
        descriptionField.setText(textQuestion.getDescription());
    }

}
