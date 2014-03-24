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

    protected String condition = "";

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
        if (hasDependantQuestions()) {
            setDependentQuestionsRequired(this, question.required);
        }
    }

    public void setDependentQuestionsRequired(Question question, boolean required) {
        for (String key : question.dependentQuestions.keySet()) {
            List<Question> questions = question.dependentQuestions.get(key);
            for (Question subQuestion : questions) {
                subQuestion.setRequired(required);
                if (subQuestion.hasDependantQuestions()) {
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
    public HashMap<String, List<Question>> getDependantQuestionsMap() {
        return this.dependentQuestions;
    }

    /**
     * Checks whether the question has any dependent questions.
     *
     * @return TRUE if dependent questions exist, FALSE otherwise.
     */
    public boolean hasDependantQuestions() {
        Set<String> keys = this.dependentQuestions.keySet();
        return (keys.size() > 0);
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
        return new LinkedList<>();
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
        question.setRequired(this.required);
        if (hasDependantQuestions()) {
            setDependentQuestionsRequired(question, this.required);
        }
        dependentQuestions.put(condition, list);
    }

    public void removeDependantQuestion(Question dependentQuestion) {
        for (String key : getDependantQuestionsMap().keySet()) {
            List<Question> questionList = getDependantQuestionsMap().get(key);
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
        for (Question question : questions) {
            addDependentQuestion(condition, question);
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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
        // Types: 0 = scale, 1 = choose many, 2 = yes/no, 3 = text, 4 = choose one, 5 = rank

        switch (className) {
            case "RangeQuestion":
                return 0;
            case "SelectManyQuestion":
                return 1;
            case "YesNoQuestion":
                return 2;
            case "TextQuestion":
                return 3;
            case "SelectOneQuestion":
                return 4;
            case "RankQuestion":
                return 5;
            default:
                return -1;
        }
    }
}
