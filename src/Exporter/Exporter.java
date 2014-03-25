package Exporter;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.AnswerSet;
import ModelObjects.Answers.Answer;
import ModelObjects.Questionnaire;
import ModelObjects.QuestionnairePointer;
import ModelObjects.Questions.Question;
import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Niklas Begley on 22/03/2014.
 *
 * Creates a CSV file of all the answers of a given questionnaire. Must be supplied a path when called.
 *
 */
public class Exporter
{
    final static ArrayList<String[]> result = new ArrayList<>();

    public static boolean exportQuestionnaireData(Questionnaire inputQuestionnaire, String inputPath) throws NoQuestionnaireException
    {
        final ArrayList<AnswerSet> answerSets;
        final Questionnaire questionnaire = inputQuestionnaire;
        final String path = inputPath;

        try
        {
            // Check that the questionnaire exists
            if(DataLayer.getQuestionnaireByID(questionnaire.getId()) != null)
            {
                answerSets = DataLayer.getAnswerSetsForQuestionnaire(questionnaire);
            }
            else
            {
                return false;
            }


            /**********************************
             * CSV Writer from https://code.google.com/p/opencsv/
             *********************************/
            CSV csv = CSV
                    .separator(',')  // delimiter of fields
                    .quote('"')      // quote character
                    .create();       // new instance is immutable

            csv.write(path, new CSVWriteProc() {
                @Override
                public void process(CSVWriter out)
                {
                    // All rows should be added to this list
                    result.add(getQuestionIDs(questionnaire)); // Add IDs to use as references for other rows
                    result.add(getQuestionTitles(questionnaire));

                    for(AnswerSet set : answerSets)
                    {
                        result.add(getAnswersFromSet(set));
                    }

                    result.remove(0);

                    out.writeAll(result);

                    result.clear();
                }
            });
            return true;
        }
        catch(SQLException e)
        {
            System.err.println("SQL error exporting data.");
            return false;
        }

    }

    public static boolean exportQuestionnaireData(QuestionnairePointer questionnaire, String path) throws NoQuestionnaireException
    {
        Questionnaire q = DataLayer.getQuestionnaireWithPointer(questionnaire);
        return exportQuestionnaireData(q, path);
    }

    private static String[] getAnswersFromSet(AnswerSet answerSet)
    {
        String[] IDs = result.get(0);
        String[] results = new String[IDs.length];

        for(int i = 0; i < IDs.length; i++)
        {
            results[i] = findAnswerFor(IDs[i], answerSet);
        }

        return results;
    }

    private static String findAnswerFor(String ID, AnswerSet answerSet)
    {
        for(Answer a : answerSet.getAnswers())
        {
            if(a.getID().equals(ID))
            {
                return joinAnswers(a);
            }
        }
        return "(No Answer)";
    }

    private static String[] getQuestionTitles(Questionnaire questionnaire)
    {
        String[] IDs = result.get(0);
        String[] titles = new String[IDs.length];

        for(int i = 0; i < IDs.length; i++)
        {
            titles[i] = findTitleFor(IDs[i], questionnaire);
        }

        return titles;
    }

    private static String findTitleFor(String ID, Questionnaire questionnaire)
    {
        for(Question q : questionnaire.getQuestions())
        {
            String title = checkQuestionAndDependentsForID(q, ID);
            if(title != null)
            {
                return title;
            }
        }
        return "*Title not found*";
    }

    private static String checkQuestionAndDependentsForID(Question question, String ID)
    {
        if(question.getID().equals(ID))
        {
            return question.getTitle();
        }
        if(question.hasDependantQuestions())
        {
            for(String key : question.getDependantQuestionsMap().keySet())
            {
                for(Question q : question.getDependentQuestions(key))
                {
                    String title = checkQuestionAndDependentsForID(q, ID);
                    if(title != null)
                    {
                        return title;
                    }
                }
            }
        }
        return null;
    }

    private static String[] getQuestionIDs(Questionnaire questionnaire)
    {
        ArrayList<String> tmpIDs = new ArrayList<>();

        for(Question question : questionnaire.getQuestions())
        {
            addQuestionIDsWithDependents(question, tmpIDs);
        }

        String[] ids = new String[tmpIDs.size()];
        for(int i = 0; i < ids.length; i++)
        {
            ids[i] = tmpIDs.get(i);
        }
        return ids;
    }

    private static void addQuestionIDsWithDependents(Question question, ArrayList<String> titles)
    {
        titles.add(question.getID());
        if(question.hasDependantQuestions())
        {
           for(String key : question.getDependantQuestionsMap().keySet())
           {
               for(Question q : question.getDependentQuestions(key))
               {
                   addQuestionIDsWithDependents(q, titles);
               }
           }
        }
    }

    private static String joinAnswers(Answer answer)
    {
        String answerString = "";
        for(String s : answer.getAnswers())
        {
            if(answerString.length() != 0)
            {
                // Put a delimited between answers if more than 1
                answerString = answerString + " | ";
            }
            answerString = answerString + s;
        }
        return answerString;
    }
}
