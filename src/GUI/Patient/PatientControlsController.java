package GUI.Patient;

import Accessors.DataLayer;
import Helpers.GUI.AlertDialog;
import Helpers.GUI.FlexibleToolbarSpace;
import ModelObjects.Patient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Me on 04/03/2014.
 */
public class PatientControlsController implements Initializable {

    @FXML private Parent root;

    // Left Pane Controls
    @FXML private TextField patientSearchField;
    @FXML private ListView patientListView;

    // Form Input Controls
    @FXML private TextField nhsNumberField;
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField dayDOBField;
    @FXML private TextField monthDOBField;
    @FXML private TextField yearDOBField;
    @FXML private TextField postcodeField;
    private TextField[] dataInputFields; // All Input Fields
    private TextField[] requiredFields; // Subset of Input Fields that require input

    // Right Pane Toolbar Controls
    @FXML private ToolBar rightPaneToolBar;
    private static Region flexibleSpace;
    private Button saveNewButton, clearFieldsButton, cancelNewPatientButton,
            saveChangesButton, deselectPatientButton, deletePatientButton;

    // Left Pane Data
    private ArrayList<Patient> allPatients;
    private ArrayList<Patient> matchedPatients;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.saveNewButton = new Button("Save As New");
        this.saveChangesButton = new Button("Save Changes to Patient");
        this.clearFieldsButton = new Button("Clear Fields");
        this.cancelNewPatientButton = new Button("Cancel");
        this.deletePatientButton = new Button("Delete Patient");
        this.deselectPatientButton = new Button("Deselect Patient");

        this.matchedPatients = new ArrayList<Patient>();
        this.flexibleSpace = new FlexibleToolbarSpace();

        this.patientSearchField.setText("");

        this.dataInputFields = new TextField[]{nhsNumberField, firstNameField, middleNameField, lastNameField, dayDOBField,
                monthDOBField, yearDOBField, postcodeField};
        this.requiredFields = new TextField[]{nhsNumberField, firstNameField, lastNameField, dayDOBField,
                monthDOBField, yearDOBField};

        setInputFieldsEnabled(false);
        fetchAllPatients();

