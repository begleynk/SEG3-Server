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
            digest.update(password.getBytes("UTF-8"));
            byte[] hash = digest.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< hash.length ;i++)
            {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            return sb.toString();
        }
        catch (Exception e)
        {
            System.err.println("Error hashing password.");
            return null;
        }
    }
}
