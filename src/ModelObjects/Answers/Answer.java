package ModelObjects.Answers;

import com.google.gson.annotations.SerializedName;

import java.util.*;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class Answer {

    @SerializedName(value = "question_id")
    protected String id;
    // Sometimes one answer may include multiple selections. Hence the
    @SerializedName(value = "answer")
    protected ArrayList<String> answers;

    public Answer(String id, ArrayList<String> answers)
    {
        this.id = id;
        this.answers = answers;
    }

    public String getID() { return this.id; };
    public ArrayList<String> getAnswers() { return this.answers; };

}
