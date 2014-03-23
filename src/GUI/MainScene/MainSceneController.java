package GUI.MainScene;

import GUI.Welcome.WelcomeSceneController;
import Helpers.GUI.PaneHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 27/02/2014.
 *
 */
public class MainSceneController implements Initializable {

    private Stage stage;

    @FXML private Parent root;

    @FXML private StackPane stackPane;

    @FXML private ImageView mainLogo;
    @FXML private ChoiceBox<Object> viewChooser;

    private final Object[] menuOptions = {
            "Welcome",
            new Separator(),
            "Patients",
            new Separator(),
            "Questionnaire Builder",
            "Questionnaire Deployment",
            "Distribute Questionnaires",
            "View Answers",
            new Separator(),
            "Connect Tablets",
            new Separator(),
            "Change Logs",
            new Separator(),
            "Export Answers"
    };
    private final String[] viewPaths = {
            "/GUI/Welcome/welcome.fxml",
            null, // Separator
            "/GUI/ManagePatients/patientControls.fxml",
            null, // Separator
            "/GUI/QuestionnaireBuilder/questionnaireBuilder.fxml",
            "/GUI/QuestionnaireDeployment/questionnaireDeployment.fxml",
            "/GUI/QuestionnaireDistribute/questionnaireDistribute.fxml",
            "/GUI/ViewAnswers/viewAnswers.fxml",
            null, // Separator
            "/GUI/ConnectTablets/settingControls.fxml",
            null, // Separator
            "/GUI/ChangeLogs/changeLogs.fxml",
            null, // Separator
            "/GUI/ExportAnswer/exportAnswer.fxml"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupMenu();
        setLogo();
        this.viewChooser.getSelectionModel().select(0);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setupMenu() {
        viewChooser.setItems(FXCollections.observableArrayList(menuOptions));
        viewChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                setView(newNumber.intValue());
            }
        });
    }

    public void setView(int viewIndex) {
        stackPane.getChildren().clear();
        String viewPath = viewPaths[viewIndex];
        if (viewPath != null && viewPath.length() > 0) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(viewPath));
                Pane pane = PaneHelper.loadPaneForAnchorParentWithFXMLLoader(fxmlLoader);
                stackPane.getChildren().add(0, pane);
                if (fxmlLoader.getController().getClass() == WelcomeSceneController.class) {
                    WelcomeSceneController welcomeController = fxmlLoader.getController();
                    welcomeController.setStage(stage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLogo(){
        // load the image
        Image image = new Image("/GUI/Theme/MediQ_logo_main.png");
        //could also "file:/Gui/..."
        mainLogo.setImage(image);
       // System.out.println("smooth logo: " +mainLogo.isSmooth());
        //mainLogo.relocate(200,200);
    }

}
