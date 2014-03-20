package GUI.Welcome;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan Joya on 05/03/14.
 * 
 */
public class welcomeController implements Initializable {

    @FXML private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        boolean isPasscodeSet = false;
//        try {
//            isPasscodeSet = DataLayer.isAdminPasscodeSet();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (!isPasscodeSet) {
//            while (!isPasscodeSet) {
//                String input = Dialogs.showInputDialog((Stage) root.getScene().getWindow(), "Please set the Admin passcode:", "The Admin passcode needs to be set for the App", "");
//                try {
//                    DataLayer.setAdminPasscode(input);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                Dialogs.showInformationDialog((Stage) root.getScene().getWindow(), "Please choose a different passcode to the one you are currently using.");
//            }
//        }
    }

}
