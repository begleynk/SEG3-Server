package ModelObjects.Questions.Types;

import ModelObjects.Questions.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class YesNoQuestion extends SelectOneQuestion {

    /**
     * List of answer options containing only 'Yes' and 'No' answers.
     */
    private static final List<String> answerOptions = new ArrayList<String>() {{add("Yes"); add("No");}};

    /**
     * YesNoQuestion Constructor.
     * Automatically sets answer options to {Yes / No}.
     *
     * @param id Question's unique ID.
     * @param title Question title.
     * @param description Question description.
     * @param required Defines whether the question is required or not.
     */
    public YesNoQuestion(String id, String title, String description, boolean required)
    {
        super(id, title, description, required, answerOptions);
    }

    @Override
    public void updateContents(Question question) {
        super.updateContents(question);
    }

    @Override
    public String toString() {
        String string = super.toString();
        string += "  choices: ";
        for (String choice : answerOptions) {
            string += choice + ", ";
        }
        return string;
    }
}
