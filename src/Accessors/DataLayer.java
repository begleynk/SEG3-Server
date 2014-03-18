package Accessors;

import Exceptions.NoQuestionnaireException;
import ModelObjects.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Niklas Begley on 03/03/2014.
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
     QUESTIONNAIRE STATE ARRAY
     *************************************************************/

    private static String[] states = {"Draft", "Deployed", "Archived"};

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

    public static Patient updatePatient(Patient patient) throws SQLException
    {
        try
        {
            databaseAccessor.updatePatientRecord(patient);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error updating patient " + patient.getNhsNumber());
            throw e;
        }
        return patient;
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
        ArrayList<Patient> patients;
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

    public static ArrayList<QuestionnairePointer> getQuestionnairePointers() throws SQLException, NoQuestionnaireException
    {
        try
        {
            return databaseAccessor.getAllQuestionnaires();
        }
        catch (SQLException e)
        {
            throw e;
        }
    }

    public static ArrayList<QuestionnairePointer> getQuestionnairePointersForState(int state) throws SQLException, NoQuestionnaireException
    {
        try
        {
            return databaseAccessor.getAllQuestionnairesForState(state);
        }
        catch (SQLException e)
        {
            throw e;
        }
    }

    public static Questionnaire getQuestionnaireWithPointer(QuestionnairePointer pointer) throws NoQuestionnaireException
    {
        try
        {
            Questionnaire questionnaire = questionnaireAccessor.getQuestionnaireById(pointer.getId());
            questionnaire.setState(pointer.getState());
            return questionnaire;
        }
        catch (NoQuestionnaireException e)
        {
            e.printStackTrace();
            System.err.println("FATAL: Error finding questionnaire " + pointer.getId() + ". Your database may be corrupt. Wat. Contact support.");
            throw e;
        }
    }

    public static Questionnaire updateQuestionnare(Questionnaire questionnaire) throws NoQuestionnaireException
    {
        if(questionnaireAccessor.questionnaireExists(questionnaire))
        {
            questionnaireAccessor.saveQuestionnaire(questionnaire);
        }
        else
        {
            System.err.println("Tried to update questionnaire that didn't exist.");
            throw new NoQuestionnaireException();
        }
        return questionnaire;
    }

    public static boolean addQuestionnaire(Questionnaire questionnaire) throws SQLException
    {
        Questionnaire savedQuestionnaire = databaseAccessor.insertQuestionnaireRecord(questionnaire);
        if (questionnaireAccessor.saveQuestionnaire(savedQuestionnaire))
        {
            return true;
        }
        else
        {
            // Roll back database if data repo failed to save the questionnaire to disk
            databaseAccessor.removeQuestionnaire(savedQuestionnaire);
            return false;
        }
    }

    public static boolean removeQuestionnaire(Questionnaire questionnaire) throws SQLException, NoQuestionnaireException
    {
        try
        {
            databaseAccessor.removeQuestionnaire(questionnaire);
            questionnaireAccessor.removeQuestionnaire(questionnaire);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error removing questionnaire " + questionnaire.getId());
            throw e;
        }
        return true;
    }

    public static Questionnaire setQuestionnaireStateToDeployed(Questionnaire questionnaire) throws SQLException, NoQuestionnaireException
    {
        try
        {
            // 1 = Deployed
            databaseAccessor.setQuestionnaireState(questionnaire, states[1]);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (NoQuestionnaireException e)
        {
            e.printStackTrace();
            System.err.println("Tried to deploy unsaved questionnaire.");
            throw e;
        }
        questionnaire.setState(states[1]);
        return questionnaire;
    }

    public static Questionnaire setQuestionnaireStateToArchived(Questionnaire questionnaire) throws SQLException, NoQuestionnaireException
    {
        try
        {
            // 2 = Archived
            databaseAccessor.setQuestionnaireState(questionnaire, states[2]);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (NoQuestionnaireException e)
        {
            e.printStackTrace();
            System.err.println("Tried to archive unsaved questionnaire.");
            throw e;
        }
        questionnaire.setState(states[2]);
        return questionnaire;
    }

    public static QuestionnairePointer setQuestionnairePointerStateToDepolyed(QuestionnairePointer pointer) throws  SQLException, NoQuestionnaireException
    {
        try
        {
            // 1 = Deployed
            databaseAccessor.setQuestionnairePointerState(pointer, states[1]);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (NoQuestionnaireException e)
        {
            e.printStackTrace();
            System.err.println("Tried to deploy unsaved questionnaire.");
            throw e;
        }
        pointer.setState(states[1]);
        return pointer;
    }

    public static QuestionnairePointer setQuestionnairePointerStateToArchived(QuestionnairePointer pointer) throws  SQLException, NoQuestionnaireException
    {
        try
        {
            // 2 = Archived
            databaseAccessor.setQuestionnairePointerState(pointer, states[2]);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (NoQuestionnaireException e)
        {
            e.printStackTrace();
            System.err.println("Tried to deploy unsaved questionnaire.");
            throw e;
        }
        pointer.setState(states[2]);
        return pointer;
    }

    /************************************************************
     QUESTIONNAIRE_PATIENT METHODS
     *************************************************************/

    public static boolean linkPatientAndQuestionnaire(ArrayList<Patient> patients, QuestionnairePointer questionnaire) throws SQLException
    {
        boolean allInsertsSuccessful = true;
        for (Patient patient : patients) {
            allInsertsSuccessful = databaseAccessor.linkPatientAndQuestionnairePointer(patient, questionnaire);
        }
        return allInsertsSuccessful;
    }



    /************************************************************
     PATIENT_LOG METHODS
     *************************************************************/

    public static ArrayList<PatientLog> getAllPatientLogs() throws SQLException
    {
        return databaseAccessor.getAllPatientLogs();
    }

    public static void populatePatientLogsUpdate () throws SQLException
    {
        try {
            databaseAccessor.populatePatientLogsUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error on update logs in Patient Log");
            throw e;
        }
    }
    public static void populatePatientLogsInsert () throws SQLException
    {
        try {
            databaseAccessor.populatePatientLogsInsert();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error on insert logs in Patient Log");
            throw e;
        }
    }
    public static void populatePatientLogsDelete () throws SQLException
    {
        try {
            databaseAccessor.populatePatientLogsDelete();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error on delete logs in Patient Log");
            throw e;
        }
    }

    /************************************************************
     QUESTIONNAIRE_LOG METHODS
     *************************************************************/

    public static ArrayList<QuestionnaireLog> getAllQuestionnaireLogs() throws SQLException
    {
        return databaseAccessor.getAllQuestionnaireLogs();
    }

    public static void populateQuestionnaireLogsUpdate () throws SQLException
    {
        try {
            databaseAccessor.populateQuestionnaireLogsUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error on update logs in Questionnaire Log");
            throw e;
        }
    }
    public static void populateQuestionnaireLogsInsert () throws SQLException
    {
        try {
            databaseAccessor.populateQuestionnaireLogsInsert();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error on insert logs in Questionnaire Log");
            throw e;
        }
    }
    public static void populateQuestionnaireLogsDelete () throws SQLException
    {
        try {
            databaseAccessor.populateQuestionnaireLogsDelete();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error on delete logs in Questionnaire Log");
            throw e;
        }
    }

    /************************************************************
     ADMIN METHODS
     *************************************************************/

//    TODO: TO BE IMPLEMENTED

}
