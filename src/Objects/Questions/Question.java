package Objects.Questions;

import com.oracle.javafx.jmx.json.JSONDocument;

/**
 * Created by NiklasBegley on 13/02/2014.
 */
public class Question {

    private int id;
    private String title;
    private boolean required;
    private int type;

    public Question(int id, String title, int type, boolean required)
    {
        this.id = id;
        this.title = title;
        this.type = type;
        this.required = required;
    }
}
