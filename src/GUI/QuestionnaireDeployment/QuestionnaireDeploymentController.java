package GUI.QuestionnaireDeployment;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.QuestionnairePointer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.beans.EventHandler;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 10/03/2014.
 *
 */
public class QuestionnaireDeploymentController implements Initializable {

    @FXML private ListView<QuestionnairePointer> deployedListView;
    @FXML private ListView<QuestionnairePointer> archivedListView;
    @FXML private ListView<QuestionnairePointer> draftListView;

    private ObservableList<QuestionnairePointer> deployedQuestionnaires
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> archivedQuestionnaires
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> draftQuestionnaires
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupListViews();

        fetchDeployedQuestionnaires();
        fetchArchivedQuestionnaires();
        fetchDraftQuestionnaires();
    }

    public void setupListViews() {
        Callback<ListView<QuestionnairePointer>, ListCell<QuestionnairePointer>> callback = new Callback<ListView<QuestionnairePointer>, ListCell<QuestionnairePointer>>() {
            @Override
            public ListCell<QuestionnairePointer> call(ListView<QuestionnairePointer> p) {
                return new ListCell<QuestionnairePointer>() {
                    @Override
                    protected void updateItem(QuestionnairePointer pointer, boolean aBool) {
                        super.updateItem(pointer, aBool);
                        if (pointer != null) {
                            setText(" " + pointer.getTitle());
                        }
                    }
                };
            }
        };

        this.deployedListView.setCellFactory(callback);
        this.archivedListView.setCellFactory(callback);
        this.draftListView.setCellFactory(callback);

        this.deployedListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.archivedListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.draftListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        this.deployedListView.setItems(deployedQuestionnaires);
        this.archivedListView.setItems(archivedQuestionnaires);
        this.draftListView.setItems(draftQuestionnaires);

        this.draftListView.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                doubleClicked(mouseEvent);
            }
        });
    }

   public void doubleClicked(MouseEvent mouseEvent) {
      if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
          if(mouseEvent.getClickCount() == 2) {

              System.out.println("Double clicked to view the questions on the selected Questionnaire!");
              Group root = new Group();
              Stage stage = new Stage();
              Scene scene = new Scene(root, 500, 500, Color.WHITE);
              stage.setTitle(draftListView.getSelectionModel().getSelectedItem().getTitle());
              stage.setScene(scene);
              stage.show();
          }
      }
   }

    // Fetching Data from Database

    public void fetchDeployedQuestionnaires() {
        try {
            this.deployedQuestionnaires.setAll(DataLayer.getQuestionnairePointersForState(1));
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void fetchArchivedQuestionnaires() {
        try {
            this.archivedQuestionnaires.setAll(DataLayer.getQuestionnairePointersForState(2));
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void fetchDraftQuestionnaires() {
        try {
            this.draftQuestionnaires.setAll(DataLayer.getQuestionnairePointersForState(0));
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    // Switching Button Actions

    public void redeployQuestionnairesAction() {
        ObservableList<QuestionnairePointer> selectedItems = this.archivedListView.getSelectionModel().getSelectedItems();
        for (QuestionnairePointer pointer : selectedItems) {
            try {
                DataLayer.setQuestionnairePointerStateToDepolyed(pointer);
            } catch (SQLException | NoQuestionnaireException e) {
                e.printStackTrace();
            }
        }
        fetchArchivedQuestionnaires();
        fetchDeployedQuestionnaires();
    }

    public void archiveQuestionnairesAction() {
        ObservableList<QuestionnairePointer> selectedItems = this.deployedListView.getSelectionModel().getSelectedItems();
        for (QuestionnairePointer pointer : selectedItems) {
            try {
                DataLayer.setQuestionnairePointerStateToArchived(pointer);
            } catch (SQLException | NoQuestionnaireException e) {
                e.printStackTrace();
            }
        }
        fetchArchivedQuestionnaires();
        fetchDeployedQuestionnaires();
    }

    public void deployQuestionnairesAction() {
        ObservableList<QuestionnairePointer> selectedItems = this.draftListView.getSelectionModel().getSelectedItems();
        for (QuestionnairePointer pointer : selectedItems) {
            try {
                DataLayer.setQuestionnairePointerStateToDepolyed(pointer);
            } catch (SQLException | NoQuestionnaireException e) {
                e.printStackTrace();
            }
        }
        fetchDeployedQuestionnaires();
        fetchDraftQuestionnaires();
    }

}
