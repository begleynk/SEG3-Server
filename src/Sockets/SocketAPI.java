package Sockets;

import Accessors.DataLayer;
import Accessors.QuestionnaireAccessor;
import Accessors.QuestionnaireReader;
import Helpers.JsonHelper;
import ModelObjects.Patient;
import com.google.gson.Gson;

import java.sql.SQLException;

/**
 * Created by NiklasBegley on 10/02/2014.
 *
 * A static class that will define the API endpoints for the sockets
 */
public class SocketAPI {

    public static String getResponseFor(String input)
    {
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
            return "Bar\n"+ "END";
        }
        else if (input.equals("Bar"))
        {
            return "Foo\n"+ "END";
        }
        else if (input.equals("Credit"))
        {
            return "Suisse\n"+ "END";
        }
        else if (input.equals("Suisse"))
        {
            return "Credit\n"+ "END";
        }
        else if (input.matches("(GetQuestionnaireByName:).*"))
        {
            return QuestionnaireReader.getQuestionnaireByName(input.split(": ")[1]) + "END";
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
            }
            catch (SQLException e)
            {
                return "{'error_code': 1337 }END";
            }
            if (patient == null)
            {
                return "{ 'error_code': 666 }END";
            }
            Gson json = JsonHelper.getInstance();
            return json.toJson(patient) + "END";
        }
        else if (input.matches("(GetAllQuestionnairesForPatient:).*"))
        {
            return "Method pending";
        }
        else if (input.matches("(GetQuestionnaireByID:).*"))
        {
            return "Method pending";
//            Gson json = JsonHelper.getInstance();
//            return json.toJson(QuestionnaireAccessor.getQuestionnaires());
        }
        else if (input.equals("Close"))
        {
            return "Close";
        }
        else
        {
            return "WTF?"+ "END";
        }
    }
}
