package ModelObjects.Questions.Types;

import ModelObjects.Questions.Question;

/**
 * Created by James Bellamy on 08/03/2014.
 *
 */
public class RangeQuestion extends Question {

    /**
     * Lower and upper bounds of the range.
     */
    private int lowerBound, upperBound;


    /**
     * Generic Question Constructor.
     *
     * @param id          Question's unique ID.
     * @param title       Question title.
     * @param description Question description.
     * @param required    Flag defining whether the question is required or not.
     * @param lowerBound  Minimum value accepted for answer range
     * @param upperBound  Maximum value accepted for answer range
     */
    public RangeQuestion(String id, String title, String description, boolean required, int lowerBound, int upperBound) {
        super(id, title, description, required);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public void updateContents(Question question) {
        super.updateContents(question);
        RangeQuestion rangeQuestion = (RangeQuestion) question;
        this.lowerBound = rangeQuestion.lowerBound;
        this.upperBound = rangeQuestion.upperBound;
    }

    public int getLowerBound () {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    @Override
    public String toString() {
        String string = super.toString();
        string += "  LowerBound: " + getLowerBound();
        string += "  UpperBound: " + getUpperBound();
        return string;
    }
}
