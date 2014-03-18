package GUI.ChangeLogs;

import Accessors.DataLayer;
import ModelObjects.Patient;
import ModelObjects.PatientLog;
import ModelObjects.QuestionnaireLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by ahmetersahin on 11/03/2014.
 *
 */
public class ChangeLogsController implements Initializable {

    // General
    @FXML private Accordion changeLogsAccordion;

    @FXML private ListView patientLogList;
    // Questionnaire titled pane and list
    @FXML private ListView questionnaireLogList;

    private ObservableList<PatientLog> allPatientsLog
            = FXCollections.observableArrayList();

    private ObservableList<QuestionnaireLog> allQuestionnairesLog
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateBothLists();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SHIT IS NOT WORKING!!!!!!!!!!!");
        }
        fetchAllLogs();
    }

    public void fetchAllLogs() {
        try {
            allPatientsLog = FXCollections.observableArrayList(DataLayer.getAllPatientLogs());
            allQuestionnairesLog = FXCollections.observableArrayList(DataLayer.getAllQuestionnaireLogs());
            patientLogList.getItems().setAll(allPatientsLog);
            questionnaireLogList.getItems().setAll(allQuestionnairesLog);

            System.out.println(allPatientsLog);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateBothLists() throws SQLException {

        DataLayer.populatePatientLogsUpdate();
        DataLayer.populatePatientLogsInsert();
        DataLayer.populatePatientLogsDelete();
        DataLayer.populateQuestionnaireLogsUpdate();
        DataLayer.populateQuestionnaireLogsInsert();
        DataLayer.populateQuestionnaireLogsDelete();

    }

}
