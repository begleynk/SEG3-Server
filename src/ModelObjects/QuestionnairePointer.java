package ModelObjects;

/**
 * Created by Niklas Begley on 08/03/2014.
 *
 */
public class QuestionnairePointer {

    private int id;
    private String title;
    private String state;

    private static String[] states = {"Draft", "Deployed", "Archived"};

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

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Id: " + getId() +
                "  Title: " + getTitle() +
                "  State: " + getState();
    }
}
