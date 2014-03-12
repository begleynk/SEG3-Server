package GUI.ConnectTablets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan on 05/03/14.
 *
 */
public class SettingControlsController implements Initializable {

    // textfield and listview from GUI
    @FXML private TextField questionSearchField;
    @FXML private ListView questionListView;
    @FXML private ToolBar rightPaneToolBar;
    @FXML private Label ipaddress;
    @FXML private Label port;
    @FXML private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ipaddress.setText("hello");
        //port.setText("the port yo");
    }

    //get ippaddress
    //get port set



}// end class: