package Helpers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by NiklasBegley on 08/03/2014.
 */
public class IDHelper {

    public static String generateRandomID() throws Exception
    {
        try
        {
            Long time = System.currentTimeMillis();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(time.toString().getBytes());
            byte[] digest = md.digest();

            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);

            while(hashtext.length() < 32 )
            {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }
        catch(Exception e)
        {
            System.err.println("Error generating random ID.");
            e.printStackTrace();
            throw new Exception("Error generating random ID.");
        }
    }
}

