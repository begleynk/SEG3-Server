package Accessors;

import Exceptions.NoQuestionnaireException;
import Helpers.OSHelper;
import ModelObjects.*;
import ModelObjects.Questions.Question;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Niklas Begley on 27/02/2014.
 *
 */
public class DatabaseAccessor {

    private Connection connection;

    private String[] states = {"Draft", "Deployed", "Archived"};

    public DatabaseAccessor()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Database connector not found.");
            e.printStackTrace();
        }
        try
        {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + OSHelper.getStoragePath() + "/database.db");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /************************************************************
     QUESTIONNAIRE METHODS
     *************************************************************/

    public ArrayList<QuestionnairePointer> getAllQuestionnaires() throws SQLException
    {
        String query = "SELECT * FROM Questionnaire;";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.execute();
        ResultSet result = statement.getResultSet();

        ArrayList<QuestionnairePointer> pointerList = new ArrayList<>();

        while(result.next())
        {
            QuestionnairePointer questionnairePointer = new QuestionnairePointer(result.getInt(1), result.getString(2), result.getString(3));
            pointerList.add(questionnairePointer);
        }

        return pointerList;
    }

    public QuestionnairePointer getQuestionnaireByID(int id) throws SQLException
    {
        String query = "SELECT * FROM Questionnaire WHERE Q_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        statement.execute();
        ResultSet result = statement.getResultSet();

        if(result.next())
        {
            return new QuestionnairePointer(result.getInt(1), result.getString(2), result.getString(3));
        }
        else
        {
            return null;
        }
    }

    public ArrayList<QuestionnairePointer> getAllQuestionnairesForState(int state) throws SQLException
    {
        String query = "SELECT * FROM Questionnaire WHERE Q_state = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, states[state]);

        statement.execute();
        ResultSet result = statement.getResultSet();

        ArrayList<QuestionnairePointer> pointerList = new ArrayList<>();

        while(result.next())
        {
            QuestionnairePointer questionnairePointer = new QuestionnairePointer(result.getInt(1), result.getString(2), result.getString(3));
            pointerList.add(questionnairePointer);
        }

        return pointerList;
    }

    public Questionnaire insertQuestionnaireRecord(Questionnaire questionnaire) throws SQLException
    {
        String query = "INSERT INTO Questionnaire (Q_title, Q_state) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, questionnaire.getTitle());
        statement.setString(2, questionnaire.getState());
        statement.execute();
        ResultSet keys = statement.getGeneratedKeys();
        if(keys.next())
        {
            questionnaire.set_id(keys.getInt(1));
            return questionnaire;
        }
        else
        {
            throw new SQLException("Error inserting Questionnaire record.");
        }
    }

    /*
    The questionnaire must have an ID value!
    */
    public boolean removeQuestionnaire(Questionnaire questionnaire) throws SQLException
    {
        if(questionnaire.getId() == 0)
        {
            // Must have an id to remove!
            return false;
        }
        String query = "DELETE FROM Questionnaire WHERE Q_id= ? ;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, questionnaire.getId());
        statement.execute();
        return true;
    }

    public boolean setQuestionnairePointerState(QuestionnairePointer pointer, String state) throws SQLException, NoQuestionnaireException
    {

        String query = "UPDATE Questionnaire SET Q_state = ? WHERE Q_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, state);
        statement.setInt(2, pointer.getId());
        int result = statement.executeUpdate();

        if(result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /************************************************************
     PATIENT METHODS
     *************************************************************/

    public ArrayList<Patient> getAllPatients() throws SQLException
    {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        String query = "SELECT * FROM Patient";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while(result.next())
        {
            Patient patient = new Patient(result.getString(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5),result.getString(6));
            patients.add(patient);
        }
        return patients;
    }

    public ArrayList<TablePatient> getAllTablePatients() throws SQLException
    {
        ArrayList<TablePatient> patients = new ArrayList<TablePatient>();
        String query = "SELECT * FROM Patient";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        ResultSet result = statement.getResultSet();
        while(result.next())
        {
            TablePatient patient = new TablePatient(result.getString(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5),result.getString(6));
            patients.add(patient);
        }
        return patients;
    }

    public Patient getPatientByNHSNumber(String nhsNumber) throws SQLException
    {
        String query = "SELECT * FROM Patient WHERE P_NHS_Number = ? ;";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, nhsNumber);
        statement.execute();
        ResultSet result = statement.getResultSet();

        if (result.next())
        {
            Patient patient = new Patient(result.getString(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5),result.getString(6));
            return patient;
        }
        else
        {
            return null;
        }
    }

    public int updatePatientRecord(Patient patient) throws SQLException
    {
        Statement statement = createStatement();
        String query = "UPDATE Patient SET P_first_name = '" + patient.getFirst_name() + "', " + "P_middle_name = '" + patient.getMiddle_name() + "', " +
                "P_surname = '" + patient.getSurname() + "', " +
                "P_date_of_birth = '" + patient.getDateOfBirth() + "', " +
                "P_postcode = '" + patient.getPostcode() + "' " +
                "WHERE P_NHS_number = '" + patient.getNhsNumber() + "';";
        return statement.executeUpdate(query);
    }

    public Patient insertPatientRecord(Patient patient) throws SQLException
    {
        Statement statement = createStatement();
        String query = "INSERT INTO Patient(P_NHS_number, P_first_name, P_middle_name, P_surname, P_date_of_birth, P_postcode) VALUES('" +
                patient.getNhsNumber() + "','" +
                patient.getFirst_name() + "','" +
                patient.getMiddle_name() + "','" +
                patient.getSurname() + "','" +
                patient.getDateOfBirth() + "','" +
                patient.getPostcode() + "');";
        statement.execute(query);
        return patient;
    }

    public boolean removePatient(Patient patient) throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("DELETE FROM Patient WHERE P_NHS_number = " + patient.getNhsNumber() + ";");
        insertRemovedPatientRecord(patient);
        linkRemovedPatientAndMultipleQuestionnairePointers(patient, getQuestionnairePointersForPatient(patient));
        unlinkPatientAndMultipleQuestionnairePointers(patient, getQuestionnairePointersForPatient(patient));


        return true;
    }

    /************************************************************
     REMOVED_PATIENT METHOD
     *************************************************************/

    public  Patient insertRemovedPatientRecord(Patient patient) throws SQLException
    {
        Statement statement = createStatement();
        String query = "INSERT INTO Removed_Patient(P_NHS_number, P_first_name, P_middle_name, P_surname, P_date_of_birth, P_postcode) VALUES('" +
                patient.getNhsNumber() + "','" +
                patient.getFirst_name() + "','" +
                patient.getMiddle_name() + "','" +
                patient.getSurname() + "','" +
                patient.getDateOfBirth() + "','" +
                patient.getPostcode() + "');";
        statement.execute(query);
        return patient;
    }

    /************************************************************
     REMOVED_PATIENT_QUESTIONNAIRE METHOD
     *************************************************************/

    public boolean linkRemovedPatientAndMultipleQuestionnairePointers(Patient patient, ArrayList<QuestionnairePointer> questionnaires) throws SQLException
    {
        for(QuestionnairePointer questionnairePointer : questionnaires){
            if (questionnairePointer.getId() == 0 || patient.getNhsNumber().equals(""))
            {
                return false;
            }
            Statement statement = createStatement();
            statement.execute("INSERT INTO Removed_Patient_Questionnaire (P_NHS_number, Q_id, Completed) VALUES ('" +
                    patient.getNhsNumber() + "','" +
                    questionnairePointer.getId() + "', '0');");

        }
        return true;

    }


    /************************************************************
     QUESTIONNAIRE_PATIENT METHODS
     *************************************************************/

    public ArrayList<QuestionnairePointer> getQuestionnairePointersForPatient(Patient patient) throws SQLException
    {
        Statement statement = createStatement();
        String query = "SELECT Questionnaire.Q_id, Questionnaire.Q_title, Questionnaire.Q_state " +
                "FROM Patient_Questionnaire " +
                "INNER JOIN Questionnaire " +
                "ON Patient_Questionnaire.Q_id = Questionnaire.Q_id  " +
                "WHERE Completed = 0 AND Q_state = 'Deployed' AND P_NHS_number ='" + patient.getNhsNumber() +"';";
        System.out.println(query);
        ResultSet result = statement.executeQuery(query);

        ArrayList<QuestionnairePointer> pointerList = new ArrayList<>();
        while(result.next())
        {
            QuestionnairePointer questionnairePointer = new QuestionnairePointer(result.getInt(1), result.getString(2), result.getString(3));
            pointerList.add(questionnairePointer);
        }
        return pointerList;
    }

    public boolean linkPatientAndQuestionnaire(Patient patient, Questionnaire questionnaire) throws SQLException
    {
        if(questionnaire.getId() == 0 || patient.getNhsNumber().equals(""))
        {
            return false;
        }
        Statement statement = createStatement();
        statement.execute("INSERT INTO Patient_Questionnaire (P_NHS_number, Q_id, Completed) VALUES ('" +
                patient.getNhsNumber() + "," +
                questionnaire.getId() + ", 0');");
        return true;
    }

    public boolean linkPatientAndQuestionnairePointer(Patient patient, QuestionnairePointer questionnaire) throws SQLException
    {
        if (questionnaire.getId() == 0 || patient.getNhsNumber().equals(""))
        {
            return false;
        }
        Statement statement = createStatement();
        statement.execute("INSERT INTO Patient_Questionnaire (P_NHS_number, Q_id, Completed) VALUES ('" +
                patient.getNhsNumber() + "','" +
                questionnaire.getId() + "', '0');");
        return true;
    }

    public boolean linkPatientAndMultipleQuestionnairePointers (Patient patient, ArrayList<QuestionnairePointer> questionnaires) throws SQLException {
        for(QuestionnairePointer questionnairePointer : questionnaires){
            if (questionnairePointer.getId() == 0 || patient.getNhsNumber().equals(""))
            {
                return false;
            }
            Statement statement = createStatement();
            statement.execute("INSERT INTO Patient_Questionnaire (P_NHS_number, Q_id, Completed) VALUES ('" +
                    patient.getNhsNumber() + "','" +
                    questionnairePointer.getId() + "', '0');");
        }
        return true;
    }

    /*public boolean linkMultiplePatientsAndMultipleQuestionnairePointers(ArrayList<Patient> patients, ArrayList<QuestionnairePointer> questionnaires) throws SQLException
    {
        for(QuestionnairePointer questionnairePointer : questionnaires){
            for(Patient patient : patients){

                if (questionnairePointer.getId() == 0 || patient.getNhsNumber().equals(""))
                {
                    return false;
                }
                Statement statement = createStatement();
                statement.execute("INSERT INTO Patient_Questionnaire (P_NHS_number, Q_id, Completed) VALUES ('" +
                        patient.getNhsNumber() + "','" +
                        questionnairePointer.getId() + "', '0');");

            }
        }
        return true;
    }
    **/

    public boolean unlinkPatientAndQuestionnairePointer(Patient patient, QuestionnairePointer questionnaire) throws SQLException
    {
        if (questionnaire.getId() == 0 || patient.getNhsNumber().equals(""))
        {
            return false;
        }
        Statement statement = createStatement();
        statement.execute("DELETE FROM Patient_Questionnaire WHERE P_NHS_number = '" +
                patient.getNhsNumber() + "' AND Q_id = '" +
                questionnaire.getId() + "'");
        return true;
    }

    public boolean unlinkPatientAndMultipleQuestionnairePointers(Patient patient, ArrayList<QuestionnairePointer> questionnaires) throws SQLException
    {
        for(QuestionnairePointer questionnairePointer : questionnaires){
            if (questionnairePointer.getId() == 0 || patient.getNhsNumber().equals(""))
            {
                return false;
            }
            Statement statement = createStatement();
            statement.execute("DELETE FROM Patient_Questionnaire WHERE P_NHS_number = '" +
                    patient.getNhsNumber() + "' AND Q_id = '" +
                    questionnairePointer.getId() + "'");
        }
        return true;

    }

    /*public boolean unlinkMultiplePatientsAndMultipleQuestionnairePointers(ArrayList<Patient> patients, ArrayList<QuestionnairePointer> questionnaires) throws SQLException
    {
        for(QuestionnairePointer questionnairePointer : questionnaires){
            for(Patient patient : patients){

                if (questionnairePointer.getId() == 0 || patient.getNhsNumber().equals(""))
                {
                    return false;
                }
                Statement statement = createStatement();
                statement.execute("DELETE FROM Patient_Questionnaire WHERE P_NHS_number = '" +
                        patient.getNhsNumber() + "' AND Q_id = '" +
                        questionnairePointer.getId() + "'");
            }
        }
        return true;
    }
    **/



    public boolean isPatientAssignedToQuestionnaire(Patient patient, QuestionnairePointer questionnaire) throws SQLException {
        if (questionnaire == null || questionnaire.getId() == 0 || patient.getNhsNumber().equals(""))
        {
            return false;
        }
        Statement statement = createStatement();
        String query = "SELECT * FROM Patient_Questionnaire WHERE P_NHS_Number = '" + patient.getNhsNumber() + "' AND Q_id = '" + questionnaire.getId() + "';";

        ResultSet result = statement.executeQuery(query);
        if (result.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean setPatientQuestionnaireAsCompleted(QuestionnairePointer questionnaire, Patient patient) throws SQLException
    {
        if(isPatientAssignedToQuestionnaire(patient, questionnaire))
        {
            System.out.println("Patient NHS: " + patient.getNhsNumber());
            System.out.println("Questionnaire ID: " + questionnaire.getId());
            String query = "UPDATE Patient_Questionnaire SET Completed = 1 WHERE Q_id = ? AND P_NHS_Number = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, questionnaire.getId());
            statement.setString(2, patient.getNhsNumber());
            int success = statement.executeUpdate();
            System.out.println("Rows changed: " + success);
            if(success == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean setPatientQuestionnaireAsNotCompleted(QuestionnairePointer questionnaire, Patient patient) throws SQLException
    {
        if(isPatientAssignedToQuestionnaire(patient, questionnaire))
        {
            String query = "UPDATE Patient_Questionnaire SET Completed = 0 WHERE Q_id = ? AND P_NHS_Number = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, questionnaire.getId());
            statement.setString(2, patient.getNhsNumber());
            int success = statement.executeUpdate();
            if(success == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /************************************************************
     PATIENT LOG METHODS
     *************************************************************/

    public ArrayList<PatientLog> getAllPatientLogs() throws SQLException
    {
        ArrayList<PatientLog> patientLogs = new ArrayList<PatientLog>();
        Statement statement = createStatement();
        String query = "SELECT * FROM Patient_Log";
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
            PatientLog patientLog = new PatientLog(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5),result.getString(6),
                    result.getString(7), result.getString(8), result.getString(9), result.getString(10), result.getString(11),
                    result.getString(12), result.getString(13), result.getString(14), result.getString(15));
            patientLogs.add(patientLog);
        }
        return patientLogs;
    }

    public void populatePatientLogsUpdate() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS update_Patient_Log AFTER UPDATE ON Patient\n" +
                "BEGIN\n" +
                "  INSERT INTO Patient_Log  (P_NHS_number_OLD, P_NHS_number_NEW, P_first_name_OLD, P_first_name_NEW,\n" +
                "                            P_middle_name_OLD, P_middle_name_NEW, P_surname_OLD, P_surname_NEW,\n" +
                "                            P_date_of_birth_OLD, P_date_of_birth_NEW, P_postcode_OLD, P_postcode_NEW, SQL_action, Time_enter)\n" +
                "          values (old.P_NHS_number,new.P_NHS_number,old.P_first_name,new.P_first_name,old.P_middle_name,\n" +
                "                  new.P_middle_name,old.P_surname, new.P_surname, old.P_date_of_birth, new.P_date_of_birth,\n" +
                "                  old.P_postcode, new.P_postcode, 'UPDATE', DATETIME('NOW') );\n" +
                "END");
    }

    public void populatePatientLogsInsert() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS insert_Patient_Log AFTER INSERT ON Patient\n" +
                "BEGIN\n" +
                "  INSERT INTO Patient_Log  (P_NHS_number_NEW, P_first_name_NEW,\n" +
                "                            P_middle_name_NEW, P_surname_NEW,\n" +
                "                            P_date_of_birth_NEW, P_postcode_NEW, SQL_action, Time_enter)\n" +
                "          values (new.P_NHS_number,new.P_first_name,\n" +
                "                  new.P_middle_name,new.P_surname, new.P_date_of_birth,\n" +
                "                  new.P_postcode, 'INSERT', DATETIME('NOW') );\n" +
                "END");
    }

    public void populatePatientLogsDelete() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS delete_Patient_Log DELETE ON Patient\n" +
                "BEGIN\n" +
                "  INSERT INTO Patient_Log  (P_NHS_number_OLD, P_first_name_OLD,\n" +
                "                            P_middle_name_OLD, P_surname_OLD,\n" +
                "                            P_date_of_birth_OLD, P_postcode_OLD, SQL_action, Time_enter)\n" +
                "          values (old.P_NHS_number,old.P_first_name,\n" +
                "                  old.P_middle_name,old.P_surname, old.P_date_of_birth,\n" +
                "                  old.P_postcode, 'DELETE', DATETIME('NOW') );\n" +
                "END");
    }



    /************************************************************
     QUESTIONNAIRE LOG METHODS
     *************************************************************/
    public ArrayList<QuestionnaireLog> getAllQuestionnaireLogs() throws SQLException
    {
        ArrayList<QuestionnaireLog> questionnaireLogs = new ArrayList<QuestionnaireLog>();
        Statement statement = createStatement();
        String query = "SELECT * FROM Questionnaire_Log";
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
            QuestionnaireLog questionnaireLog = new QuestionnaireLog(result.getInt(1), result.getInt(2), result.getInt(3),
                    result.getString(4),result.getString(5),result.getString(6),
                    result.getString(7), result.getString(8), result.getString(9));
            questionnaireLogs.add(questionnaireLog);
        }
        return questionnaireLogs;
    }

    public void populateQuestionnaireLogsUpdate() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS update_Questionnaire_Log AFTER UPDATE  ON Questionnaire\n" +
                "BEGIN\n" +
                "  INSERT INTO Questionnaire_Log  (Q_id_OLD, Q_id_NEW, Q_title_OLD, Q_title_NEW,\n" +
                "                            Q_state_OLD, Q_state_NEW, SQL_action, Time_enter)\n" +
                "      values (old.Q_id,new.Q_id,old.Q_title,new.Q_title,old.Q_state,\n" +
                "                  new.Q_state, 'UPDATE', DATETIME('NOW') );\n" +
                "END");
    }

    public void populateQuestionnaireLogsInsert() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS insert_Questionnaire_Log AFTER INSERT  ON Questionnaire\n" +
                "BEGIN\n" +
                "  INSERT INTO Questionnaire_Log  (Q_id_NEW, Q_title_NEW,\n" +
                "                            Q_state_NEW, SQL_action, Time_enter)\n" +
                "      values (new.Q_id,new.Q_title, new.Q_state, 'INSERT', DATETIME('NOW') );\n" +
                "END");
    }

    public void populateQuestionnaireLogsDelete() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS delete_Questionnaire_Log DELETE  ON Questionnaire\n" +
                "BEGIN\n" +
                "  INSERT INTO Questionnaire_Log  (Q_id_OLD, Q_title_OLD, Q_state_OLD, SQL_action, Time_enter)\n" +
                "      values (old.Q_id, old.Q_title, old.Q_state, 'DELETE', DATETIME('NOW') );\n" +
                "END");
    }

    /************************************************************
     PATIENT_QUESTIONNAIRE LOG METHODS
     *************************************************************/

    public ArrayList<PatientQuestionnaireLog> getAllPatientQuestionnaireLogs() throws SQLException
    {
        ArrayList<PatientQuestionnaireLog> patientQuestionnaireLogs = new ArrayList<PatientQuestionnaireLog>();
        Statement statement = createStatement();
        String query = "SELECT * FROM Patient_Questionnaire_Log";
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
            PatientQuestionnaireLog patientQuestionnaireLog = new PatientQuestionnaireLog(result.getInt(1), result.getString(2), result.getString(3),
                    result.getString(4),result.getString(5),result.getString(6),
                    result.getString(7), result.getString(8), result.getString(9));
            patientQuestionnaireLogs.add(patientQuestionnaireLog);
        }
        return patientQuestionnaireLogs;
    }

    public void populatePatientQuestionnaireLogsUpdate() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS update_Patient_Questionnaire_Log AFTER UPDATE  ON Patient_Questionnaire\n" +
                "BEGIN\n" +
                "  INSERT INTO Patient_Questionnaire_Log  (P_NHS_number_OLD, P_NHS_number_NEW ,Q_id_OLD, Q_id_NEW,\n" +
                "                            Completed_OLD, Completed_NEW, SQL_action, Time_enter)\n" +
                "      values (old.P_NHS_number, new.P_NHS_number, old.Q_id,new.Q_id,\n" +
                "                  old.Completed, new.Completed, 'UPDATE', DATETIME('NOW') );\n" +
                "END");
    }

    public void populatePatientQuestionnaireLogsInsert() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS insert_Patient_Questionnaire_Log AFTER INSERT  ON Patient_Questionnaire\n" +
                "BEGIN\n" +
                "  INSERT INTO Patient_Questionnaire_Log  (P_NHS_number_NEW, Q_id_NEW, Completed_NEW,\n" +
                "                            SQL_action, Time_enter)\n" +
                "      values (new.P_NHS_number, new.Q_id, new.Completed, 'INSERT', DATETIME('NOW') );\n" +
                "END");
    }

    public void populatePatientQuestionnaireLogsDelete() throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("CREATE TRIGGER IF NOT EXISTS delete_Patient_Questionnaire_Log DELETE  ON Patient_Questionnaire\n" +
                "BEGIN\n" +
                "  INSERT INTO Patient_Questionnaire_Log  (P_NHS_Number_OLD, Q_id_OLD, Completed_OLD, SQL_action, Time_enter)\n" +
                "      values (old.P_NHS_number, old.Q_id, old.Completed, 'DELETE', DATETIME('NOW') );\n" +
                "END");
    }

    /************************************************************
     ADMIN METHODS
     *************************************************************/

    public boolean setAdminPasscode(String passcode) throws SQLException
    {
        String query = "INSERT INTO Admin(A_passcode) VALUES(?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, passcode);

        statement.execute();
        ResultSet keys = statement.getGeneratedKeys();

        if(keys.next())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean clearAdminPasscode() throws SQLException
    {
        String query = "DELETE FROM Admin;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        return true;
    }


    public String getAdminPasscode() throws SQLException
    {
        String query = "SELECT * FROM Admin";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        ResultSet result = statement.getResultSet();
        if(result.next())
        {
            return result.getString(1);
        }
        else
        {
            return null;
        }
    }

    /************************************************************
     PRIVATE METHODS
     *************************************************************/

    private Statement createStatement()
    {
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return statement;
    }
}
