package GUI.QuestionnaireArchive;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.QuestionnairePointer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 10/03/2014.
 *
 */
public class QuestionnaireArchiveController implements Initializable {

    @FXML private ListView<QuestionnairePointer> deployedListView;
    @FXML private ListView<QuestionnairePointer> archivedListView;

    private ObservableList<QuestionnairePointer> deployedQuestionnaires
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> archivedQuestionnaires
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Callback<ListView<QuestionnairePointer>, ListCell<QuestionnairePointer>> callback = new Callback<ListView<QuestionnairePointer>, ListCell<QuestionnairePointer>>() {
            @Override
            public ListCell<QuestionnairePointer> call(ListView<QuestionnairePointer> p) {
                return new ListCell<QuestionnairePointer>() {
                    @Override
                    protected void updateItem(QuestionnairePointer pointer, boolean aBool) {
                        super.updateItem(pointer, aBool);
                        if (pointer != null) {
                            setText(pointer.getTitle());
                        }
                    }
                };
            }
        };

        this.deployedListView.setCellFactory(callback);
        this.archivedListView.setCellFactory(callback);

        this.deployedListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.archivedListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        fetchAll();
    }

    public void fetchAll() {
        fetchDeployedQuestionnaires();
        fetchArchivedQuestionnaires();
    }

    // Fetching Data from Database

    public void fetchDeployedQuestionnaires() {
        try {
            this.deployedQuestionnaires.setAll(DataLayer.getQuestionnairePointersForState(1));
            updateDeployedListView();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void fetchArchivedQuestionnaires() {
        try {
            this.archivedQuestionnaires.setAll(DataLayer.getQuestionnairePointersForState(2));
            updateArchivedListView();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    // Updating UI when new Data has been fetched

    public void updateDeployedListView() {
        this.deployedListView.setItems(deployedQuestionnaires);
    }

    public void updateArchivedListView() {
        this.archivedListView.setItems(archivedQuestionnaires);
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
        fetchAll();
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
        fetchAll();
    }
}
