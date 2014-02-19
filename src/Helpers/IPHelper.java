package Helpers;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by NiklasBegley on 19/02/2014.
 */
public class IPHelper {

    public static String getIP()
    {
        String raw = "";
        try
        {
            raw = getHostData();
        }
        catch(UnknownHostException e)
        {
            return e.getMessage();
        }

        // Separate IP address from host name
        return raw.split("/")[1];
    }

    public static String getHostName()
    {
        String raw = "";
        try
        {
            raw = getHostData();
        }
        catch(UnknownHostException e)
        {
            return e.getMessage();
        }

        // Separate host name from IP
        return raw.split("/")[0];
    }

    private static String getHostData() throws UnknownHostException
    {
        String address = "";
        try
        {
            address = InetAddress.getLocalHost().toString();
        }
        catch(UnknownHostException e)
        {
            throw e;
        }

        return address;
    }
}
