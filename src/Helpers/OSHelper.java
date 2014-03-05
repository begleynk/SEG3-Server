package Helpers;

/**
 * Created by NiklasBegley on 03/03/2014.
 */
public class OSHelper
{
    private static String osIDMacOSX = "mac os x";
    private static String osIDWindows = ""; // TODO: find string printed when on windows
    private static String osIDLinux = ""; // TODO: find string printed when on linux
    private static String appDataPathComponent = "/MediQ/";

    public static String getOperatingSystem()
    {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getStoragePath()
    {
        String filePath = System.getProperty("user.home");

        if (getOperatingSystem().equals(osIDMacOSX))
        {
            // = /Users/{User}/Documents/MediQ/
            return filePath + "/Documents" + appDataPathComponent;
        }
        else if (getOperatingSystem().equals(osIDWindows))
        {
            return filePath + appDataPathComponent; // TODO: + "/My Documents"???? like it is done in mac (above)
        }
        else if (getOperatingSystem().equals(osIDLinux))
        {
            return filePath + appDataPathComponent; // TODO: find a good place on unix to store app data
        }
        else // god knows what OS this is...
        {
            return filePath + appDataPathComponent;
        }
    }
}
