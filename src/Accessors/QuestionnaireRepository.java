package Accessors;

import Helpers.JsonHelper;
import ModelObjects.Questionnaire;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Created by NiklasBegley on 13/02/2014.
 */
public class QuestionnaireRepository {

    private static String questionnaireStoragePath = "data-storage/questionnaires/";
    private static Gson json = JsonHelper.getInstance();

    public static LinkedList<Questionnaire> getQuestionnaires()
    {
        LinkedList<Questionnaire> questionnaires = new LinkedList<Questionnaire>();

        File root = new File(questionnaireStoragePath);
        String[] subItems = root.list();

        for(String item : subItems)
        {
            if (new File(questionnaireStoragePath + item).isDirectory())
            {
                try
                {
                    BufferedReader reader = new BufferedReader(new FileReader(questionnaireStoragePath + item + "/questionnaire.json"));
                    Questionnaire q = json.fromJson(reader, Questionnaire.class);
                    questionnaires.add(q);
                }
                catch (IOException error)
                {
                    // If this is hit, there is corruption in the repository.
                    return null;
                }
            }
        }

        return questionnaires;
    }

    public static Questionnaire getQuestionnaireById(int questionnaireId)
    {
        Path path = Paths.get(questionnaireStoragePath + questionnaireId);
        Questionnaire questionnaire;

        if(Files.exists(path))
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(questionnaireStoragePath + questionnaireId + "/questionnaire.json"));
                questionnaire = json.fromJson(reader, Questionnaire.class);
            }
            catch (IOException error)
            {
                // If this is hit, there is corruption in the repository.
                return null;
            }

            return questionnaire;
        }
        else
        {
            return null;
        }
    }

    public static boolean saveQuestionnaire(Questionnaire questionnaire)
    {
        // Saves the questionnaire or overwrites an existing one with the same id and name
        Path path = Paths.get(questionnaireStoragePath + questionnaire.getId());

        if (Files.notExists(path))
        {
            File directory = new File(path.toString());
            directory.mkdir();
        }

        String raw = json.toJson(questionnaire);

        try
        {
            //write converted json data to a file named "CountryGSON.json"
            FileWriter writer = new FileWriter(path.toString() + "/questionnaire.json");
            writer.write(raw);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean resetRepository(int confirmationCode)
    {
        // USE WITH CAUTION!
        if (confirmationCode == 911)
        {
            File root = new File(questionnaireStoragePath);
            String[] subItems = root.list();

            for(String item : subItems)
            {
                File dir = new File(questionnaireStoragePath + item);
                if(dir.exists())
                {
                    System.out.println("Now deleting " + dir.getPath());
                    if (!dir.delete() && dir.isDirectory())
                    {
                        // Delete all children first
                        for(File file : dir.listFiles())
                        {
                            file.delete();
                        }
                        dir.delete();
                    }
                }
            }
        }

        return true;
    }
}
