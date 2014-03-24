package GUI.ViewAnswers;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.AnswerSet;
import ModelObjects.Patient;
import ModelObjects.Questionnaire;
import ModelObjects.QuestionnairePointer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

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

    @FXML private ListView<QuestionnairePointer> questionnairePointerListView;
    @FXML private TextField questionnaireSearchField;

    @FXML private AnchorPane noQuestionnaireSelectedPane;
    @FXML private AnchorPane questionnaireSelectedPane;
    @FXML private AnchorPane answersPane;
    private ArrayList<Pane> rightPanes = new ArrayList<>();

    private ObservableList<QuestionnairePointer> visibleQuestionnairePointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> offScreenQuestionnairePointers
            = FXCollections.observableArrayList();

    /*************************
     * Questionnaire View
     *************************/

    @FXML private Label questionnaireTitleLabel;
    @FXML private Label numberOfSubmissions;
    @FXML private Label numberOfQuestions;

    @FXML private TableView<Patient> answerTable;
    @FXML private TableColumn<Patient, String> tableNSHcolumn;
    @FXML private TableColumn<Patient, String> tableFirstNameColumn;
    @FXML private TableColumn<Patient, String> tableLastNameColumn;

    private final ObservableList<Patient> patientsThatHaveAnswered = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Keep track of all panels for later...
        rightPanes.add(noQuestionnaireSelectedPane);
        rightPanes.add(questionnaireSelectedPane);
        rightPanes.add(answersPane);

        // Show Default Pane
        switchToPane(noQuestionnaireSelectedPane);

        // Setup the Patient TableView
        setupAnswerTable();

        // Setup the ListView behaviours
        setupQuestionnairePointerListView();

        // Fetch the data for the left menu
        fetchQuestionnairePointers();
    }

    public void setupQuestionnairePointerListView()
    {
        this.questionnairePointerListView.setCellFactory(new Callback<ListView<QuestionnairePointer>, ListCell<QuestionnairePointer>>() {
            @Override
            public ListCell<QuestionnairePointer> call(ListView<QuestionnairePointer> p) {
                return new ListCell<QuestionnairePointer>() {
                    @Override
                    protected void updateItem(QuestionnairePointer pointer, boolean aBool) {
                        super.updateItem(pointer, aBool);
                        if (pointer != null) {
                            setText(pointer.getTitle());
                        }
                    }
                };
            }
        });
        this.questionnairePointerListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<QuestionnairePointer>() {
                    @Override
                    public void changed(ObservableValue<? extends QuestionnairePointer> observableValue, QuestionnairePointer old_pointer, QuestionnairePointer new_pointer) {
                        if (new_pointer != null) {
                            try
                            {
                                showQuestionnaireDetails(DataLayer.getQuestionnaireWithPointer(new_pointer));
                            }
                            catch(NoQuestionnaireException e)
                            {
                                System.err.println("Tried to get a questionnaire that does not exist.");
                            }
                        } else {
                            switchToPane(noQuestionnaireSelectedPane);
                        }
                    }
                }
        );
        this.questionnairePointerListView.setItems(visibleQuestionnairePointers);
    }

    public void setupAnswerTable()
    {
        tableNSHcolumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("nhsNumber"));
        tableFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("first_name"));
        tableLastNameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));

        answerTable.setItems(patientsThatHaveAnswered);
    }

    // Left Menu Methods

    public void fetchQuestionnairePointers()
    {
        try {
            this.offScreenQuestionnairePointers.clear();
            this.visibleQuestionnairePointers.clear();
            this.visibleQuestionnairePointers.addAll(DataLayer.getQuestionnairePointersForState(1));
            this.visibleQuestionnairePointers.addAll(DataLayer.getQuestionnairePointersForState(2));
            searchInputChangedAction();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void searchInputChangedAction()
    {
        String searchTerm = questionnaireSearchField.getText();
        if (searchTerm == null || searchTerm.equals("") ) {
            visibleQuestionnairePointers.addAll(offScreenQuestionnairePointers);
            offScreenQuestionnairePointers.clear();
        } else {
            fuzzySearchQuestionnairePointerUsingSearchTerm(searchTerm);
        }
    }

    public void fuzzySearchQuestionnairePointerUsingSearchTerm(String searchTerm)
    {
        searchTerm = searchTerm.trim().toLowerCase();
        offScreenQuestionnairePointers.addAll(visibleQuestionnairePointers);
        visibleQuestionnairePointers.clear();
        ArrayList<QuestionnairePointer> pointers = new ArrayList<>();
        for (QuestionnairePointer pointer : offScreenQuestionnairePointers) {
            if (pointer.getTitle().toLowerCase().startsWith(searchTerm) ||
                    pointer.getTitle().toLowerCase().contains(searchTerm)) {
                pointers.add(pointer);
            }
        }
        for (QuestionnairePointer pointer : pointers) {
            offScreenQuestionnairePointers.remove(pointer);
        }
        visibleQuestionnairePointers.setAll(pointers);
    }

    public void questionnaireListViewSelectNone()
    {
        this.questionnairePointerListView.getSelectionModel().clearSelection();
        this.questionnaireSearchField.requestFocus();
    }

    // Context Transition Actions

    public void switchToPane(Pane pane)
    {
        for(Pane p : rightPanes)
        {
            p.setVisible((p == pane));
        }
    }

    public void showQuestionnaireDetails(Questionnaire questionnaire)
    {
        setupQuestionnaireView(questionnaire);
        switchToPane(questionnaireSelectedPane);
    }

    public void setupQuestionnaireView(Questionnaire questionnaire)
    {
        try
        {
            ArrayList<AnswerSet> allAnswers = DataLayer.getAnswerSetsForQuestionnaire(questionnaire);

            numberOfSubmissions.setText("" + allAnswers.size());
            numberOfQuestions.setText("" + questionnaire.getQuestions().size());
            questionnaireTitleLabel.setText(questionnaire.getTitle());

            updatePatientAnswerTable(allAnswers);
        }
        catch(NoQuestionnaireException | SQLException e)
        {
            Dialogs.showErrorDialog((Stage)this.root.getScene().getWindow(), "There was an error getting the questionnaires. Please contact support.");
            e.printStackTrace();
        }
    }

    // TableView Actions

    public void updatePatientAnswerTable(ArrayList<AnswerSet> answerSets) throws SQLException
    {
        patientsThatHaveAnswered.clear();
        for (AnswerSet set : answerSets)
        {
            Patient patient = DataLayer.getPatientByNSHNUmber(set.getPatientNHS());
            patientsThatHaveAnswered.add(patient);
        }
    }

}
