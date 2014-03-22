package Sockets;

import Accessors.DataLayer;
import Exceptions.AnswerNotCompleteException;
import Exceptions.NoQuestionnaireException;
import Helpers.JsonHelper;
import ModelObjects.AnswerSet;
import ModelObjects.Answers.Answer;
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
        String input = Encryptor.decrypt(encoded);

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
        else if (input.matches("(CheckPasscode:).*"))
        {
            /****************************************
             CHECK PASSCODE
             *****************************************/
            try
            {
                String passcode = input.split(": ")[1];
                if(DataLayer.checkAdminPasscode(passcode))
                {
                    return Encryptor.encryptAndFormat("{ 'result': true }");
                }
                else
                {
                    return Encryptor.encryptAndFormat("{ 'result': false }");
                }
            }
            catch (SQLException e)
            {
                return Encryptor.encryptAndFormat("{'error_code': 1337 }");
            }
        }
        else if (input.matches("(SendAnswers: ).*"))
        {
            /****************************************
             SEND ANSWERS
             *****************************************/
            try
            {
                String answerJSON = input.replaceFirst("SendAnswers: ", "");
                // Clean up any linebreaks
                answerJSON.replace("\n", "");
                answerJSON.replace("\r", "");

                Gson json = JsonHelper.getInstance();

                AnswerSet answerSet =  json.fromJson(answerJSON, AnswerSet.class);

                // Check the patient exists
                if(DataLayer.getPatientByNSHNUmber(answerSet.getPatientNHS()) != null)
                {
                    Questionnaire questionnaire = DataLayer.getQuestionnaireByID(answerSet.getQuestionnaireID());

                    if(questionnaire.getState() == "Deployed")
                    {
                        DataLayer.saveAnswer(answerSet);
                        return Encryptor.encryptAndFormat("{ 'result': true }");
                    }
                    else
                    {
                        return Encryptor.encryptAndFormat("{'error_code': 457 }");
                    }
                }
                else
                {
                    return Encryptor.encryptAndFormat("{'error_code': 666 }");
                }
            }
            catch (NoQuestionnaireException e)
            {
                return Encryptor.encryptAndFormat("{'error_code': 404 }");
            }
            catch (SQLException e)
            {
                return Encryptor.encryptAndFormat("{'error_code': 1337 }");
            }
            catch (AnswerNotCompleteException e)
            {
                return Encryptor.encryptAndFormat("{'error_code': 456 }");
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
