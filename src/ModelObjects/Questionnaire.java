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
    private String state;
    private LinkedList<Question> questions = new LinkedList<>();

    private static String[] states = {"Draft", "Deployed", "Archived"};

    public Questionnaire(String title)
    {
        this.title = title;
        this.state = states[0];
    }

    public Questionnaire(String title, int state)
    {
        this.title = title;
        this.state = states[state];
    }

    // Getters

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

    // Setters

    public void set_id(int id)
    {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    // JSON Data

    public LinkedList<Question> getQuestions()
    {
        return this.questions;
    }

    public void addQuestion(Question question)
    {
        this.questions.add(question);
    }

    /**
     * Loads dummy data into the questionnaire for development testing.
     */
    public void loadDummy()
    {
        questions.clear();

        //list of possible answers
        List<String> a = new LinkedList<>();
        a.add("awesome");
        a.add("good");
        a.add("can be");
        a.add("nothing special");
        a.add("terrible");

        //add different questions
        questions.add(new TextQuestion("1", "Tell me how you are", "Use keywords or describe how you feel in a sentence", false));

        //add also dependent questions
        YesNoQuestion y = new YesNoQuestion("2", "Are you good?", "Yes or No", true);
        y.addDependentQuestion("Yes", new YesNoQuestion("3", "2.0 Feeling well?", "d", true));
        y.addDependentQuestion("Yes", new SelectOneQuestion("4", "2.1 Mood?", "d", true, a));
        y.addDependentQuestion("No", new SelectManyQuestion("5", "2.2 Mood? 2", "d", false, a));
        questions.add(y);

        //some more questions and dependent questions
        SelectOneQuestion m = new SelectOneQuestion("6", "Pick your mood", "d", true, a);
        m.addDependentQuestion("awesome", new YesNoQuestion("7", "3.1 Is it raining?", "d", false));

        SelectOneQuestion m2 = new SelectOneQuestion("6", "Pick your mood 2", "d", true, a);
        m2.addDependentQuestion("awesome", new YesNoQuestion("7", "3.1 Is it raining? 2", "d", false));

        m.addDependentQuestion(a.get(1), m2);

        YesNoQuestion ynq = new YesNoQuestion("8", "3.2 Is it freezing?", "d", false);
        ynq.addDependentQuestion("Yes", new YesNoQuestion("9", "3.1 Is it raining?", "d", false));
        m.addDependentQuestion("nothing special", ynq);

        questions.add(m);
        questions.add(new SelectOneQuestion("10", "4. What is your current mood?", "d", true, a));
        RangeQuestion r = new RangeQuestion("11", "5. How good are you?", "d", true, -10, 10);
        questions.add(r);

        questions.add(new RankQuestion("12", "Test", "d", true, a));
    }

    @Override
    public String toString() {
        String string =
                "Id: " + getId() +
                "  Title: " + getTitle() +
                "  State: " + getState() +
                "\nQuestions:\n";
        for (Question question : questions) {
            string += question.toString() + "\n";
        }
        return string;
    }
}
