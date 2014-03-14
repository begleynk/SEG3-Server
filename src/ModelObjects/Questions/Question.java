package ModelObjects.Questions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Niklas Begley on 13/02/2014.
 *
 */
public abstract class Question {

    protected String id;
    protected String title;
    protected String description;
    protected boolean required;
    protected HashMap<String, List<Question>> dependentQuestions = new HashMap<>();

    /**
     * Generic Question Constructor.
     *
     * @param id Question's unique ID.
     * @param title Question title.
     * @param description Question description.
     * @param required Flag defining whether the question is required or not.
     */
    public Question(String id, String title, String description, boolean required)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.required = required;
    }

    /**
     * Provides access to the dependentQuestions Map
     *
     * @return dependentQuestions
     */
    public HashMap<String, List<Question>> getDependentQuestionsMap() {
        return dependentQuestions;
    }

    /**
     * Checks whether the question has any dependent questions.
     *
     * @return TRUE if dependent questions exist, FALSE otherwise.
     */
    public boolean hasDependentQuestions() {
        Set<String> keys = dependentQuestions.keySet();
        if (keys.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the List of dependent question for a given condition.
     *
     * @param condition Condition for which dependent questions are
     * desired.
     * @return List of dependent questions based on the given condition.
     */
    public List<Question> getDependentQuestions(String condition)
    {
        if (this.dependentQuestions.get(condition) != null) {
            return this.dependentQuestions.get(condition);
        }
        return new LinkedList<Question>();
    }

    /**
     * Adds dependent question and its condition to
     * a question.
     *
     * @param condition Condition under which the dependent
     * question will be displayed.
     * @param question Dependent question displayed once the
     * condition is met.
     */
    public void addDependentQuestion(String condition, Question question)
    {
        List<Question> list = getDependentQuestions(condition);
        list.add(question);
        dependentQuestions.put(condition, list);
    }

    /**
     * Adds a list of dependent questions and their condition
     * to a question.
     *
     * @param condition Condition under which dependent
     * questions will be displayed.
     * @param questions List of dependent questions displayed once the
     * condition is met.
     */
    public void addDependentQuestions(String condition, List<Question> questions)
    {
        for (int i = 0; i < questions.size(); i++) {
            addDependentQuestion(condition, questions.get(i));
        }
    }

    /**
     * Checks whether the question is required or not.
     *
     * @return TRUE if required, FALSE otherwise.
     */
    public boolean isRequired()
    {
        return this.required;
    }

    /**
     * Returns the question's ID.
     *
     * @return Question's ID.
     */
    public String getID()
    {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Id: " + this.id +
                "  Title: " + this.title +
                "  Description: " + this.description +
                "  isRequired: " + ((this.required) ? "YES" : "NO");
    }
}
