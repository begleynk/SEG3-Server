package GUI.QuestionnaireBuilder.QuestionTemplates.Range;

import GUI.QuestionnaireBuilder.QuestionTemplates.QuestionTypeController;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.RangeQuestion;
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
public class RangeController extends QuestionTypeController implements Initializable {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private TextField lowerBoundField;
    @FXML private TextField upperBoundField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lowerBoundField.setPromptText("0");
        upperBoundField.setPromptText("10");
        questionTypeIdentifier = "Range Question: ";
    }

    @Override
    public Question getConstructedQuestion(String Id, boolean required) {
        if (isQuestionDefined()) {
            int lowerBound = Integer.parseInt(lowerBoundField.getText());
            int upperBound = Integer.parseInt(upperBoundField.getText());
            return new RangeQuestion(Id, titleField.getText(), descriptionField.getText(), required, lowerBound, upperBound);
        } else {
            return null;
        }
    }

    @Override
    public void clearInputFields() {
        titleField.setText("");
        descriptionField.setText("");
        lowerBoundField.setText("");
        upperBoundField.setText("");
    }

    @Override
    public boolean isQuestionDefined() {
        int lowerBound = Integer.parseInt(lowerBoundField.getText());
        int upperBound = Integer.parseInt(upperBoundField.getText());
        return (titleField.getText().length() > 0 && descriptionField.getText().length() > 0
                && lowerBoundField.getText().length() > 0 && upperBoundField.getText().length() > 0
                && upperBound > lowerBound);
    }

    @Override
    public void populateWithExistingQuestion(Question existingQuestion) {
        RangeQuestion rangeQuestion = (RangeQuestion) existingQuestion;
        titleField.setText(rangeQuestion.getTitle());
        descriptionField.setText(rangeQuestion.getDescription());
        lowerBoundField.setText(rangeQuestion.getLowerBound() + "");
        upperBoundField.setText(rangeQuestion.getUpperBound() + "");
    }
}
