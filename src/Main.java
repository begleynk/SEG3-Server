import Database.DatabaseInitializer;
import Helpers.DataStorageHelper;
import Sockets.ConnectionHandler;
import Sockets.SocketServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by James Bellamy on 04/02/2014.
 *
 */
public class Main extends Application {

    private SocketServer socketServer;

    @Override
    public void start(Stage stage) throws Exception {

        // Create app directory structure.
        intializeAppDirectories();

        // FOR TESTING:
//        Questionnaire questionnaire = DataLayer.getQuestionnaireByID(3);
//        questionnaire.loadDummy();
//        DataLayer.updateQuestionnare(questionnaire);

        // FOR TESTING:
//        DataLayer.addQuestionnaire(new Questionnaire("Test 1", 1));
//        DataLayer.addQuestionnaire(new Questionnaire("Test 2", 1));
//        DataLayer.addQuestionnaire(new Questionnaire("Test 3", 2));
//        DataLayer.addQuestionnaire(new Questionnaire("Test 4", 2));
//        DataLayer.addQuestionnaire(new Questionnaire("Test 5", 0));

        // FOR TESTING:
//        Questionnaire questionnaire = new Questionnaire(0, "test");
//        questionnaire.loadDummy();
//        Patient patient = DataLayer.getAllPatients().get(0);
//        AnswerSet answerSet = new AnswerSet(questionnaire, patient);
//        System.out.println(answerSet.toString());

        int socketPort = 4000;
        this.socketServer = new SocketServer(socketPort);
        Thread socketThread = new Thread(this.socketServer);
        socketThread.start();

        try {
            Parent mainScreen = FXMLLoader.load(getClass().getResource("/GUI/MainScene/mainScene.fxml"));
            Scene scene = new Scene(mainScreen, stage.getWidth(), stage.getHeight());
            stage.setMinWidth(1000);
            stage.setMinHeight(700);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String appTitle = "MediQ Server";
        stage.setTitle(appTitle);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ConnectionHandler.closeAllConnections();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void intializeAppDirectories()
    {
        DataStorageHelper.createDirectory();
        DatabaseInitializer.initialize();
    }

}