import Accessors.QuestionnaireRepository;
import Helpers.JsonHelper;
import Objects.Questionnaire;
import Objects.Questions.Question;
import Sockets.SocketServer;
import sun.org.mozilla.javascript.internal.json.JsonParser;
import com.google.gson.Gson;

/**
 * Created by Me on 04/02/2014.
 */
public class FooMar {
    public static void main(String[] args) {
        System.out.println("Enesay");

        // ****************************************
        // Creating some questionnaires for testing
        Questionnaire questionnaire = new Questionnaire(1, "Test Questionnaire");
        questionnaire.addQuestion(new Question(1,"Yo mama", 1, true));

        Questionnaire questionnaire2 = new Questionnaire(2, "Second Test Questionnaire");
        questionnaire.addQuestion(new Question(1,"Yo mama", 1, true));

        QuestionnaireRepository.saveQuestionnaire(questionnaire);
        QuestionnaireRepository.saveQuestionnaire(questionnaire2);
        // ****************************************

        SocketServer server = new SocketServer(4000);
        server.start();
    }
}