import Accessors.DataLayer;
import Database.DatabaseInitializer;
import Helpers.DataStorageHelper;
import ModelObjects.Questionnaire;
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
        DataLayer.addQuestionnaire(new Questionnaire(10, "Test 1", 2));
        DataLayer.addQuestionnaire(new Questionnaire(11, "Test 2", 2));
        DataLayer.addQuestionnaire(new Questionnaire(12, "Test 3", 2));
        DataLayer.addQuestionnaire(new Questionnaire(13, "Test 4", 2));

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
            stage.setMinWidth(800);
            stage.setMinHeight(600);
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