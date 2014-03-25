package GUI.ViewAnswers;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import Exporter.Exporter;
import ModelObjects.AnswerSet;
import ModelObjects.Answers.QuestionAnswerTableColumn;
import ModelObjects.Patient;
import ModelObjects.Questionnaire;
import ModelObjects.QuestionnairePointer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
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

    private ArrayList<AnswerSet> selectedQuestionnaireAnswerSets;
    private Questionnaire selectedQuestionnaire;

    @FXML private Label questionnaireTitleLabel;
    @FXML private Label numberOfSubmissions;
    @FXML private Label numberOfQuestions;

    @FXML private TableView<Patient> answerTable;
    @FXML private TableColumn<Patient, String> tableNSHcolumn;
    @FXML private TableColumn<Patient, String> tableFirstNameColumn;
    @FXML private TableColumn<Patient, String> tableLastNameColumn;

    @FXML private Button viewAnswerButton;
    @FXML private Button exportAnswersButton;

    private final ObservableList<Patient> patientsThatHaveAnswered = FXCollections.observableArrayList();

    /*************************
     * Answer View
     *************************/

    @FXML private Label answerViewNSH;
    @FXML private Label answerViewQuestionnaireTitle;

    @FXML private TableView<QuestionAnswerTableColumn> answerViewtable;
    @FXML private TableColumn<QuestionAnswerTableColumn, String> requiredColumn;
    @FXML private TableColumn<QuestionAnswerTableColumn, String> questionnaireTitleColumn;
    @FXML private TableColumn<QuestionAnswerTableColumn, String> answerColumn;

    private final ObservableList<QuestionAnswerTableColumn> displayAnswersList = FXCollections.observableArrayList();

    @FXML private Button backButton;

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

        // Setup buttons in layouts
        setupButtons();

        // Fetch the data for the left menu
        fetchQuestionnairePointers();
    }

    public void updateViewAnswersTable(AnswerSet a, Questionnaire q)
    {
        displayAnswersList.setAll(QuestionAnswerTableColumn.createListFromQuestionsAndAnswers(q, a));
        System.out.println(displayAnswersList.size());
    }

    public void setupViewAnswersTable()
    {
        this.answerColumn.setCellValueFactory(new PropertyValueFactory<QuestionAnswerTableColumn, String>("answers"));
        this.questionnaireTitleColumn.setCellValueFactory(new PropertyValueFactory<QuestionAnswerTableColumn, String>("questionTitle"));
        this.requiredColumn.setCellValueFactory(new PropertyValueFactory<QuestionAnswerTableColumn, String>("required"));
        this.answerViewtable.setItems(displayAnswersList);
    }

    public void updateAnswerView(AnswerSet answerSet, Questionnaire q)
    {
        answerViewNSH.setText(answerSet.getPatientNHS());
        answerViewQuestionnaireTitle.setText(q.getTitle());
        updateViewAnswersTable(answerSet, q);
    }

    public void viewAnswer()
    {
        Patient selectedPatient = answerTable.getSelectionModel().getSelectedItem();
        if(selectedPatient != null)
        {
            for(AnswerSet a : selectedQuestionnaireAnswerSets)
            {
                if(a.getPatientNHS().equals(selectedPatient.getNhsNumber()))
                {
                    setupViewAnswersTable();
                    updateAnswerView(a, selectedQuestionnaire);
                    switchToPane(answersPane);
                    break;
                }
            }
        }
    }

    public void setupButtons()
    {
        this.viewAnswerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewAnswer();
            }
        });

        this.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchToPane(questionnaireSelectedPane);
            }
        });

        this.exportAnswersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select where to save the answers");
                fileChooser.setInitialFileName("" + selectedQuestionnaire.getTitle() + ".csv");
                String path = fileChooser.showSaveDialog(root.getScene().getWindow()).getPath();
                try
                {
                    Exporter.exportQuestionnaireData(selectedQuestionnaire, path);
                }
                catch (NoQuestionnaireException e)
                {
                    System.err.println("Tried to get a questionnaire that does not exist.");
                }
            }
        });
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
            selectedQuestionnaire = questionnaire;
            selectedQuestionnaireAnswerSets = DataLayer.getAnswerSetsForQuestionnaire(questionnaire);

            numberOfSubmissions.setText("" + selectedQuestionnaireAnswerSets.size());
            numberOfQuestions.setText("" + questionnaire.getQuestions().size());
            questionnaireTitleLabel.setText(questionnaire.getTitle());

            updatePatientAnswerTable(selectedQuestionnaireAnswerSets);
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
