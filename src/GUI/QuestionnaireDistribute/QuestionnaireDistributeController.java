package GUI.QuestionnaireDistribute;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.Patient;
import ModelObjects.QuestionnairePointer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by ahmetersahin on 11/03/2014.
 */
public class QuestionnaireDistributeController implements Initializable {

    // Questionnaire controls
    @FXML private TextField searchQuestionnaireField;
    @FXML private ListView<QuestionnairePointer> questionnairePointerListView;

    // Patient Controls
    @FXML private TextField searchPatientField;
    @FXML private ListView<Patient> patientListView;
    @FXML private ChoiceBox customGroupsChooser;

    private QuestionnairePointer selectedQuestionnairePointer;
    private ArrayList<Patient> selectedPatients;

    private ObservableList<QuestionnairePointer> allQuestionnairePointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> matchedQuestionnairePointers
            = FXCollections.observableArrayList();

    private ObservableList<Patient> allPatients
            = FXCollections.observableArrayList();
    private ObservableList<Patient> matchedPatients
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setup Questionnaire Pointed ListView
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
                        selectedQuestionnairePointer = new_pointer;
                    }
                }
        );

        this.patientListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.patientListView.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>() {
            @Override
            public ListCell<Patient> call(ListView<Patient> p) {
                return new ListCell<Patient>() {
                    @Override
                    protected void updateItem(Patient patient, boolean aBool) {
                        super.updateItem(patient, aBool);
                        if (patient != null) {
                            setText("#" + patient.getNhsNumber() + ": \n" + patient.getFirst_name() + " " + patient.getSurname());
                        }
                    }

                };
            }
        });

        fetchDeployedQuestionnaires();
        fetchAllPatients();
    }

    // Questionnaire View Methods

    // Fetching Menu data [QuestionnairePointer(s)]
    public void fetchDeployedQuestionnaires() {
        try {
            this.allQuestionnairePointers.setAll(DataLayer.getQuestionnairePointersForState(1));
            questionnaireSearchInputChangedAction();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void questionnaireSearchInputChangedAction() {
        String searchTerm = searchQuestionnaireField.getText();
        questionnairePointerListView.getSelectionModel().clearSelection();
        if (searchTerm == null || searchTerm.equals("") ) {
            questionnairePointerListView.setItems(allQuestionnairePointers);
        } else {
            questionnairePointerListView.setItems(fuzzySearchQuestionnairePointerUsingSearchTerm(searchTerm));
        }
    }

    public ObservableList<QuestionnairePointer> fuzzySearchQuestionnairePointerUsingSearchTerm(String searchTerm) {
        matchedQuestionnairePointers.clear();
        searchTerm = searchTerm.trim().toLowerCase();
        for (QuestionnairePointer pointer : allQuestionnairePointers) {
            if (pointer.getTitle().toLowerCase().startsWith(searchTerm) ||
                    pointer.getTitle().toLowerCase().contains(searchTerm)) {
                matchedQuestionnairePointers.add(pointer);
            }
        }
        return matchedQuestionnairePointers;
    }

    public void deselectQuestionnaire() {
        questionnairePointerListView.getSelectionModel().clearSelection();
    }

    // Patient View Methods

    public void fetchAllPatients() {
        try {
            this.allPatients.setAll(DataLayer.getAllPatients());
            patientSearchInputChangedAction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Patient> fuzzySearchPatientsUsingSearchTerm(String searchTerm) {
        matchedPatients.clear();
        searchTerm = searchTerm.trim().toLowerCase();
        for (Patient aPatient : allPatients) {
            if (aPatient.getNhsNumber().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getFirst_name().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getMiddle_name().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getSurname().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getDateOfBirth().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getPostcode().toLowerCase().startsWith(searchTerm)) {
                matchedPatients.add(aPatient);
            }
        }
        return matchedPatients;
    }

    public void patientSearchInputChangedAction() {
        String searchTerm = searchPatientField.getText();
        patientListView.getSelectionModel().clearSelection();
        if (searchTerm == null || searchTerm.equals("") ) {
            patientListView.setItems(allPatients);
        } else {
            patientListView.setItems(fuzzySearchPatientsUsingSearchTerm(searchTerm));
        }
    }

    public void getPatientSelection() {
        selectedPatients = new ArrayList<>(patientListView.getSelectionModel().getSelectedItems());
        //System.out.println(selectedPatients);
    }

    public void selectAllPatients() {
        patientListView.getSelectionModel().selectAll();
    }

    public void deselectPatients() {
        patientListView.getSelectionModel().clearSelection();
    }

    // Assign Action

    public void assignAction() {
        getPatientSelection();
        if (selectedQuestionnairePointer != null && selectedPatients.size() > 0) {
            // TODO: Assign Action in DataLayer
            System.out.println(selectedQuestionnairePointer.toString());
            for (Patient patient : selectedPatients) {
                System.out.println(patient.toString());
            }
            questionnairePointerListView.getSelectionModel().clearSelection();
            patientListView.getSelectionModel().clearSelection();
        }
    }
}
