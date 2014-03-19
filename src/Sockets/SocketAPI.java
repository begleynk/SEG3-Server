package Sockets;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import Helpers.JsonHelper;
import ModelObjects.Patient;
import ModelObjects.Questionnaire;
import ModelObjects.QuestionnairePointer;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Niklas Begley on 10/02/2014.
 *
 * A static class that will define the API endpoints for the sockets
 *
 */
public class SocketAPI {

    public static String getResponseFor(String encoded)
    {
        System.out.println(encoded);
        String input = Encryptor.decrypt(encoded);
        System.out.println(input);

        /**
         *  API behaviour is defined here
         *
         *  E.g Getting a list of patients could be something like:
         *
         *  else if input.matches("GetPatientList")
         *  {
         *      return PatientRepository.getAllPatients();
         *  }
         */
        if (input.equals("Foo"))
        {
            /****************************************
             SANITY CHECK
             *****************************************/

            return Encryptor.encryptAndFormat("Bar\n");
        }
        else if (input.matches("(FindPatient:).*"))
        {
            /****************************************
             FIND PATIENT BY NHS NUMBER
             *****************************************/

            Patient patient;
            try
            {
                patient = DataLayer.getPatientByNSHNUmber(input.split(": ")[1]);
                if (patient != null)
                {
                    Gson json = JsonHelper.getInstance();
                    return Encryptor.encryptAndFormat(json.toJson(patient));
                }
                else
                {
                    return Encryptor.encryptAndFormat("{'error_code': 666 }");
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return Encryptor.encryptAndFormat("{'error_code': 1337 }");
            }
        }
        else if (input.matches("(GetAllQuestionnairesForPatient:).*"))
        {
            /****************************************
             GET QUESTIONNAIRE FOR PATIENT
             *****************************************/

            try
            {
                Patient patient = DataLayer.getPatientByNSHNUmber(input.split(": ")[1]);
                if(patient != null)
                {
                    ArrayList<QuestionnairePointer> questionnaires = DataLayer.getQuestionnairePointersForPatient(patient);
                    Gson json = JsonHelper.getInstance();
                    return Encryptor.encryptAndFormat(json.toJson(questionnaires));
                }
                else
                {
                    return Encryptor.encryptAndFormat("{'error_code': 666 }");
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return Encryptor.encryptAndFormat("{'error_code': 1337 }");
            }
        }
        else if (input.matches("(GetQuestionnaireByID:).*"))
        {
            /****************************************
             GET QUESTIONNAIRE BY ID
             *****************************************/
            try
            {
                Questionnaire questionnaire = DataLayer.getQuestionnaireByID(Integer.parseInt(input.split(": ")[1]));
                if(questionnaire == null)
                {
                    // Can this be ever hit?
                    return Encryptor.encryptAndFormat("{'error_code': 404 }");
                }
                else
                {
                    Gson json = JsonHelper.getInstance();
                    return Encryptor.encryptAndFormat(json.toJson(questionnaire));
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return Encryptor.encryptAndFormat("{'error_code': 1337 }");
            }
            catch (NoQuestionnaireException e)
            {
                return Encryptor.encryptAndFormat("{'error_code': 404 }");
            }
        }
        else if (input.equals("Close"))
        {
            /****************************************
             * CLOSE CONNECTION
             * Do not encrypt the kill signal!
             *****************************************/
        
            return "Close";
        }
        else
        {
            return Encryptor.encryptAndFormat("WTF?");
        }
    }

}
