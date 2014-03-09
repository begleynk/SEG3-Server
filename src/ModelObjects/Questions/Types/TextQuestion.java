package ModelObjects.Questions.Types;

import ModelObjects.Questions.Question;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class TextQuestion extends Question {

    /**
     * TextQuestion Constructor.
     *
     * @param id          Question's unique ID.
     * @param title       Question title.
     * @param description Question description.
     * @param required    Flag defining whether the question is required or not.
     */
    public TextQuestion(String id, String title, String description, boolean required) {
        super(id, title, description, required);
    }
}
