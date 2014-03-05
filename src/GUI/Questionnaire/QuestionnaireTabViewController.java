package GUI.Questionnaire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Me on 04/03/2014.
 */
public class QuestionnaireTabViewController implements Initializable {

    @FXML private Tab draftsTab, deployedTab, archivingTab;
    private Tab[] tabs = {draftsTab, deployedTab, archivingTab};

    // File Paths to tab content views
    private String[] views = {null, null, "/GUI/Questionnaire/Archive/questionnaireTabArchive.fxml"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTabViews();
    }

    /**
     * Sets the content pane for each tab
     */
    public void setupTabViews() {
        setTabContent(draftsTab, 0);
        setTabContent(deployedTab, 1);
        setTabContent(archivingTab, 2);
    }

    /**
     * Generates the root AnchorPane pane from the fxml file path and sets it as the content pane of the tab
     * @param aTab is the tab whose content will be set
     * @param viewIndex is the index of the file path we want to get the fxml from
     */
    public void setTabContent(Tab aTab, int viewIndex) {
        if (views[viewIndex] != null) {
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource(views[viewIndex]));
                AnchorPane.setTopAnchor(pane, 0.0);
                AnchorPane.setBottomAnchor(pane, 0.0);
                AnchorPane.setRightAnchor(pane, 0.0);
                AnchorPane.setLeftAnchor(pane, 0.0);
                aTab.setContent(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
