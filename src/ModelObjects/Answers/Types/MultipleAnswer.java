package ModelObjects.Answers.Types;

import ModelObjects.Answers.Answer;

import java.util.List;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class MultipleAnswer extends Answer {

    /**
     * Answers for a given question.
     */
    List<Object> answers;

    /**
     * @see ModelObjects.Answers.Answer
	 */
    @Override
    protected boolean isAnswered()
    {
        if (required || answers.isEmpty()) {
            return false;
        }
        return true;
    }
}
