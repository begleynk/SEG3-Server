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

        fetchDeployedQuestionnaires();
    }

    public void fetchDeployedQuestionnaires() {
        try {
            this.deployedQuestionnaires.setAll(DataLayer.getQuestionnairePointers());
            updateListViews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void updateListViews() {
        updateDeployedListView();
    }

    public void updateDeployedListView() {
        this.deployedListView.getSelectionModel().clearSelection();
        this.deployedListView.setItems(deployedQuestionnaires);
    }

    public void updateArchivedListView() {

    }
}
