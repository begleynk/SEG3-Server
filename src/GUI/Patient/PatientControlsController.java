package GUI.Patient;

import Accessors.DataLayer;
import Helpers.Controls.AlertDialog;
import ModelObjects.Patient;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    private Button saveNewButton, cancelNewPatientButton, saveChangesButton, deletePatientButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.saveNewButton = new Button("Save As New");
        this.saveChangesButton = new Button("Save Changes to Patient");
        this.cancelNewPatientButton = new Button("Clear Fields");
        this.deletePatientButton = new Button("Delete Patient");

        this.dataInputFields = new TextField[]{nhsNumberField, firstNameField, middleNameField, lastNameField, dayDOBField,
                monthDOBField, yearDOBField, postcodeField};
        this.requiredFields = new TextField[]{nhsNumberField, firstNameField, lastNameField, dayDOBField,
                monthDOBField, yearDOBField};

        setInputFieldsEnabled(false);

        this.saveNewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveNewPatient(null);
            }
        });
        this.cancelNewPatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearInputFields(null);
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
                            setText(patient.getNhsNumber() + ": " + patient.getFirst_name() + " " + patient.getSurname());
                        }
                    }

                };

                return cell;
            }
        });
    }

    public void searchInputChangedAction(Event event) {
        System.out.println(event.toString());
        if (patientSearchField.getText().toString().isEmpty()) {
            try {
                ArrayList<Patient> allPatients = DataLayer.getAllPatients();
                patientListView.getItems().setAll(allPatients);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // TODO: Fetch by searching
        }
    }

    public void setupViewForAddingPatientAction(Event event) {
        rightPaneToolBar.getItems().clear();
        rightPaneToolBar.getItems().addAll(saveNewButton, getFlexibleSpace(), cancelNewPatientButton);
        setInputFieldsEnabled(true);
    }

    public void existingPatientSelected(Event event) {
        rightPaneToolBar.getItems().clear();
        rightPaneToolBar.getItems().addAll(saveChangesButton, getFlexibleSpace(), deletePatientButton);
        setInputFieldsEnabled(true);
    }

    public void saveNewPatient(Event event) {
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
            } catch (SQLException e) {
                e.printStackTrace();
                // TODO: This error needs to be handled in the GUI
            }
        }
    }

    public void clearInputFields(Event event) {
        for (TextField aField : dataInputFields) {
            aField.setText("");
        }
    }

    public void setInputFieldsEnabled(boolean enabled) {
        for (TextField aField : dataInputFields) {
            aField.setDisable(!enabled);
        }
    }

    public Region getFlexibleSpace() {
        if (flexibleSpace == null) {
            flexibleSpace = new Region();
            HBox.setHgrow(flexibleSpace, Priority.ALWAYS);
        }
        return flexibleSpace;
    }
}
