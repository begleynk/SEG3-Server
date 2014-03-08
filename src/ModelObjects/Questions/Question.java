package ModelObjects.Questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NiklasBegley on 13/02/2014.
 */
public abstract class Question {

    protected long id;
    protected String title;
    protected String description;
    protected boolean required;
    protected HashMap<String, List<Question>> dependentQuestions;

}
