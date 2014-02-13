package Sockets;

import Accessors.QuestionnaireReader;
import Accessors.QuestionnaireRepository;
import Helpers.JsonHelper;
import com.google.gson.Gson;

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
            return "Bar";
        }
        else if (input.equals("Bar"))
        {
            return "Foo";
        }
        else if (input.equals("Credit"))
        {
            return "Suisse";
        }
        else if (input.equals("Suisse"))
        {
            return "Credit";
        }
        else if (input.matches("(GetQuestionnaireByName:).*"))
        {
            return QuestionnaireReader.getQuestionnaireByName(input.split(": ")[1]);
        }
        else if (input.matches("(FindPatient:).*"))
        {
            return "Method pending";
        }
        else if (input.matches("(GetAllQuestionnairesForPatient:).*"))
        {
            return "Method pending";
        }
        else if (input.matches("(GetQuestionnaireByID:).*"))
        {
            Gson json = JsonHelper.getInstance();
            return json.toJson(QuestionnaireRepository.getQuestionnaires());
        }
        else if (input.equals("Close"))
        {
            return "Close";
        }
        else
        {
            return "WTF?";
        }
    }
}
