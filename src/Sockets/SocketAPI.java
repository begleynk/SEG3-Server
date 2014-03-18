package Sockets;

import Accessors.DataLayer;
import Accessors.QuestionnaireReader;
import Helpers.JsonHelper;
import ModelObjects.Patient;
import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.SQLException;

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
        String input = decrypt(encoded);
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
            return encryptAndFormat("Bar\n");
        }
        else if (input.equals("Bar"))
        {
            return encryptAndFormat("Foo\n");
        }
        else if (input.equals("Credit"))
        {
            return encryptAndFormat("Suisse\n");
        }
        else if (input.equals("Suisse"))
        {
            return encryptAndFormat("Credit\n");
        }
        else if (input.matches("(GetQuestionnaireByName:).*"))
        {
            return encryptAndFormat(QuestionnaireReader.getQuestionnaireByName(input.split(": ")[1]));
        }
        else if (input.matches("(FindPatient:).*"))
        {
            /****************************************
             FIND PATIENT BY NHS NUMBER
             *****************************************/

            System.out.println("Finding patient...");

            Patient patient;
            try
            {
                patient = DataLayer.getPatientByNSHNUmber(input.split(": ")[1]);
            }
            catch (SQLException e)
            {
                return encryptAndFormat("{'error_code': 1337 }");
            }
            if (patient == null)
            {
                return encryptAndFormat("{ 'error_code': 666 }");
            }
            Gson json = JsonHelper.getInstance();
            return encryptAndFormat(json.toJson(patient));
        }
        else if (input.matches("(GetAllQuestionnairesForPatient:).*"))
        {
            return encryptAndFormat("Method pending");
        }
        else if (input.matches("(GetQuestionnaireByID:).*"))
        {
            return encryptAndFormat("Method pending");
//            Gson json = JsonHelper.getInstance();
//            return json.toJson(QuestionnaireAccessor.getQuestionnaires());
        }
        else if (input.equals("Close"))
        {
            // Do not encrypt the kill signal!
            return "Close";
        }
        else
        {
            return encryptAndFormat("WTF?");
        }
    }

    /*********************************************
                    ENCRYPTION
     *********************************************/

    private static SecretKeySpec secret;

    private static IvParameterSpec iv;
    private static Cipher decryptor;
    private static Cipher encryptor;

    private static void createSecret()
    {
        try
        {
            char[] password = new char[]{'f','x','5','6','x','o','r'};
            byte[] salt = new byte[]{1,2,3,4,5,6,7,8};

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
            SecretKey tmp = factory.generateSecret(spec);
            secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void createEncryptor()
    {
        try
        {
            encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptor.init(Cipher.ENCRYPT_MODE, secret, iv);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void createIV()
    {
        byte[] ivSpec = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        iv = new IvParameterSpec(ivSpec);
    }

    private static void createDecryptor()
    {
        try
        {
            decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptor.init(Cipher.DECRYPT_MODE, secret, iv);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String decrypt(String message)
    {
        if(secret == null)
        {
            createSecret();
            createIV();
            createEncryptor();
            createDecryptor();
        }
        try
        {
            byte[] encrypted = DatatypeConverter.parseBase64Binary(message);
            byte[] decrypted = decryptor.doFinal(encrypted);
            return new String(decrypted, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private static String encrypt(String message)
    {
        if(secret == null)
        {
            createSecret();
            createIV();
            createEncryptor();
            createDecryptor();
        }
        try
        {
            byte[] encrypted = encryptor.doFinal(message.getBytes("UTF-8"));
            return DatatypeConverter.printBase64Binary(encrypted);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private static String encryptAndFormat(String message)
    {
        System.out.println("Encrypting: " + message);
        String response = encrypt(message) + "END";
        System.out.println(response);
        return response;
    }
}
