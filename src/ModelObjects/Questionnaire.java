package ModelObjects;

import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Niklas Begley on 13/02/2014.
 *
 */
public class Questionnaire
{
    private int id;
    private String title;
    private LinkedList<Question> questions;

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

    public LinkedList<Question> getQuestions()
    {
        return this.questions;
    }

    public void addQuestion(Question q)
    {
        this.questions.add(q);
    }

    /**
     * Loads dummy data into the questionnaire for development testing.
     */
    public void loadDummy()
    {
        //list of possible answers
        List<String> a = new LinkedList<String>();
        a.add("awesome");
        a.add("good");
        a.add("can be");
        a.add("nothing special");
        a.add("terrible");

        //add different questions
        questions.add(new TextQuestion("1", "Tell me how you are", "Use keywords or describe how you feel in a sentance", false));

        //add also dependent questions
        YesNoQuestion y = new YesNoQuestion("2", "Are you good?", "Yes or No", true);
        y.addDependentQuestion("Yes", new YesNoQuestion("3", "2.0 Feeling well?", "", true));
        y.addDependentQuestion("Yes", new SelectOneQuestion("4", "2.1 Mood?", "", true, a));
        y.addDependentQuestion("No", new SelectManyQuestion("5", "2.2 Mood? 2", "", false, a));
        questions.add(y);

        //some more questions and dependent questions
        SelectManyQuestion m = new SelectManyQuestion("6", "Pick your mood", "", true, a);
        m.addDependentQuestion("awesome", new YesNoQuestion("7", "3.1 Is it raining?", "", false));

        YesNoQuestion ynq = new YesNoQuestion("8", "3.2 Is it freezing?", "", false);
        ynq.addDependentQuestion("Yes", new YesNoQuestion("9", "3.1 Is it raining?", "", false));
        m.addDependentQuestion("nothing special", ynq);

        questions.add(m);
        questions.add(new SelectOneQuestion("10", "4. What is your current mood?", "", true, a));
        RangeQuestion r = new RangeQuestion("11", "5. How good are you?", "", true, -10, 10);
        r.addDependentQuestion("12", new YesNoQuestion("11", "5.1 Is it snowing?", "", false));
        questions.add(r);

        questions.add(new RankQuestion("13", "Test", "", true, a));
    }

    public void set_id(int id)
    {
        this.id = id;
    }
}
