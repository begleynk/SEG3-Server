package ModelObjects.Questions.Types;

import ModelObjects.Questions.Question;

import java.util.List;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class SelectOneQuestion extends Question {

    /**
     * List of answer options.
     */
    private List<String> answerOptions;

    /**
     * SelectOneQuestion Constructor.
     *
     * @param id            Question's unique ID.
     * @param title         Question title.
     * @param description   Question description.
     * @param required      Flag defining whether the question is required or not.
     * @param answerOptions Available answer options.
     */
    public SelectOneQuestion(String id, String title, String description, boolean required, List<String> answerOptions) {
        super(id, title, description, required);
        this.answerOptions = answerOptions;
    }

}
