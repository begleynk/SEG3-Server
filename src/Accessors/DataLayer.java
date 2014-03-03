package Accessors;

import Exceptions.NoQuestionnaireException;
import ModelObjects.Patient;
import ModelObjects.Questionnaire;
import ModelObjects.Questions.Question;

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

    private static QuestionnaireAccessor questionnaireAccessor = new QuestionnaireAccessor();
    private static DatabaseAccessor databaseAccessor = new DatabaseAccessor();

    /************************************************************
     PATIENT METHODS
     *************************************************************/

    public static boolean insertPatient(Patient patient)
    {
        try
        {
            databaseAccessor.insertPatientRecord(patient);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Patient> getAllPatients()
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
                System.err.println("Could not find questionnaire with id " + questionnaireIDs[i]);
                throw e;
            }
        }
        return questionnaires;
    }

}
