package ModelObjects;

import ModelObjects.Answers.Answer;
import ModelObjects.Answers.Types.MultipleAnswer;
import ModelObjects.Answers.Types.SingleAnswer;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.RankQuestion;
import ModelObjects.Questions.Types.YesNoQuestion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class AnswerSet {

    private long questionnaireID;
    private String patientNHSNumber;
    private boolean isCompleted;
    private ArrayList<Answer> answerSet;

    public AnswerSet(Questionnaire questionnaire, Patient patient) {
        isCompleted = false;
        this.patientNHSNumber = patient.getNhsNumber();
        this.questionnaireID = questionnaire.getId();
        this.answerSet = new ArrayList<Answer>();
        // Creating AnswerSet by Mapping Questionnaire structure
        for (Question rootQuestion : questionnaire.getQuestions()) {
            Answer answer = null;
            if (rootQuestion.getClass() == RankQuestion.class || rootQuestion.getClass() == YesNoQuestion.class) {
                answer = new MultipleAnswer();
            } else {
                answer = new SingleAnswer();
            }
            if (rootQuestion.hasDependantQuestions()) {
                generateDependentAnswers(answer, rootQuestion);
            }
            answer.setAnswerID(rootQuestion.getID());
            answer.setRequired(rootQuestion.isRequired());
            this.answerSet.add(answer);
        }
    }

    public void generateDependentAnswers (Answer answer, Question question) {
        HashMap<String, List<Question>> conditionToQuestionMap = question.getDependantQuestionsMap();
        Collection<String> conditionKeys = conditionToQuestionMap.keySet();
        for (String key : conditionKeys) {
            List<Question> questionList = conditionToQuestionMap.get(key);
            List<Answer> dependentAnswers = new ArrayList<Answer>();
            for (Question dependentQuestion : questionList) {
                Answer dependentAnswer = null;
                if (dependentQuestion.getClass() == RankQuestion.class || dependentQuestion.getClass() == YesNoQuestion.class) {
                    dependentAnswer = new MultipleAnswer();
                } else {
                    dependentAnswer = new SingleAnswer();
                }
                if (dependentQuestion.hasDependantQuestions()) {
                    generateDependentAnswers(dependentAnswer, dependentQuestion);
                }
                dependentAnswer.setAnswerID(dependentQuestion.getID());
                dependentAnswer.setRequired(dependentQuestion.isRequired());
                dependentAnswers.add(dependentAnswer);
            }
            answer.addDependentAnswers(key, dependentAnswers);
        }
    }

    @Override
    public String toString() {
        String string =
                "id: " + this.questionnaireID +
                "  nhs: " + this.patientNHSNumber +
                "  isCompleted: " + ((this.isCompleted) ? "YES" : "NO") +
                "\nAnswers:\n";
        for (Answer answer : answerSet) {
            string += answer.toString();
        }
        return string;
    }
}
