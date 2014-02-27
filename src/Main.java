import Accessors.DatabaseAccessor;
import Database.DatabaseInitializer;
import GUI.MainScene.MainScene;
import ModelObjects.Questionnaire;
import ModelObjects.Questions.Question;
import Sockets.SocketServer;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by James Bellamy on 04/02/2014.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //QuestionnaireAccessor.saveQuestionnaire(questionnaire);
        //QuestionnaireAccessor.saveQuestionnaire(questionnaire2);
        // ****************************************

        new Thread(new SocketServer(4000)).start();

        MainScene scene = new MainScene();
        scene.show();
    }

    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        Questionnaire questionnaire = new Questionnaire(1, "Test Questionnaire");
        questionnaire.addQuestion(new Question(1, "Yo mama", 1, true));

        DatabaseAccessor foo = new DatabaseAccessor();
        System.out.println(foo.insertQuestionnaireRecord(questionnaire));

        launch(args);
    }
}