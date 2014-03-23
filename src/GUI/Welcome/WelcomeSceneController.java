package GUI.Welcome;

import Accessors.DataLayer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Faizan Joya on 05/03/14.
 * 
 */
public class WelcomeSceneController implements Initializable {

    private Stage stage;

    @FXML private Parent root;

    private TextField oldPasscodeField = new TextField(),
            newPasscodeField = new TextField(),
            confirmNewPasscodeField = new TextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            if (DataLayer.isPasscodeSetToDefault()) {
                askForNewPasscodeFirstTime(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNewPasscode() {
        askForNewPasscodeFirstTime(false);
    }

    public void askForNewPasscodeFirstTime(boolean firstTime) {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        if (firstTime) {
            grid.add(new Label("New Passcode:"), 0, 0);
            grid.add(newPasscodeField, 1, 0);
            grid.add(new Label("Confirm New Passcode:"), 0, 1);
            grid.add(confirmNewPasscodeField, 1, 1);
        } else {
            grid.add(new Label("Old Passcode:"), 0, 0);
            grid.add(oldPasscodeField, 1, 0);
            grid.add(new Label("New Passcode:"), 0, 1);
            grid.add(newPasscodeField, 1, 1);
            grid.add(new Label("Confirm New Passcode:"), 0, 2);
            grid.add(confirmNewPasscodeField, 1, 2);
        }

        String title;
        String message;

        if (firstTime) {
            title = "The Admin Passcode needs to be initially setup for the App";
            message = "Please set the Admin Passcode [it Cannot be 1234]: ";
        } else {
            title = "Set Admin Passcode";
            message = "Please set the Admin Passcode: ";
        }

        Callback<Void, Void> myCallback = new Callback<Void, Void>() {
            @Override
            public Void call(Void param) {
                return null;
            }
        };
        Dialogs.DialogResponse resp = Dialogs.showCustomDialog(stage,
                grid,
                message,
                title,
                Dialogs.DialogOptions.OK_CANCEL, myCallback);

        if (!resp.equals(Dialogs.DialogResponse.OK)) {
             if (firstTime) {
                Platform.exit();
             }
        } else {
            if (firstTime) {
                updateDefaultPasscode();
            } else {
                updatePasscode();
            }
        }
    }

    public void updateDefaultPasscode() {
        boolean passcodeNotDefault; // false
        String newPasscode = newPasscodeField.getText();
        String confirmNewPasscode = confirmNewPasscodeField.getText();
        try {
            if (newPasscode != null && !newPasscode.equals("")
                    && confirmNewPasscode != null && !confirmNewPasscode.equals("")) {
                if (newPasscode.equals(confirmNewPasscode)) {
                    if (newPasscode.matches("^\\d{4,12}$")) {
                        if (DataLayer.checkAdminPasscode(newPasscode)) {
                            Dialogs.showInformationDialog(stage, "Please choose a different Passcode to the one you are currently using.");
                            passcodeNotDefault = false;
                        } else {
                            try {
                                DataLayer.setAdminPasscode(newPasscode);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            // Should be successful every time
                            passcodeNotDefault = !DataLayer.isPasscodeSetToDefault();
                        }
                    } else {
                        Dialogs.showInformationDialog(stage, "Passcodes must be all numbers, and be between 4 and 12 numbers long.");
                        passcodeNotDefault = false;
                    }
                } else {
                    Dialogs.showInformationDialog(stage, "The confirm new Passcode did not match your new Passcode.");
                    passcodeNotDefault = false;
                }
            } else {
                Dialogs.showInformationDialog(stage, "You did not fill in all of the fields.");
                passcodeNotDefault = false;
            }
            if (!passcodeNotDefault) {
                askForNewPasscodeFirstTime(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePasscode() {
        boolean passcodeSetToNew; // false
        String oldPasscode = oldPasscodeField.getText();
        String newPasscode = newPasscodeField.getText();
        String confirmNewPasscode = confirmNewPasscodeField.getText();
        try {
            if (newPasscode != null && !newPasscode.equals("")
                    && confirmNewPasscode != null && !confirmNewPasscode.equals("")
                    && oldPasscode != null && !oldPasscode.equals("")) {
                if (DataLayer.checkAdminPasscode(oldPasscode)) {
                    if (newPasscode.equals(confirmNewPasscode)) {
                        if (newPasscode.matches("^\\d{4,12}$")) {
                            if (oldPasscode.equals(newPasscode)) {
                                Dialogs.showInformationDialog(stage, "Please choose a different Passcode to the one you are currently using.");
                                passcodeSetToNew = false;
                            } else {
                                try {
                                    DataLayer.setAdminPasscode(newPasscode);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                passcodeSetToNew = true;
                            }
                        } else {
                            Dialogs.showInformationDialog(stage, "The Passcode must be all numbers, and be between 4 and 12 numbers long.");
                            passcodeSetToNew = false;
                        }
                    } else {
                        Dialogs.showInformationDialog(stage, "The confirm new Passcode did not match your new Passcode.");
                        passcodeSetToNew = false;
                    }
                } else {
                    Dialogs.showInformationDialog(stage, "The Old Passcode given is incorrect.");
                    passcodeSetToNew = false;
                }
            } else {
                Dialogs.showInformationDialog(stage, "You did not fill in all of the fields.");
                passcodeSetToNew = false;
            }
            if (!passcodeSetToNew) {
                askForNewPasscodeFirstTime(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
