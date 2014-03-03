package Accessors;

import ModelObjects.Patient;
import ModelObjects.Questionnaire;
import ModelObjects.Questions.Question;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by NiklasBegley on 27/02/2014.
 */
public class DatabaseAccessor {

    private Connection connection;
    private Statement statement;


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
            this.connection = DriverManager.getConnection("jdbc:sqlite:data-storage/database.db");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /************************************************************
            QUESTIONNAIRE METHODS
    *************************************************************/

    public Questionnaire insertQuestionnaireRecord(Questionnaire questionnaire) throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("INSERT INTO Questionnaire (Title) VALUES ('" + questionnaire.getTitle() + "');");
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
        statement.execute("DELETE FROM Questionnaire WHERE Q_id = " + questionnaire.getId() + ";");
        return true;
    }


    /************************************************************
     PATIENT METHODS
     *************************************************************/

    public Patient insertPatientRecord(Patient patient) throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("INSERT INTO Patient (P_NHS_number, P_first_name, P_middle_name, P_surname, P_date_of_birth, P_postcode) VALUES ('" +
                patient.getNhsNumber() + "," +
                patient.getFirst_name() + ", " +
                patient.getMiddle_name() + ", " +
                patient.getSurname() + ", " +
                patient.getDateOfBirth() + ", " +
                patient.getPostcode() + "');");
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

    /*
    CREATE TABLE IF NOT EXISTS 'Patient_Questionnaire' (
    'P_NHS_number' int(10) NOT NULL,
    'Q_id' int(8) NOT NULL,
    'Completed' tinyint(1) NOT NULL,
    FOREIGN KEY ('P_NHS_number') REFERENCES 'Patient'('P_NHS_number') ON UPDATE CASCADE,
    FOREIGN KEY ('Q_id') REFERENCES 'Questionnaire'('Q_id') ON UPDATE CASCADE,
    PRIMARY KEY ('P_NHS_number','Q_id')
    );
    */

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
