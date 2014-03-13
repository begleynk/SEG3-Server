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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 04/03/2014.
 * Collaboration with Faizan Joya 11/03/2014
 */
public class QuestionnaireBuilderController implements Initializable {

    @FXML private Parent root;

    @FXML private StackPane stackPane;
    @FXML private SplitPane editingView;

    @FXML private TextField questionnaireSearchField;
    @FXML private ListView<QuestionnairePointer> questionnairePointerListView;

    @FXML private TextField questionnaireTitleField;
    @FXML private Button deployButton;

    @FXML private StackPane questionStack;
    @FXML private ChoiceBox<Object> questionChooser;

    // question types in choicebox i.e. dropdown to choose type of question
    private final Object[] menuOptions = {
            "Free Text",
            "Multiple Choice",
            "Single Choice",
            "Range",
            "Rank",
    };

    // url corresponding to choisebox question selection
    private final String[] questionviewPaths = {
            "/GUI/QuestionnaireBuilder/QuestionTemplates/FreeText/FreeTextQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/MultipleChoice/MultipleChoiceQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/SingleChoice/SingleChoiceQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/Range/RangeQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/Rank/RankQuestion.fxml",
    };

    private ObservableList<QuestionnairePointer> allPointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> matchedQuestionnairePointers
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set view
        this.setView(0);
        this.setupMenu();
        this.questionChooser.getSelectionModel().select(0);

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
                        setEditingViewVisible((new_pointer != null));
                    }
                }
        );
        
        setEditingViewVisible(false);

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
        setEditingViewVisible(true);
    }

    public void setView(int viewIndex) {
        String viewPath = questionviewPaths[viewIndex];
        if (viewPath != null && viewPath.length() > 0) {
            questionStack.getChildren().clear();
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource(viewPath));
                AnchorPane.setTopAnchor(pane, 0.0);
                AnchorPane.setBottomAnchor(pane, 0.0);
                AnchorPane.setRightAnchor(pane, 0.0);
                AnchorPane.setLeftAnchor(pane, 0.0);
                questionStack.getChildren().add(0, pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupMenu() {
        questionChooser.setItems(FXCollections.observableArrayList(menuOptions));
        questionChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                setView(newNumber.intValue());
            }
        });
    }

    public void setEditingViewVisible(boolean visible) {
        if (visible) {
            this.stackPane.getChildren().add(this.editingView);
        } else {
            this.stackPane.getChildren().remove(this.editingView);
        }
    }

    public void questionnaireListViewSelectNone() {
        this.questionnairePointerListView.getSelectionModel().clearSelection();
        this.questionnaireSearchField.requestFocus();
    }
}
