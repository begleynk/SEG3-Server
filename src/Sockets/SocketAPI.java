package Sockets;

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
         *  case "GetPatientList":
         *      return PatientRepository.getAllPatients();
         */
        switch (input)
        {
            case "Foo":
                return "Bar";
            case "Close":
                return "Close";
            default:
                return "WTF?";
        }
    }
}
