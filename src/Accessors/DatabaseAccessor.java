package Accessors;

import Exceptions.NoQuestionnaireException;
import Helpers.OSHelper;
import ModelObjects.*;

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
        Statement statement = createStatement();
        String query = "SELECT * FROM Questionnaire;";
        ResultSet result = statement.executeQuery(query);

        ArrayList<QuestionnairePointer> pointerList = new ArrayList<>();

        while(result.next())
        {
            QuestionnairePointer questionnairePointer = new QuestionnairePointer(result.getInt(1), result.getString(2), result.getString(3));
            pointerList.add(questionnairePointer);
        }

        return pointerList;
    }

    public ArrayList<QuestionnairePointer> getAllQuestionnairesForState(int state) throws SQLException
    {
        Statement statement = createStatement();
        String query = "SELECT * FROM Questionnaire WHERE Q_state = '" + states[state] +"';";
        ResultSet result = statement.executeQuery(query);

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
        Statement statement = createStatement();
        statement.execute("INSERT INTO Questionnaire (Q_title, Q_state) VALUES ('"+
                questionnaire.getTitle() +"', '"+
                questionnaire.getState() +"')");
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
        Statement statement = createStatement();
        statement.execute("DELETE FROM Questionnaire WHERE Q_id= " + questionnaire.getId() + ";");
        return true;
    }

    public String getQuestionnaireState(Questionnaire questionnaire) throws SQLException, NoQuestionnaireException
    {
        Statement statement = createStatement();
        String query = "SELECT Q_state FROM Questionnaire WHERE Q_id=" + questionnaire.getId() + ";";
        ResultSet result = statement.executeQuery(query);

        if(result.next())
        {
            return result.getString(1);
        }
        else
        {
            throw new NoQuestionnaireException();
        }
    }

    public boolean setQuestionnaireState(Questionnaire questionnaire, String state) throws SQLException, NoQuestionnaireException
    {
        Statement statement = createStatement();
        String query = "UPDATE Questionnaire SET Q_state = '" + state + "' WHERE Q_id = " + questionnaire.getId() + ";";
        int result = statement.executeUpdate(query);
        return (result > 0);
    }

    public boolean setQuestionnairePointerState(QuestionnairePointer pointer, String state) throws SQLException, NoQuestionnaireException
    {
        Statement statement = createStatement();
        String query = "UPDATE Questionnaire SET Q_state = '" + state + "' WHERE Q_id = " + pointer.getId() + ";";
        int result = statement.executeUpdate(query);
        return (result > 0);
    }


    /************************************************************
     PATIENT METHODS
     *************************************************************/

    public ArrayList<Patient> getAllPatients() throws SQLException
    {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        Statement statement = createStatement();
        String query = "SELECT * FROM Patient";
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
            Patient patient = new Patient(result.getString(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5),result.getString(6));
            patients.add(patient);
        }
        return patients;
    }

    public Patient getPatientByNHSNumber(String nhsNumber) throws SQLException
    {
        Statement statement = createStatement();
        String query = "SELECT * FROM Patient WHERE P_NHS_Number = '" + nhsNumber + "';";

        ResultSet result = statement.executeQuery(query);

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
        String query = "UPDATE Patient SET " +
                "P_first_name = '" + patient.getFirst_name() + "', " +
                "P_middle_name = '" + patient.getMiddle_name() + "', " +
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
        return true;
    }

    /************************************************************
     QUESTIONNAIRE_PATIENT METHODS
     *************************************************************/

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
        if(questionnaire.getId() == 0 || patient.getNhsNumber().equals(""))
        {
            return false;
        }
        Statement statement = createStatement();
        statement.execute("INSERT INTO Patient_Questionnaire (P_NHS_number, Q_id, Completed) VALUES ('" +
                patient.getNhsNumber() + "','" +
                questionnaire.getId() + "', '0');");
        return true;
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
