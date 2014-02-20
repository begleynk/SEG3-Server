package GUI.MainScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Me on 20/02/2014.
 */
public class MainScene extends Stage {

    public MainScene () {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("prototype.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setTitle("Welcome");
        this.setScene(new Scene(root));
    }
}
