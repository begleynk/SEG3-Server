package Helpers;

import com.google.gson.*;

/**
 * Created by NiklasBegley on 13/02/2014.
 *
 * A singleton JSON helper class.
 *
 */
public class JsonHelper {

    private static Gson gson = null;

    protected JsonHelper()
    {

    }

    public static Gson getInstance()
    {
        if (gson == null)
        {
            gson = new Gson();
        }

        return gson;
    }
}
