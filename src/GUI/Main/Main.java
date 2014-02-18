package GUI.Main;

import Accessors.QuestionnaireRepository;
import Helpers.JsonHelper;
import ModelObjects.Questionnaire;
import ModelObjects.Questions.Question;
import Sockets.SocketServer;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Me on 04/02/2014.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // ****************************************
        // Creating some questionnaires for testing
        Questionnaire questionnaire = new Questionnaire(1, "Test Questionnaire");
        questionnaire.addQuestion(new Question(1, "Yo mama", 1, true));

        Questionnaire questionnaire2 = new Questionnaire(2, "Second Test Questionnaire");
        questionnaire.addQuestion(new Question(1,"Yo mama", 1, true));

        //QuestionnaireRepository.saveQuestionnaire(questionnaire);
        //QuestionnaireRepository.saveQuestionnaire(questionnaire2);
        // ****************************************

        SocketServer server = new SocketServer(4000);
        server.start();

        Parent root = FXMLLoader.load(getClass().getResource("main_scene.fxml"));
        stage.setTitle("Welcome");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}