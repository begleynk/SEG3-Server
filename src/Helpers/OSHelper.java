package Helpers;

import javax.swing.*;

/**
 * Created by NiklasBegley on 03/03/2014.
 */
public class OSHelper
{
    public static String getOperatingSystem()
    {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getStoragePath()
    {
        return new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/MediQ/";
    }
}
