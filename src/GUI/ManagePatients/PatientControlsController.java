package GUI.ManagePatients;

import Accessors.DataLayer;
import Helpers.DateCheckHelper;
import Helpers.GUI.FlexibleToolbarSpace;
import ModelObjects.Patient;
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
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 04/03/2014.
 * Collaboration with Faizan Joya 20/03/2014.
 *
 */
public class PatientControlsController implements Initializable {

    @FXML private Parent root;

    // Left Pane Controls
    @FXML private TextField patientSearchField;
    @FXML private ListView<Patient> patientListView;

    // Form Input Controls
    @FXML private TextField nhsNumberField;
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField dayDOBField;
    @FXML private TextField monthDOBField;
    @FXML private TextField yearDOBField;
    @FXML private TextField postcodeField;

    // All Input Fields
    private TextField[] dataInputFields;
    // Subset of Input Fields that require input
    private TextField[] requiredFields;

    // Input Field information labels
    @FXML private Label nhsInformationLabel;
    @FXML private Label dobInformationLabel;
    @FXML private Label postcodeInformationLabel;

    // Right Pane Toolbar Controls
    @FXML private ToolBar rightPaneToolBar;
    private static Region flexibleSpace = new FlexibleToolbarSpace();
    private Button saveNewButton = new Button("Save As New"),
            cancelNewPatientButton = new Button("Cancel"),
            saveChangesButton = new Button("Save Changes"),
            deletePatientButton = new Button("Delete Patient"),
            deselectPatientButton = new Button("Deselect Patient"),
            clearFieldsButton = new Button("Clear Fields");

    // Left Pane Data
    private final ObservableList<Patient> visiblePatients
            = FXCollections.observableArrayList();
    private final ObservableList<Patient> offScreenPatients
            = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataInputFields = new TextField[]{nhsNumberField, firstNameField, middleNameField, lastNameField, dayDOBField,
                monthDOBField, yearDOBField, postcodeField};
        requiredFields = new TextField[]{nhsNumberField, firstNameField, lastNameField, dayDOBField,
                monthDOBField, yearDOBField};

        setupButtonActions();
        setupPatientListView();

        setInputFieldsEnabled(false);

        fetchAllPatients();
    }

    public void setupButtonActions() {
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
    }

    public void setupPatientListView() {
        patientListView.setItems(visiblePatients);
        patientListView.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>() {
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
            this.offScreenPatients.clear();
            this.visiblePatients.setAll(DataLayer.getAllPatients());
            searchInputChangedAction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fuzzySearchPatientsUsingSearchTerm(String searchTerm) {
        searchTerm = searchTerm.trim().toLowerCase();
        offScreenPatients.addAll(visiblePatients);
        visiblePatients.clear();
        ArrayList<Patient> patients = new ArrayList<>();
        for (Patient aPatient : offScreenPatients) {
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
        visiblePatients.addAll(patients);
    }

    public void searchInputChangedAction() {
        String searchTerm = patientSearchField.getText();
        if (searchTerm == null || searchTerm.equals("") ) {
            visiblePatients.addAll(offScreenPatients);
            offScreenPatients.clear();
        } else {
            fuzzySearchPatientsUsingSearchTerm(searchTerm);
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
        if (checkFieldValidation()) {
            String dob = yearDOBField.getText().trim() + "-" + monthDOBField.getText().trim() + "-" + dayDOBField.getText().trim();
            Patient updatedPatient = new Patient(nhsNumberField.getText(), firstNameField.getText().trim(),
                    middleNameField.getText().trim(), lastNameField.getText().trim(), dob, postcodeField.getText().toUpperCase().trim());
            try {
                DataLayer.updatePatient(updatedPatient);
                fetchAllPatients();
                clearWorkspace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteExistingPatient() {
        Patient selectedPatient = patientListView.getSelectionModel().getSelectedItem();
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
        if (checkFieldValidation()) {
            // Date Format : YYYY-MM-DD
            String dob = yearDOBField.getText().trim() + "-" + monthDOBField.getText().trim() + "-" + dayDOBField.getText().trim();
            Patient newPatient = new Patient(nhsNumberField.getText(), firstNameField.getText().trim(),
                    middleNameField.getText().trim(), lastNameField.getText().trim(), dob, postcodeField.getText().toUpperCase().trim());
            try {
                DataLayer.addPatient(newPatient);
                fetchAllPatients();
                clearWorkspace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Validation Methods

    public boolean checkRequiredFieldsAreCompleted() {
        boolean allFieldsFilled = true;
        for (TextField aField : requiredFields) {
            if (aField.getText().isEmpty()) {
                allFieldsFilled = false;
                Dialogs.showInformationDialog((Stage)root.getScene().getWindow(),
                        "Not all of the required fields (highlighted *) are complete",
                        "Please fill in all of the required fields",
                        "");
                break;
            }
        }
        return allFieldsFilled;
    }

    public boolean checkFieldValidation() {

        if (!checkRequiredFieldsAreCompleted()) {
            return false;
        }

        boolean allIsValid = true;
        String errorMessage = "";

        String nhsNumberString = nhsNumberField.getText();
        if (nhsNumberString.length() != 10 || !nhsNumberString.matches("^\\d{10}$")) {
            errorMessage += "NHSNumber needs to be exactly 10 digits \n";
            allIsValid = false;
        }

        String firstNameString = firstNameField.getText().trim();
        if (firstNameString.length() < 2 || firstNameString.length() > 20){
            errorMessage += "First name needs to be 2 to 20 characters long \n";
            allIsValid = false;
        }

        String middleNameString = middleNameField.getText().trim();
        if (!middleNameString.equals("") && (middleNameString.length() < 2 || middleNameString.length() > 20)){
            errorMessage += "Middle name needs to be 2 to 20 characters long if entered\n";
            allIsValid = false;
        }

        String lastNameString = lastNameField.getText().trim();
        if (lastNameString.length() < 2 || lastNameString.length() > 20){
            errorMessage += "Last name needs to be 2 to 20 characters long \n";
            allIsValid = false;
        }

        String day = dayDOBField.getText().trim();
        String month = monthDOBField.getText().trim();
        String year = yearDOBField.getText().trim();
        if (!DateCheckHelper.isDateValid(day, month, year) || Integer.parseInt(year) < 1885){
            errorMessage += "Please enter a valid date of birth. \n" +
                    "If date of birth is 1st January 2001 then enter 01-01-2001 \n";
            errorMessage = errorMessage.concat(DateCheckHelper.checkDMY(day, month, year));
            allIsValid = false;
        }

        String postcode = postcodeField.getText();
        postcode = postcode.toUpperCase().trim();
        if (!postcode.equals("") && !postcode.matches("(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) [0-9][A-Z-[CIKMOV]]{2})")) {
            errorMessage += "Postcode needs to be valid \n";
            allIsValid = false;
        }

        if (!allIsValid) {
            Dialogs.showInformationDialog((Stage)root.getScene().getWindow(),
                    errorMessage,
                    "Please check that the information you have entered is valid",
                    "");
        }

        return allIsValid;
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

    public void patientListViewSelectNone() {
        this.patientListView.getSelectionModel().clearSelection();
        this.patientSearchField.requestFocus();
    }

}


