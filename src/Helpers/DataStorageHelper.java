package Helpers;

import java.io.File;

/**
 * Created by Niklas Begley on 03/03/2014.
 *
 */
public class DataStorageHelper
{
    public static void createDirectory()
    {
        File dir = new File(OSHelper.getStoragePath());

        if(!dir.exists())
        {
            if(dir.mkdirs())
            {
                System.out.println("Created app directory");
            }
            else
            {
                System.err.println("Error creating app directory.");
            }
        }
        else
        {
            System.out.println("App directory exists - continuing setup.");
        }
    }
}
