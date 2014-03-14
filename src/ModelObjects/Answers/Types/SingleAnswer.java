package ModelObjects.Answers.Types;

import ModelObjects.Answers.Answer;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class SingleAnswer extends Answer {

    /**
     * Currently set answer.
     */
    private Object answer;

    /**
     * @see ModelObjects.Answers.Answer
     */
    @Override
    protected boolean isAnswered()
    {
        if (required || answer.equals(null)) {
            return false;
        }
        return true;
    }

    @Override
    protected void setStringAnswer() {
        answerContent = answer.toString();
    }
}
