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

    private int socketPort = 4000;
    private SocketServer socketServer;
    private Thread socketThread;

    private String appTitle = "MediQ Server";

    @Override
    public void start(Stage stage) throws Exception {

        // Create app directory structure.
        intializeAppDirectories();

//        // FOR TESTING:
//        Questionnaire questionnaire = new Questionnaire(0, "test");
//        questionnaire.loadDummy();
//        Patient patient = DataLayer.getAllPatients().get(0);
//        AnswerSet answerSet = new AnswerSet(questionnaire, patient);
//        System.out.println(answerSet.toString());

        this.socketServer = new SocketServer(socketPort);
        this.socketThread = new Thread(this.socketServer);
        this.socketThread.start();

        try {
            Parent mainScreen = FXMLLoader.load(getClass().getResource("/GUI/MainScene/mainScene.fxml"));
            Scene scene = new Scene(mainScreen, stage.getWidth(), stage.getHeight());
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
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