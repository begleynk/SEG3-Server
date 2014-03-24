package ModelObjects.Answers;

import ModelObjects.AnswerSet;
import ModelObjects.Questionnaire;
import ModelObjects.Questions.Question;
import com.sun.corba.se.spi.orbutil.proxy.LinkedInvocationHandler;
import com.sun.deploy.util.StringUtils;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by NiklasBegley on 24/03/2014.
 */

public class QuestionAnswerTableColumn
{
    private String questionTitle;
    private String answers;
    private String required;

    public QuestionAnswerTableColumn(Question q, Answer a)
    {
        this.questionTitle = q.getTitle();
        this.answers = StringUtils.join(a.getAnswers(), " | ");
        if(q.isRequired())
        {
            required = "*";
        }
        else
        {
            required = "";
        }
    }

    public QuestionAnswerTableColumn(Question q)
    {
        this.questionTitle = q.getTitle();
        this.answers = "(No answer)";
    }

    public static ArrayList<QuestionAnswerTableColumn> createListFromQuestionsAndAnswers(Questionnaire q, AnswerSet a)
    {
        ArrayList<QuestionAnswerTableColumn> result = new ArrayList<>();
        result = parseQuestions(q.getQuestions(), a.getAnswers(), result);
        return result;
    }

    private static ArrayList<QuestionAnswerTableColumn> parseQuestions(LinkedList<Question> questions, ArrayList<Answer> answers, ArrayList<QuestionAnswerTableColumn> result)
    {
        for(Question question : questions)
        {
            boolean answerFound = false;
            for(Answer answer : answers)
            {
                if(answer.getID().equals(question.getID()))
                {
                    answerFound = true;
                    result.add(new QuestionAnswerTableColumn(question, answer));
                }
            }
            if(!answerFound && question.isRequired())
            {
                result.add(new QuestionAnswerTableColumn(question));
            }
            if(question.hasDependantQuestions())
            {
                LinkedList<Question> dependentQuestions = new LinkedList<>();
                for(String s : question.getDependantQuestionsMap().keySet())
                {
                    dependentQuestions.addAll(question.getDependentQuestions(s));
                }
                parseQuestions(dependentQuestions, answers, result);
            }
        }
        return result;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getAnswers() {
        return answers;
    }

    public String getRequired() {
        return required;
    }
}