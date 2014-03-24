import Database.DatabaseInitializer;
import GUI.MainScene.MainSceneController;
import Helpers.DataStorageHelper;
import Helpers.GUI.PaneHelper;
import Sockets.ConnectionHandler;
import Sockets.SocketServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by James Bellamy on 04/02/2014.
 *
 */
public class Main extends Application {

    private SocketServer socketServer;
    private Thread serverThread;

    @Override
    public void start(Stage stage) throws Exception {

        // Create app directory structure.
        intializeAppDirectories();

        // FOR TESTING:
//        Questionnaire questionnaire = DataLayer.getQuestionnaireByID(21);
//        questionnaire.loadDummy();
//        DataLayer.updateQuestionnare(questionnaire);

        // TO Quickly delete questionnaires!!
//        ArrayList<QuestionnairePointer> pointers = DataLayer.getQuestionnairePointers();
//        ArrayList<Questionnaire> questionnaires = new ArrayList<>();
//        for (QuestionnairePointer pointer : pointers) {
//            questionnaires.add(DataLayer.getQuestionnaireByID(pointer.getId()));
//        }
//        for (Questionnaire questionnaire : questionnaires) {
//            DataLayer.removeQuestionnaire(questionnaire);
//        }

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
        this.serverThread = new Thread(this.socketServer, "Socket Server");
        serverThread.start();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/MainScene/mainScene.fxml"));
            Parent mainScene = PaneHelper.loadPaneForAnchorParentWithFXMLLoader(fxmlLoader);
            MainSceneController mainSceneController = fxmlLoader.getController();
            mainSceneController.setStage(stage);
            Scene scene = new Scene(mainScene, stage.getWidth(), stage.getHeight());
            stage.setMinWidth(1000);
            stage.setMinHeight(700);
            stage.setFullScreen(true);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String appTitle = "MediQ Server";
        stage.setTitle(appTitle);
        stage.getIcons().add(new Image("/GUI/Theme/MediQ_logo_1.png"));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        socketServer.stopListening();
        serverThread.interrupt();
        ConnectionHandler.closeAllConnections();
        super.stop();
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