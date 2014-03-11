package GUI.QuestionnaireBuilder;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.QuestionnairePointer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 04/03/2014.
 *
 */
public class QuestionnaireBuilderController implements Initializable {

    @FXML private Parent root;

    @FXML private SplitPane editingView;

    @FXML private TextField questionnaireSearchField;
    @FXML private ListView<QuestionnairePointer> questionnairePointerListView;

    @FXML private TextField questionnaireTitleField;
    @FXML private Button deployButton;

    private ObservableList<QuestionnairePointer> allPointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> matchedQuestionnairePointers
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.questionnairePointerListView.setCellFactory(new Callback<ListView<QuestionnairePointer>, ListCell<QuestionnairePointer>>() {
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
        });
        questionnairePointerListView.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<QuestionnairePointer>() {
                @Override
                public void changed(ObservableValue<? extends QuestionnairePointer> observableValue, QuestionnairePointer old_pointer, QuestionnairePointer new_pointer) {
                    System.out.println(new_pointer);
                }
        });

        //this.editingView.setVisible(false);

        fetchDeployedQuestionnaires();
    }

    public void fetchDeployedQuestionnaires() {
        try {
            this.allPointers.setAll(DataLayer.getQuestionnairePointersForState(0));
            searchInputChangedAction();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<QuestionnairePointer> fuzzySearchQuestionnairePointerUsingSearchTerm(String searchTerm) {
        matchedQuestionnairePointers.clear();
        searchTerm = searchTerm.trim().toLowerCase();
        for (QuestionnairePointer pointer : allPointers) {
            if (pointer.getTitle().toLowerCase().startsWith(searchTerm) ||
                    pointer.getTitle().toLowerCase().contains(searchTerm)) {
                matchedQuestionnairePointers.add(pointer);
            }
        }
        return matchedQuestionnairePointers;
    }

    public void searchInputChangedAction() {
        String searchTerm = questionnaireSearchField.getText();
        questionnairePointerListView.getSelectionModel().clearSelection();
        if (searchTerm == null || searchTerm.equals("") ) {
            questionnairePointerListView.setItems(allPointers);
        } else {
            questionnairePointerListView.setItems(fuzzySearchQuestionnairePointerUsingSearchTerm(searchTerm));
        }
    }

    public void setupViewForBuildingNewQuestionnaireAction(Event event) {
        //this.editingView.setVisible(true);
    }

}
