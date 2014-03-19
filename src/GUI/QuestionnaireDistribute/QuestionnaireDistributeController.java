package GUI.QuestionnaireDistribute;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.Patient;
import ModelObjects.QuestionnairePointer;
import ModelObjects.TablePatient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 11/03/2014.
 * 
 */
public class QuestionnaireDistributeController implements Initializable {

    // Questionnaire controls
    @FXML private TextField searchQuestionnaireField;
    @FXML private ListView<QuestionnairePointer> questionnairePointerListView;

    // Patient Controls
    @FXML private TextField searchPatientField;
    @FXML private TableView<TablePatient> patientTableView;
    @FXML private TableColumn<TablePatient, String> nhsNumberColumn;
    @FXML private TableColumn<TablePatient, String> nameColumn;
    @FXML private TableColumn<TablePatient, Boolean> checkBoxColumn;

    private QuestionnairePointer selectedQuestionnairePointer;

    private ObservableList<QuestionnairePointer> allQuestionnairePointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> matchedQuestionnairePointers
            = FXCollections.observableArrayList();

    private ObservableList<TablePatient> visiblePatients
            = FXCollections.observableArrayList();
    private ObservableList<TablePatient> offScreenPatients
            = FXCollections.observableArrayList();

    protected static HashMap<String, Boolean> isAssignedMap;

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
                        if (old_pointer != null) {
                            assignAction();
                            System.out.println("Auto Saved Assignments");
                        }
                        selectedQuestionnairePointer = new_pointer;
                        if (new_pointer != null) {
                            ArrayList<TablePatient> patients = new ArrayList<>(visiblePatients);
                            try {
                                isAssignedMap = DataLayer.areTablePatientsAssignedToQuestionnaire(patients, selectedQuestionnairePointer);
                                for (TablePatient patient : visiblePatients) {
                                    if (isAssignedMap.get(patient.getNhsNumber())) {
                                        patient.setProperty_is_assigned(true);
                                        patient.setOrignal_assignment(true);
                                    } else {
                                        patient.setProperty_is_assigned(false);
                                        patient.setOrignal_assignment(false);
                                    }
                                }
                                for (TablePatient patient : offScreenPatients) {
                                    if (isAssignedMap.get(patient.getNhsNumber())) {
                                        patient.setProperty_is_assigned(true);
                                        patient.setOrignal_assignment(true);
                                    } else {
                                        patient.setProperty_is_assigned(false);
                                        patient.setOrignal_assignment(false);
                                    }
                                }
                                patientSearchInputChangedAction();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (!patientTableView.getColumns().contains(checkBoxColumn)) {
                                patientTableView.getColumns().add(checkBoxColumn);
                            }
                        } else {
                            for (TablePatient patient : visiblePatients) {
                                patient.setProperty_is_assigned(false);
                                patient.setOrignal_assignment(false);
                            }
                            for (TablePatient patient : offScreenPatients) {
                                patient.setProperty_is_assigned(false);
                                patient.setOrignal_assignment(false);
                            }
                            isAssignedMap.clear();
                            patientTableView.getColumns().remove(checkBoxColumn);
                        }
                    }
                }
        );

        this.nhsNumberColumn.setCellValueFactory(new PropertyValueFactory<TablePatient, String>("property_nhs_number"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<TablePatient, String>("property_full_name"));
        this.checkBoxColumn.setCellValueFactory(new PropertyValueFactory<TablePatient, Boolean>("property_is_assigned"));
        this.checkBoxColumn.setCellFactory(new Callback<TableColumn<TablePatient, Boolean>, TableCell<TablePatient, Boolean>>() {
            public TableCell<TablePatient, Boolean> call(TableColumn<TablePatient, Boolean> p) {
                return new TableCellCheckBox<>();
            }
        });

        this.patientTableView.setEditable(true);
        this.patientTableView.getColumns().remove(this.checkBoxColumn);
        this.patientTableView.setItems(visiblePatients);

        this.questionnairePointerListView.setItems(matchedQuestionnairePointers);

        fetchDeployedQuestionnaires();
        fetchAllPatients();
    }

    // Questionnaire View Methods

    // Fetching Menu data [QuestionnairePointer(s)]
    public void fetchDeployedQuestionnaires() {
        try {
            this.allQuestionnairePointers.clear();
            this.allQuestionnairePointers.addAll(DataLayer.getQuestionnairePointersForState(1));
            questionnaireSearchInputChangedAction();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void questionnaireSearchInputChangedAction() {
        String searchTerm = searchQuestionnaireField.getText();
        if (searchTerm == null || searchTerm.equals("") ) {
            matchedQuestionnairePointers.setAll(allQuestionnairePointers);
        } else {
            fuzzySearchQuestionnairePointerUsingSearchTerm(searchTerm);
        }
    }

    public ObservableList<QuestionnairePointer> fuzzySearchQuestionnairePointerUsingSearchTerm(String searchTerm) {
        searchTerm = searchTerm.trim().toLowerCase();
        ArrayList<QuestionnairePointer> pointers = new ArrayList<>();
        for (QuestionnairePointer pointer : allQuestionnairePointers) {
            if (pointer.getTitle().toLowerCase().startsWith(searchTerm) ||
                    pointer.getTitle().toLowerCase().contains(searchTerm)) {
                pointers.add(pointer);
            }
        }
        matchedQuestionnairePointers.setAll(pointers);
        return matchedQuestionnairePointers;
    }

    public void deselectQuestionnaire() {
        questionnairePointerListView.getSelectionModel().clearSelection();
    }

    // Patient View Methods

    public void fetchAllPatients() {
        try {
            this.visiblePatients.clear();
            this.offScreenPatients.clear();
            this.visiblePatients.addAll(DataLayer.getAllTablePatients());
            patientSearchInputChangedAction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void patientSearchInputChangedAction() {
        String searchTerm = searchPatientField.getText();
        if (searchTerm == null || searchTerm.equals("") ) {
            visiblePatients.addAll(offScreenPatients);
            offScreenPatients.clear();
        } else {
            fuzzySearchPatientsUsingSearchTerm(searchTerm);
        }

        //System.out.println("Visible :" + visiblePatients.size());
        //System.out.println("OffScreen :" + offScreenPatients.size());
    }

    public ObservableList<TablePatient> fuzzySearchPatientsUsingSearchTerm(String searchTerm) {
        searchTerm = searchTerm.trim().toLowerCase();
        offScreenPatients.addAll(visiblePatients);
        visiblePatients.clear();
        ArrayList<TablePatient> patients = new ArrayList<>();
        for (TablePatient aPatient : offScreenPatients) {
            if (aPatient.getNhsNumber().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getFirst_name().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getMiddle_name().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getSurname().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getDateOfBirth().toLowerCase().startsWith(searchTerm) ||
                    aPatient.getPostcode().toLowerCase().startsWith(searchTerm)) {
                patients.add(aPatient);
            }
        }
        for (Patient patient : patients) {
            offScreenPatients.remove(patient);
        }
        visiblePatients.setAll(patients);
        return visiblePatients;
    }

    // Assign Action

    public void assignAction() {
        if (selectedQuestionnairePointer != null) {
            searchPatientField.setText("");
            patientSearchInputChangedAction();
            for (TablePatient patient : visiblePatients) {
                if (patient.getOrignal_assignment() && !patient.getProperty_is_assigned())
                {
                    // Remove assignment
                    try {
                        DataLayer.unlinkPatientAndQuestionnaire(patient, selectedQuestionnairePointer);
                        patient.setOrignal_assignment(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (!patient.getOrignal_assignment() && patient.getProperty_is_assigned())
                {
                    // Add assignment
                    try {
                        DataLayer.linkPatientAndQuestionnaire(patient, selectedQuestionnairePointer);
                        patient.setOrignal_assignment(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class TableCellCheckBox<S, T> extends TableCell<S, T> {
        private final CheckBox checkBox;
        private ObservableValue<T> ov;

        public TableCellCheckBox() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);

            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
        }
    }
}
