package GUI.QuestionnaireBuilder.QuestionTemplates.SingleChoice;

import GUI.QuestionnaireBuilder.QuestionTemplates.QuestionTypeController;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.SelectManyQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Faizan Joya on 09/03/14.
 *
 */
public class SingleChoiceController extends QuestionTypeController implements Initializable {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ListView<String> choicesListView;
    @FXML private TextField newChoiceField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionTypeIdentifier = "Single Choice Question: ";
        this.choicesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void addNewChoice() {
        if (newChoiceField.getText().length() > 0) {
            choicesListView.getItems().add(newChoiceField.getText());
        }
    }

    public void removeSelectedChoices() {
        ArrayList<Integer> selected = new ArrayList<>(choicesListView.getSelectionModel().getSelectedIndices());
        Collections.reverse(selected);
        System.out.println(selected);
        for (Integer integer : selected) {
            choicesListView.getItems().remove(integer.intValue());
        }
    }

    public void removeAllChoices() {
        choicesListView.getItems().clear();
    }

    @Override
    public Question getConstructedQuestion(String Id, boolean required) {
        if (isQuestionDefined()) {
            List<String> choices = new ArrayList<>(choicesListView.getItems());
            return new SelectManyQuestion(Id, titleField.getText(),descriptionField.getText(), required, choices);
        } else {
            return null;
        }
    }

    @Override
    public void clearInputFields() {
        titleField.setText("");
        descriptionField.setText("");
        choicesListView.getItems().clear();
    }

    @Override
    public boolean isQuestionDefined() {
        return (titleField.getText().length() > 0 && descriptionField.getText().length() > 0
                && choicesListView.getItems().size() > 0);
    }
}
