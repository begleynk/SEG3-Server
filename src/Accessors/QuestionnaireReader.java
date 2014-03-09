package Accessors;

import java.io.*;

/**
 * Created by Niklas Begley on 10/02/2014.
 *
 * THIS WILL BE REMOVED
 */
public class QuestionnaireReader {


    public static String getQuestionnaireByName(String name)
    {
        String returnValue = "";

        try(BufferedReader reader = new BufferedReader(new FileReader("local/" + name + ".json")))
        {
            StringBuilder builder = new StringBuilder();

            String line = reader.readLine();

            while (line != null)
            {
                builder.append(line);
                builder.append(System.lineSeparator());

                line = reader.readLine();
            }

            returnValue = builder.toString();
        }
        catch (FileNotFoundException e)
        {
            returnValue = "Could not find questionnaire!";
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return returnValue;
    }
}
