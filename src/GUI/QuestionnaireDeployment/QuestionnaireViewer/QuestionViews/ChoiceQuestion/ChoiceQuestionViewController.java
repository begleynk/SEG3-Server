package GUI.QuestionnaireDeployment.QuestionnaireViewer.QuestionViews.ChoiceQuestion;

import GUI.QuestionnaireDeployment.QuestionnaireViewer.QuestionViews.QuestionTypeViewController;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.SelectManyQuestion;
import ModelObjects.Questions.Types.SelectOneQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 24/03/2014.
 *
 */
public class ChoiceQuestionViewController extends QuestionTypeViewController implements Initializable {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ListView<String> choicesListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setQuestion(Question question) {
        if (question != null) {
            titleField.setText(question.getTitle());
            descriptionField.setText(question.getDescription());
            if (question.getClass() == SelectOneQuestion.class) {
                SelectOneQuestion selectOneQuestion = (SelectOneQuestion) question;
                choicesListView.getItems().addAll(selectOneQuestion.getAnswerOptions());
            }
            if (question.getClass() == SelectManyQuestion.class) {
                SelectManyQuestion selectManyQuestion = (SelectManyQuestion) question;
                choicesListView.getItems().addAll(selectManyQuestion.getAnswerOptions());
            }
        }
    }
}
