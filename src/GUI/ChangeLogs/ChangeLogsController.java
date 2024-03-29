package GUI.ChangeLogs;

import Accessors.DataLayer;
import ModelObjects.PatientLog;
import ModelObjects.PatientQuestionnaireLog;
import ModelObjects.QuestionnaireLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Ahmet Ersahin on 11/03/2014.
 *
 */
public class ChangeLogsController implements Initializable {

    // Container Panes
    @FXML private Accordion changeLogsAccordion;

    // Patient titled pane and list
    @FXML private ListView<PatientLog> patientLogList;
    // Questionnaire titled pane and list
    @FXML private ListView<QuestionnaireLog> questionnaireLogList;
    // Distribute Questionnaire titled pane and list
    @FXML private ListView<PatientQuestionnaireLog> distributeLogList;



    private ObservableList<PatientLog> allPatientsLog
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnaireLog> allQuestionnairesLog
            = FXCollections.observableArrayList();
    private ObservableList<PatientQuestionnaireLog> allDistributeLog
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateAllLists();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        patientLogList.setItems(allPatientsLog);
        questionnaireLogList.setItems(allQuestionnairesLog);
        distributeLogList.setItems(allDistributeLog);

        fetchAllLogs();
    }

    public void fetchAllLogs() {
        try {
            allPatientsLog.setAll(DataLayer.getAllPatientLogs());
            allQuestionnairesLog.setAll(DataLayer.getAllQuestionnaireLogs());
            allDistributeLog.setAll(DataLayer.getAllPatientQuestionnaireLogs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateAllLists() throws SQLException {

        DataLayer.populatePatientLogsUpdate();
        DataLayer.populatePatientLogsInsert();
        DataLayer.populatePatientLogsDelete();
        DataLayer.populateQuestionnaireLogsUpdate();
        DataLayer.populateQuestionnaireLogsInsert();
        DataLayer.populateQuestionnaireLogsDelete();
        DataLayer.populatePatientQuestionnaireLogsUpdate();
        DataLayer.populatePatientQuestionnaireLogsInsert();
        DataLayer.populatePatientQuestionnaireLogsDelete();
    }
}
