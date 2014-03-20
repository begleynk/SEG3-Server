package Helpers;

import java.security.MessageDigest;

/**
 * Created by NiklasBegley on 20/03/2014.
 */
public class PasswordEncryptor
{
    private static final String salt = "2834hk2j3nbdj1bdj21";

    public static String generateSHA256(String password)
    {
        try
        {
            password = password + salt;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            return new String(hash, "UTF-8");
        }
        catch (Exception e)
        {
            System.err.println("Error hashing password.");
            return null;
        }
    }
}
