package GUI._Prototypes;

import Accessors.DataLayer;
import ModelObjects.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Me on 03/03/2014.
 */
public class AddPatientController implements Initializable {

    @FXML private TextField firstName, lastName, dateOfBirth, nhsNumber;
    @FXML private ListView<Patient> allPatients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void addPatientAction(Event event) {
        Date aDate = new Date();
        System.out.println(dateOfBirth.getText());
        Patient aPatient = new Patient(nhsNumber.getText(), firstName.getText(), "Foo Bar", lastName.getText(), aDate, "SE16 2TL", "null");
        try {
            DataLayer.addPatient(aPatient);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: This error needs to be handled in the GUI
        }
    }

    @FXML
    public void fetchAllPatientsAction(Event event) {
        ArrayList<Patient> patients = null;
        try {
            patients = DataLayer.getAllPatients();
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: This error needs to be handled in the GUI
        }
        ObservableList<Patient> myObservableList = FXCollections.observableList(patients);
        allPatients.setItems(myObservableList);
        System.out.println("Fetching...");
        allPatients.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>(){

            @Override
            public ListCell<Patient> call(ListView<Patient> p) {

                ListCell<Patient> cell = new ListCell<Patient>(){

                    @Override
                    protected void updateItem(Patient patient, boolean aBool) {
                        super.updateItem(patient, aBool);
                        if (patient != null) {
                            System.out.println(patient.getFirst_name());
                            setText(patient.getFirst_name() + " " + patient.getSurname());
                        }
                    }

                };

                return cell;
            }
        });
    }
}
