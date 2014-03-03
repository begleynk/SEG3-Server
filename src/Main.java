import Database.DatabaseInitializer;
import ModelObjects.Questionnaire;
import ModelObjects.Questions.Question;
import Sockets.SocketServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by James Bellamy on 04/02/2014.
 */
public class Main extends Application {

    private int socketPort = 4001;
    private SocketServer socketServer;
    private Thread socketThread;

    @Override
    public void start(Stage stage) throws Exception {
        // ****************************************
        // Creating some questionnaires for testing
        Questionnaire questionnaire = new Questionnaire(1, "Test Questionnaire");
        questionnaire.addQuestion(new Question(1, "Yo mama", 1, true));

        Questionnaire questionnaire2 = new Questionnaire(2, "Second Test Questionnaire");
        questionnaire2.addQuestion(new Question(1,"Yo mama", 1, true));

        //QuestionnaireRepository.saveQuestionnaire(questionnaire);
        //QuestionnaireRepository.saveQuestionnaire(questionnaire2);
        // ****************************************

        this.socketServer = new SocketServer(socketPort);
        this.socketThread = new Thread(this.socketServer);
        this.socketThread.start();

        try {
            Parent mainScreen = FXMLLoader.load(getClass().getResource("/GUI/MainScene/view/mainScene.fxml"));
            Scene scene = new Scene(mainScreen, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("MediQ Server");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        this.socketServer.endProcesses();
    }

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        launch(args);
    }
}