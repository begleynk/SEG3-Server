package ModelObjects.Answers;

import java.util.*;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public abstract class Answer {

    protected String id;
    protected boolean required;
    protected String answerContent;
    protected HashMap<String, List<Answer>> dependentAnswers = new HashMap<>();

    public void setAnswerID (String id) {
        this.id = id;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    /**
     * Provides access to the dependentQuestions Map
     *
     * @return dependentQuestions
     */
    public HashMap<String, List<Answer>> getDependentAnswersMap() {
        return dependentAnswers;
    }

    /**
     * Returns the List of dependent answers for a given condition.
     *
     * @param condition Condition for which dependent answers are desired.
     * @return List of dependent answers based on the given condition.
     */
    public List<Answer> getDependentAnswers(String condition)
    {
        if (this.dependentAnswers.get(condition) != null) {
            return this.dependentAnswers.get(condition);
        }
        return new LinkedList<>();
    }

    /**
     * Adds dependent answer and its condition.
     *
     * @param condition Condition under which the dependent answer will be displayed.
     * @param answer Dependent answer displayed once the condition is met.
     */
    public void addDependentAnswer(String condition, Answer answer)
    {
        List<Answer> list = getDependentAnswers(condition);
        list.add(answer);
        dependentAnswers.put(condition, list);
    }

    /**
     * Adds a list of dependent answers and their condition to an answer.
     *
     * @param condition Condition under which dependent answers will be displayed.
     * @param answers List of dependent answers displayed once the condition is met.
     */
    public void addDependentAnswers(String condition, List<Answer> answers)
    {
        for (int i = 0; i < answers.size(); i++) {
            addDependentAnswer(condition, answers.get(i));
        }
    }

    /**
     * Checks whether the answer has any dependent answers.
     *
     * @return TRUE if dependent questions exist, FALSE otherwise.
     */
    public boolean hasDependentAnswers() {
        Set<String> keys = dependentAnswers.keySet();
        if (keys.size() > 0) {
            return true;
        }
        return false;
    }

    protected abstract boolean isAnswered();

    @Override
    public String toString() {
        String string =
                "id: " + this.id +
                "  isRequired: " + ((this.required) ? "YES" : "NO") +
                "  content: " + this.answerContent +
                "  numberOfDependents: " + this.getDependentAnswersMap().size() + "\n";
        Collection<String> keys = dependentAnswers.keySet();
        for (String key : keys) {
            List<Answer> answers = dependentAnswers.get(key);
            for (Answer answer : answers) {
                string += answer.toString();
            }
        }
        return string;
    }
}
