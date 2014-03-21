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
    protected int type;
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
        this.type = getClassID(this.getClass().getSimpleName());
    }

    public void updateContents(Question question) {
        this.title = question.title;
        this.description = question.description;
        this.required = question.required;
        if (hasDependentQuestions()) {
            setDependentQuestionsRequired(this, question.required);
        }
    }

    public void setDependentQuestionsRequired(Question question, boolean required) {
        for (String key : question.dependentQuestions.keySet()) {
            List<Question> questions = question.dependentQuestions.get(key);
            for (Question subQuestion : questions) {
                subQuestion.setRequired(required);
                if (subQuestion.hasDependentQuestions()) {
                    setDependentQuestionsRequired(subQuestion, required);
                }
            }
        }
    }

    /**
     * Provides access to the dependentQuestions Map
     *
     * @return dependentQuestions
     */
    public HashMap<String, List<Question>> getDependentQuestionsMap() {
        return this.dependentQuestions;
    }

    /**
     * Checks whether the question has any dependent questions.
     *
     * @return TRUE if dependent questions exist, FALSE otherwise.
     */
    public boolean hasDependentQuestions() {
        Set<String> keys = this.dependentQuestions.keySet();
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

    public void removeDependentQuestion(Question dependentQuestion) {
        for (String key : getDependentQuestionsMap().keySet()) {
            List<Question> questionList = getDependentQuestionsMap().get(key);
            if (questionList.contains(dependentQuestion)) {
                questionList.remove(dependentQuestion);
            }
        }
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

    public void setRequired(boolean required) {
        this.required = required;
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

    private int getClassID(String className)
    {
        // Types: 0 = scale, 1 = choosemany, 2 = yes/no, 3 = text, 4 = chooseone, 5 = rank

        if (className.equals("RangeQuestion")) {
            return 0;
        } else if (className.equals("SelectManyQuestion")) {
            return 1;
        } else if (className.equals("YesNoQuestion")) {
            return 2;
        } else if (className.equals("TextQuestion")) {
            return 3;
        } else if (className.equals("SelectOneQuestion")) {
            return 4;
        } else if (className.equals("RankQuestion")) {
            return 5;
        } else {
            return -1;
        }
    }
}
