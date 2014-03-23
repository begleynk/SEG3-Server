package ModelObjects;

import ModelObjects.Answers.Answer;
import ModelObjects.Questions.Question;
import com.google.gson.annotations.SerializedName;

import java.util.*;

/**
 * Created by James Bellamy on 08/03/2014.
 * But sadly completely changed by Niklas Begley on 21/03/2014...
 */
public class AnswerSet
{
    @SerializedName(value = "questionnaire_id")
    private int questionnaireID;
    @SerializedName(value = "patient_id")
    private String patientNHS;
    private ArrayList<Answer> answers;

    public AnswerSet(int questionnaireID, String patientNHS, ArrayList<Answer> answers)
    {
        this.questionnaireID = questionnaireID;
        this.patientNHS = patientNHS;
        this.answers = answers;
    }

    public int getQuestionnaireID() {
        return questionnaireID;
    }

    public String getPatientNHS() {
        return patientNHS;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public boolean isComplete(Questionnaire questionnaire)
    {
        LinkedList<Question> questions = questionnaire.getQuestions();

        for(Question q : questions)
        {
            if(q.isRequired())
            {
                boolean answerFound = false;
                for(Answer a : answers)
                {
                    if(q.getID().equals(a.getID()))
                    {
                        answerFound = true;
                    }
                }
                if(answerFound == false)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
