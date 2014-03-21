package GUI.ExportAnswer;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import ModelObjects.QuestionnairePointer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Faizan Joya on 20/03/14.
 *
 */
public class exportAnswerController implements Initializable {

    // Left Menu Controls
    @FXML private TextField questionnaireSearchField;
    @FXML private ListView<QuestionnairePointer> questionnairePointerListView;

    private ObservableList<QuestionnairePointer> visibleQuestionnairePointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> offScreenQuestionnairePointers
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Setup the List View behaviours
        setupQuestionnairePointerListView();

        // Fetch the data for the left menu
        fetchExportableQuestionnaires();
    }

    public void setupQuestionnairePointerListView() {
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
        this.questionnairePointerListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<QuestionnairePointer>() {
                    @Override
                    public void changed(ObservableValue<? extends QuestionnairePointer> observableValue, QuestionnairePointer old_pointer, QuestionnairePointer new_pointer) {
                        if (new_pointer != null) {
                            // TODO: A new Questionnaire has been Selected
                        } else {
                            // TODO: No Questionnaire Selected
                        }
                    }
                }
        );

        this.questionnairePointerListView.setItems(visibleQuestionnairePointers);
    }

    // Left Menu Methods

    public void fetchExportableQuestionnaires() {
        try {
            this.offScreenQuestionnairePointers.clear();
            this.visibleQuestionnairePointers.clear();
            this.visibleQuestionnairePointers.addAll(DataLayer.getQuestionnairePointersForState(1));
            this.visibleQuestionnairePointers.addAll(DataLayer.getQuestionnairePointersForState(2));
            searchInputChangedAction();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void searchInputChangedAction() {
        String searchTerm = questionnaireSearchField.getText();
        if (searchTerm == null || searchTerm.equals("") ) {
            visibleQuestionnairePointers.addAll(offScreenQuestionnairePointers);
            offScreenQuestionnairePointers.clear();
        } else {
            fuzzySearchQuestionnairePointerUsingSearchTerm(searchTerm);
        }
    }

    public void fuzzySearchQuestionnairePointerUsingSearchTerm(String searchTerm) {
        searchTerm = searchTerm.trim().toLowerCase();
        offScreenQuestionnairePointers.addAll(visibleQuestionnairePointers);
        visibleQuestionnairePointers.clear();
        ArrayList<QuestionnairePointer> pointers = new ArrayList<>();
        for (QuestionnairePointer pointer : offScreenQuestionnairePointers) {
            if (pointer.getTitle().toLowerCase().startsWith(searchTerm) ||
                    pointer.getTitle().toLowerCase().contains(searchTerm)) {
                pointers.add(pointer);
            }
        }
        for (QuestionnairePointer pointer : pointers) {
            offScreenQuestionnairePointers.remove(pointer);
        }
        visibleQuestionnairePointers.setAll(pointers);
    }

    public void questionnaireListViewSelectNone() {
        this.questionnairePointerListView.getSelectionModel().clearSelection();
        this.questionnaireSearchField.requestFocus();
    }


}
