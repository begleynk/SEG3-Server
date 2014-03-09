package ModelObjects;

import ModelObjects.Questions.Question;

/**
 * Created by NiklasBegley on 08/03/2014.
 */
public class QuestionnairePointer {

    private int id;
    private String title;
    private String state;

    public QuestionnairePointer(int id, String title, String state)
    {
        this.id = id;
        this.title = title;
        this.state = state;
    }


    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }


    public String getState()
    {
        return state;
    }
}
