package Accessors;

import Exceptions.NoQuestionnaireException;
import ModelObjects.Patient;
import ModelObjects.Questionnaire;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NiklasBegley on 03/03/2014.
 *
 * This is the main communication layer between the data and the interfaces/public APIs.
 * Server GUIs and Socket APIs should only be calling methods from this class.
 */
public class DataLayer
{

    /************************************************************
     ACCESSOR OBJECTS
     *************************************************************/

    // Wraps data repository.
    private static QuestionnaireAccessor questionnaireAccessor = new QuestionnaireAccessor();

    // Wraps database.
    private static DatabaseAccessor databaseAccessor = new DatabaseAccessor();

    /************************************************************
     PATIENT METHODS
     *************************************************************/

    public static boolean addPatient(Patient patient) throws SQLException
    {
        try
        {
            databaseAccessor.insertPatientRecord(patient);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error adding patient " + patient.getNhsNumber());
            throw e;
        }
        return true;
    }

    public static boolean removePatient(Patient patient) throws SQLException
    {
        try
        {
            databaseAccessor.removePatient(patient);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error removing patient " + patient.getNhsNumber());
            throw e;
        }
        return true;
    }

    public static Patient getPatientByNSHNUmber(String nhs) throws SQLException
    {
        Patient patient;
        try
        {
            patient = databaseAccessor.getPatientByNHSNumber(nhs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error finding patient with NHS number" + nhs);
            throw e;
        }
        return patient;
    }


    public static ArrayList<Patient> getAllPatients() throws SQLException
    {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        try
        {
            patients = databaseAccessor.getAllPatients();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error getting all patients.");
            throw e;
        }
        return patients;
    }

    /************************************************************
     QUESTIONNAIRE METHODS
     *************************************************************/

    public static ArrayList<Questionnaire> getAllQuestionnaires() throws SQLException, NoQuestionnaireException
    {
        int[] questionnaireIDs;
        ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

        try
        {
            questionnaireIDs = databaseAccessor.getAllQuestionnaires();
        }
        catch (SQLException e)
        {
            throw e;
        }

        for(int i = 0; i < questionnaireIDs.length; i++)
        {
            try
            {
                questionnaires.add(questionnaireAccessor.getQuestionnaireById(questionnaireIDs[i]));
            }
            catch(NoQuestionnaireException e)
            {
                System.err.println("FATAL: Could not find questionnaire with id " + questionnaireIDs[i]);
                throw e;
            }
        }
        return questionnaires;
    }

    public static boolean addQuestionnaire(Questionnaire questionnaire) throws SQLException
    {
        Questionnaire savedQuestionnaire = databaseAccessor.insertQuestionnaireRecord(questionnaire);
        if(questionnaireAccessor.saveQuestionnaire(savedQuestionnaire))
        {
            return true;
        }
        else
        {
            // Roll back database if data repo failed to save the quesionnaire to disk
            databaseAccessor.removeQuestionnaire(savedQuestionnaire);
            return false;
        }
    }

    public static boolean removeQuestionnaire(Questionnaire questionnaire) throws SQLException
    {
        try
        {
            databaseAccessor.removeQuestionnaire(questionnaire);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error removing questionnaire " + questionnaire.getId());
            throw e;
        }
        return true;
    }

//    TODO: TO BE IMPLEMENTED
//    public static Questionnaire getQuestionnaireByID(int ID) throws SQLException
//    {
//
//    }

    /************************************************************
     ADMIN METHODS
     *************************************************************/

//    TODO: TO BE IMPLEMENTED

}
