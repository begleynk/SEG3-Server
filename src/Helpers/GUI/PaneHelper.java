package Helpers.GUI;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by James Bellamy on 22/03/2014.
 *
 */
public class PaneHelper {

    public static Pane loadPaneForAnchorParentWithFXMLLoader(FXMLLoader fxmlLoader) throws IOException {
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Pane pane = (Pane) fxmlLoader.load();
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        return pane;
    }
}
