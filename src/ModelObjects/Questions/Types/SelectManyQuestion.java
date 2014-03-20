package ModelObjects.Questions.Types;

import ModelObjects.Questions.Question;

import java.util.List;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class SelectManyQuestion extends Question {

    /**
     * List of possible answers.
     */
    private List<String> answerOptions;

    /**
     * SelectManyQuestion Constructor.
     *
     * @param id            Question's unique ID.
     * @param title         Question title.
     * @param description   Question description.
     * @param required      Flag defining whether the question is required or not.
     * @param answerOptions Available answer options.
     */
    public SelectManyQuestion(String id, String title, String description, boolean required, List<String> answerOptions) {
        super(id, title, description, required);
        this.answerOptions = answerOptions;
    }

    @Override
    public void updateContents(Question question) {
        super.updateContents(question);
        SelectManyQuestion selectManyQuestion = (SelectManyQuestion) question;
        this.answerOptions = selectManyQuestion.answerOptions;
    }

    public List<String> getAnswerOptions() {
        return this.answerOptions;
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
