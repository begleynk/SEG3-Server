package ModelObjects;

import ModelObjects.Questions.Question;

import java.util.LinkedList;

/**
 * Created by NiklasBegley on 13/02/2014.
 */
public class Questionnaire
{
    private int id;
    private String title;
    private LinkedList<Question> questions;
    private String state;

    public Questionnaire(int id, String title)
    {
        this.id = id;
        this.title = title;

        this.questions = new LinkedList<Question>();
    }

    public int getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getState()
    {
        return this.state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public LinkedList<Question> getQuestions()
    {
        return this.questions;
    }

    public void addQuestion(Question q)
    {
        this.questions.add(q);
    }

    public void set_id(int id)
    {
        this.id = id;
    }
}