        this.saveNewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveNewPatient();
            }
        });
        this.clearFieldsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearInputFields();
            }
        });
        this.cancelNewPatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearWorkspace();
            }
        });
        this.saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveEditedPatient();
            }
        });
        this.deletePatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteExistingPatient();
            }
        });
        this.deselectPatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                patientListView.getSelectionModel().clearSelection();
            }
        });

        patientListView.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>() {
            @Override
            public ListCell<Patient> call(ListView<Patient> p) {
                ListCell<Patient> cell = new ListCell<Patient>() {
                    @Override
                    protected void updateItem(Patient patient, boolean aBool) {
                        super.updateItem(patient, aBool);
                        if (patient != null) {
                            setText("#" + patient.getNhsNumber() + ": \n" + patient.getFirst_name() + " " + patient.getSurname());
                        }
                    }

                };
                return cell;
            }
        });
        patientListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Patient>() {
                    public void changed(ObservableValue<? extends Patient> ov, Patient old_val, Patient new_val) {
                    existingPatientSelected(new_val);
                }
        });
    }

    // Main View Transitions

    public void setupViewForAddingPatientAction() {
        clearWorkspace();
        setInputFieldsEnabled(true);
        rightPaneToolBar.getItems().addAll(saveNewButton, flexibleSpace, cancelNewPatientButton, clearFieldsButton);
    }

    public void setupViewForEditingPatientAction() {
        clearWorkspace();
        setInputFieldsEnabled(true);
        this.nhsNumberField.setDisable(true);
        rightPaneToolBar.getItems().addAll(saveChangesButton, flexibleSpace, deselectPatientButton, deletePatientButton);
    }

    // Patient Search

    public void fetchAllPatients() {
        try {
            this.allPatients = DataLayer.getAllPatients();
            searchInputChangedAction(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Patient> fuzzySearchPatientsUsingSearchTerm(String searchTerm) {
        matchedPatients.clear();
        searchTerm = searchTerm.trim();
        searchTerm = searchTerm.toLowerCase();
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

    public void searchInputChangedAction(Event event) {
        String searchTerm = null;
        if (patientSearchField != null) {
            searchTerm = patientSearchField.getText().toString();
        }
        patientListView.getSelectionModel().clearSelection();
        if (searchTerm.isEmpty() || searchTerm == null) {
            patientListView.getItems().setAll(allPatients);
        } else {
            patientListView.getItems().setAll(fuzzySearchPatientsUsingSearchTerm(searchTerm));
        }
    }

    // Editing Existing Patients

    public void existingPatientSelected(Patient aPatient) {
        setupViewForEditingPatientAction();
        if (aPatient != null) {
        String[] dob = aPatient.getDateOfBirth().split("-");
            this.nhsNumberField.setText(aPatient.getNhsNumber());
            this.firstNameField.setText(aPatient.getFirst_name());
            this.middleNameField.setText(aPatient.getMiddle_name());
            this.lastNameField.setText(aPatient.getSurname());
            this.yearDOBField.setText(dob[0]);
            this.monthDOBField.setText(dob[1]);
            this.dayDOBField.setText(dob[2]);
            this.postcodeField.setText(aPatient.getPostcode());
        } else {
            clearWorkspace();
        }
    }

    public void saveEditedPatient() {
        boolean allFieldsFilled = true;
        for (TextField aField : requiredFields) {
            if (aField.getText().toString().isEmpty()) {
                allFieldsFilled = false;
                new AlertDialog((Stage)root.getScene().getWindow(), "You cannot leave any required fields empty",
                        AlertDialog.ICON_INFO).showAndWait();
                break;
            }
        }
        if (allFieldsFilled) {
            // Date Format : YYYY-MM-DD
            String dob = yearDOBField.getText() + "-" + monthDOBField.getText() + "-" + dayDOBField.getText();
            Patient newPatient = new Patient(nhsNumberField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(), dob, postcodeField.getText(), null);
            try {
                DataLayer.addPatient(newPatient);
                fetchAllPatients();
                clearWorkspace();
            } catch (SQLException e) {
                e.printStackTrace();
                // TODO: This error needs to be handled in the GUI
            }
        }
    }

    public void deleteExistingPatient() {
        Patient selectedPatient = (Patient) patientListView.getSelectionModel().getSelectedItem();
        try {
            DataLayer.removePatient(selectedPatient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fetchAllPatients();
        clearWorkspace();
    }

    // Creating New Patients

    public void saveNewPatient() {
        boolean allFieldsFilled = true;
        for (TextField aField : requiredFields) {
            if (aField.getText().toString().isEmpty()) {
                allFieldsFilled = false;
                new AlertDialog((Stage)root.getScene().getWindow(), "Please fill in all of the required fields",
                        AlertDialog.ICON_INFO).showAndWait();
                break;
            }
        }
        if (allFieldsFilled) {
            // Date Format : YYYY-MM-DD
            String dob = yearDOBField.getText() + "-" + monthDOBField.getText() + "-" + dayDOBField.getText();
            Patient newPatient = new Patient(nhsNumberField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(), dob, postcodeField.getText(), null);
            try {
                DataLayer.addPatient(newPatient);
                fetchAllPatients();
                clearWorkspace();
            } catch (SQLException e) {
                e.printStackTrace();
                // TODO: This error needs to be handled in the GUI
            }
        }
    }

    // Preparation for Context Transitions

    public void clearWorkspace() {
        clearInputFields();
        setInputFieldsEnabled(false);
        rightPaneToolBar.getItems().clear();
    }

    public void clearInputFields() {
        for (TextField aField : dataInputFields) {
            aField.setText("");
        }
    }

    public void setInputFieldsEnabled(boolean enabled) {
        for (TextField aField : dataInputFields) {
            aField.setDisable(!enabled);
        }
    }
}
