package Accessors;

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

    public static boolean insertPatient(Patient patient)
    {
        try
        {
            databaseAccessor.insertPatientRecord(patient);
        }
        catch (SQLException e)
        {
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
            System.err.println("Error getting all patients.");
        }
        return patients;
    }
}
