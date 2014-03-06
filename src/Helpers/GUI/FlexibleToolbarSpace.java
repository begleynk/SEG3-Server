package Helpers.GUI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Created by Me on 06/03/2014.
 */
public class FlexibleToolbarSpace extends Region {

    public FlexibleToolbarSpace() {
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
