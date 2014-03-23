package GUI.ViewAnswers;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.Patient;
import ModelObjects.Questionnaire;
import ModelObjects.QuestionnairePointer;
import ModelObjects.Questions.Question;
import Sockets.SocketProcess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Niklas Begley on 23/03/2014.
 *
 */
public class ViewAnswersController implements Initializable
{
    @FXML private Parent root;

    @FXML private ListView questionnaireList;
    private final ObservableList<QuestionnairePointer> questionnaires = FXCollections.observableArrayList();

    @FXML private TextField questionnaireSearchField;

    @FXML private Pane noQuestionnaireSelectedPane;
    @FXML private Pane questionnaireSelectedPane;
    @FXML private Pane answersPane;

    private ArrayList<Pane> leftPanes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setupQuestionnaireList();
        setupSearchField();

        leftPanes.add(noQuestionnaireSelectedPane);
        leftPanes.add(questionnaireSelectedPane);
        leftPanes.add(answersPane);

        switchToPane(noQuestionnaireSelectedPane);
    }

    public void showQuestionnaireDetails(Questionnaire questionnaire)
    {
        setupQuestionnaireView(questionnaire);
        switchToPane(questionnaireSelectedPane);
    }

    public void setupQuestionnaireView(Questionnaire questionnaire)
    {
        System.out.println(questionnaire.getTitle());
    }

    public void switchToPane(Pane pane)
    {
        for(Pane p : leftPanes)
        {
            if(p == pane)
            {
                p.setVisible(true);
            }
            else
            {
                p.setVisible(false);
            }
        }
    }

    public void searchQuestionnaires()
    {
        String query = questionnaireSearchField.getText();
        ArrayList<QuestionnairePointer> questionnairesToShow = new ArrayList<>();

        if(!query.equals("") || query == null)
        {
            for(QuestionnairePointer q : questionnaires)
            {
                if(q.getTitle().contains(query))
                {
                    questionnairesToShow.add(q);
                }
            }
            questionnaires.setAll(questionnairesToShow);
        }
        else
        {
            updateQuestionnaires();
        }
    }


    public void updateQuestionnaires()
    {
        try
        {
            ArrayList<QuestionnairePointer> tempQuestionnaires = DataLayer.getQuestionnairePointersForState(1);
            tempQuestionnaires.addAll(DataLayer.getQuestionnairePointersForState(2));
            this.questionnaires.setAll(tempQuestionnaires);
        }
        catch(SQLException | NoQuestionnaireException e)
        {
            Dialogs.showErrorDialog((Stage)this.root.getScene().getWindow(), "There was an error getting the questionnaires. Please contact support.");
        }
    }

    public void setupSearchField()
    {
        questionnaireSearchField.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent keyEvent) {
                searchQuestionnaires();
            }
        });
    }

    public void setupQuestionnaireList()
    {
        this.questionnaireList.setCellFactory(new Callback<ListView<QuestionnairePointer>, ListCell<QuestionnairePointer>>()
        {
            @Override
            public ListCell<QuestionnairePointer> call(ListView<QuestionnairePointer> p)
            {
                return new ListCell<QuestionnairePointer>()
                {
                    @Override
                    protected void updateItem(QuestionnairePointer q, boolean bool)
                    {
                        super.updateItem(q, bool);
                        if (q != null)
                        {
                            setText(q.getTitle());
                        }
                    }
                };
            }
        });
        this.questionnaireList.setItems(questionnaires);

        this.questionnaireList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuestionnairePointer>() {
            @Override
            public void changed(ObservableValue<? extends QuestionnairePointer> questionnaire, QuestionnairePointer old_one, QuestionnairePointer selection) {
                try
                {
                    Questionnaire q = DataLayer.getQuestionnaireWithPointer(selection);
                    showQuestionnaireDetails(q);
                }
                catch(NoQuestionnaireException e)
                {
                    System.err.println("Tried to get a questionnaire that does not exist.");
                }
            }
        });
        updateQuestionnaires();
    }
}
