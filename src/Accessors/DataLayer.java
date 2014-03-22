package Accessors;

import Exceptions.AnswerNotCompleteException;
import Exceptions.NoQuestionnaireException;
import Helpers.PasswordEncryptor;
import ModelObjects.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static ArrayList<TablePatient> getAllTablePatients() throws SQLException
    {
        ArrayList<TablePatient> patients;
        try
        {
            patients = databaseAccessor.getAllTablePatients();
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

    public static QuestionnairePointer getQuestionnairePointerByID(int ID) throws SQLException
    {
        try
        {
            return databaseAccessor.getQuestionnaireByID(ID);
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

    public static Questionnaire getQuestionnaireByID(int id) throws SQLException, NoQuestionnaireException
    {
        try
        {
            return questionnaireAccessor.getQuestionnaireById(id);
        }
        catch(NoQuestionnaireException e)
        {
            e.printStackTrace();
            System.err.println("Tried to find a questionnaire that does not exist.");
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

    public static QuestionnairePointer setQuestionnairePointerStateToDepolyed(QuestionnairePointer pointer) throws  SQLException, NoQuestionnaireException
    {
        try
        {
            // 1 = Deployed
            if(databaseAccessor.setQuestionnairePointerState(pointer, states[1]));
            {
                Questionnaire questionnaire = questionnaireAccessor.getQuestionnaireById(pointer.getId());
                questionnaire.setState(states[1]);
                questionnaireAccessor.saveQuestionnaire(questionnaire);
            }
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
            if(databaseAccessor.setQuestionnairePointerState(pointer, states[2]));
            {
                Questionnaire questionnaire = questionnaireAccessor.getQuestionnaireById(pointer.getId());
                questionnaire.setState(states[2]);
                questionnaireAccessor.saveQuestionnaire(questionnaire);
            }
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
     ANSWER METHODS
     *************************************************************/

    public static boolean saveAnswer(AnswerSet answers) throws NoQuestionnaireException, AnswerNotCompleteException
    {
        try
        {
            QuestionnairePointer questionnairePointer = getQuestionnairePointerByID(answers.getQuestionnaireID());
            Questionnaire questionnaire = getQuestionnaireByID(answers.getQuestionnaireID());
            Patient patient = getPatientByNSHNUmber(answers.getPatientNHS());

            if(answers.isComplete(questionnaire))
            {
                if(databaseAccessor.setPatientQuestionnaireAsCompleted(questionnairePointer, patient))
                {
                    if(questionnaireAccessor.saveAnswers(answers))
                    {
                        return true;
                    }
                    else
                    {
                        // Roll back database if saving to disk did not succeed.
                        databaseAccessor.setPatientQuestionnaireAsNotCompleted(questionnairePointer, patient);
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                throw new AnswerNotCompleteException();
            }

        }
        catch(NoQuestionnaireException e)
        {
            System.err.print("Tried to save answers to a questionnaire that doesn't exist. Database is corrupted.");
            throw e;
        }
        catch(SQLException e)
        {
            System.err.print("Database error. There may be invalid data in the database.");
            return false;
        }
    }

    /************************************************************
     QUESTIONNAIRE_PATIENT METHODS
     *************************************************************/

    public static ArrayList<QuestionnairePointer> getQuestionnairePointersForPatient(Patient patient) throws SQLException
    {
        try
        {
            return databaseAccessor.getQuestionnairePointersForPatient(patient);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error getting questionnaire pointers for patient " + patient.getNhsNumber());
            throw e;
        }
    }

    public static boolean linkPatientAndQuestionnaire(Patient patient, QuestionnairePointer questionnaire) throws SQLException
    {
        return databaseAccessor.linkPatientAndQuestionnairePointer(patient, questionnaire);
    }

    public static boolean unlinkPatientAndQuestionnaire(Patient patient, QuestionnairePointer questionnaire) throws SQLException
    {
        return databaseAccessor.unlinkPatientAndQuestionnairePointer(patient, questionnaire);
    }

    public static HashMap<String, Boolean> arePatientsAssignedToQuestionnaire(ArrayList<Patient> patients, QuestionnairePointer questionnaire) throws SQLException
    {
        HashMap<String, Boolean> patientIsAssigned = new HashMap<>();
        for (Patient patient : patients) {
            Boolean isAssigned = databaseAccessor.isPatientAssignedToQuestionnaire(patient, questionnaire);
            patientIsAssigned.put(patient.getNhsNumber(), isAssigned);
        }
        return patientIsAssigned;
    }

    public static HashMap<String, Boolean> areTablePatientsAssignedToQuestionnaire(ArrayList<TablePatient> patients, QuestionnairePointer questionnaire) throws SQLException
    {
        HashMap<String, Boolean> patientIsAssigned = new HashMap<>();
        for (TablePatient patient : patients) {
            Boolean isAssigned = databaseAccessor.isPatientAssignedToQuestionnaire(patient, questionnaire);
            patientIsAssigned.put(patient.getNhsNumber(), isAssigned);
        }
        return patientIsAssigned;
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

    public static void setAdminPasscode(String passcode) throws SQLException
    {
        String encrypted = PasswordEncryptor.generateSHA256(passcode);
        databaseAccessor.clearAdminPasscode();
        databaseAccessor.setAdminPasscode(encrypted);
    }

    public static boolean checkAdminPasscode(String passcode) throws SQLException
    {
        String hashed = PasswordEncryptor.generateSHA256(passcode);
        String storedHash = databaseAccessor.getAdminPasscode();

        if(hashed.equals(storedHash))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean doesPasscodeExist() throws SQLException
    {
        try
        {
            if(databaseAccessor.getAdminPasscode() != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

}
