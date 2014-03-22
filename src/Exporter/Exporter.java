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
import org.omg.DynamicAny._DynSequenceStub;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NiklasBegley on 22/03/2014.
 *
 * Creates a CSV file of all the answers of a given questionnaire. Must be supplied a path when called.
 */
public class Exporter
{

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
                public void process(CSVWriter out) {

                    String[] questionTitles = getQuestionTitles(questionnaire);
                    out.writeNext(questionTitles);
                    for(AnswerSet a : answerSets)
                    {
                        out.writeNext(getAnswers(a, questionnaire));
                    }
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

    private static String[] getQuestionTitles(Questionnaire questionnaire)
    {
        String[] titles = new String[questionnaire.getQuestions().size()];

        int counter = 0;
        for(Question q : questionnaire.getQuestions())
        {
            titles[counter] = q.getTitle();
        }
        return titles;
    }

    private static String[] getAnswers(AnswerSet answerSet, Questionnaire questionnaire)
    {
        String[] answers = new String[questionnaire.getQuestions().size()];

        int counter = 0;
        for(Question q : questionnaire.getQuestions())
        {
            for(Answer a : answerSet.getAnswers())
            {
                boolean hasAnAnswer = false;

                if(q.getID().equals(a.getID()))
                {
                    answers[counter] = joinAnswers(a);
                    hasAnAnswer = true;
                }
                if(!hasAnAnswer)
                {
                    answers[counter] = " - ";
                }
            }
        }
        return answers;
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
