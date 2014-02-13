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

        Questionnaire questionnaire = new Questionnaire(1, "Test Questionnaire");
        questionnaire.addQuestion(new Question(1,"Yo mama", 1, true));

        Gson json = JsonHelper.getInstance();
        System.out.println(json.toJson(questionnaire));

        SocketServer server = new SocketServer(4000);
        server.start();
    }
}