package GUI.QuestionnaireBuilder.QuestionTemplates;

import ModelObjects.Questions.Question;

/**
 * Created by Faizan Joya on 09/03/14.
 *
 */
public abstract class QuestionTypeController {

    protected Question question;
    protected String questionTypeIdentifier;

    public String getQuestionTypeIdentifier() {
        return questionTypeIdentifier;
    }

    public abstract Question getConstructedQuestion(String Id, boolean required);

    public abstract void clearInputFields();

    public abstract boolean isQuestionDefined();

    public abstract void populateWithExistingQuestion(Question existingQuestion);
}
