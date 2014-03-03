package GUI.Controller;

import Accessors.DataLayer;
import Accessors.DatabaseAccessor;
import ModelObjects.Patient;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Me on 03/03/2014.
 */
public class AddPatientController implements Initializable {

    @FXML private TextField firstName, lastName, dateOfBirth, nhsNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void addPatientAction(Event event) {
        Date aDate = new Date();
        System.out.println(dateOfBirth.getText());
        Patient aPatient = new Patient(nhsNumber.getText(), firstName.getText(), "Foo Bar", lastName.getText(), aDate, "SE16 2TL", "null");
        DataLayer.insertPatient(aPatient);
    }

}
