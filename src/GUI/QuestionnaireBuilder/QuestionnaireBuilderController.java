package GUI.QuestionnaireBuilder;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import GUI.QuestionnaireBuilder.QuestionTemplates.QuestionTypeController;
import Helpers.GUI.AlertDialog;
import Helpers.GUI.FlexibleToolbarSpace;
import ModelObjects.Questionnaire;
import ModelObjects.QuestionnairePointer;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

    // Questionnaire that we are building
    private Questionnaire draftQuestionnaire;
    private boolean isExistingQuestionnaire = false;

    // Flexible Space used in ToolBars
    private static Region flexibleSpace = new FlexibleToolbarSpace();

    // Left Menu Controls
    @FXML private TextField questionnaireSearchField;
    @FXML private ListView<QuestionnairePointer> questionnairePointerListView;

    // StackPane that holds the questionnaire editing view
    @FXML private StackPane questionnaireStackPane;
    // SplitPane which contains all of the controls used to create // edit a questionnaire
    @FXML private SplitPane questionnaireEditingView;

    // Questionnaire specific controls
    @FXML private TextField questionnaireTitleField;
    @FXML private Button deployButton;
    @FXML private Button saveDraftButton;
    @FXML private TreeView<Question> questionTreeView;

    // Control for creating a question
    @FXML private ChoiceBox<Object> questionTypeChooser;

    // Question specific controls
    @FXML private CheckBox requiredCheckBox;
    @FXML private Label questionTypeIdentifierLabel;
    // StackPane that holds the pane specific to the type of question selected
    @FXML private StackPane questionStackPane;
    // ToolBar for saving / deleting / cancelling question being edited
    @FXML private ToolBar questionToolbar;

    // QuestionToolbar buttons
    private Button saveNewQuestionButton, saveChangesQuestionButton, deleteExistingQuestionButton,
            cancelNewQuestionButton, clearQuestionFieldsButton;

    // Controller of the current question type being edited
    private QuestionTypeController questionTypeController;

    // Question type menu options
    private final Object[] questionTypeOptions = {
            "Select a Question Type to create",
            new Separator(),
            "Free Text",
            "Multiple Choice",
            "Single Choice",
            "Yes or No Choice",
            "Range",
            "Rank",
    };

    // File paths for each of the question type views
    private final String[] questionViewPaths = {
            null,
            null,
            "/GUI/QuestionnaireBuilder/QuestionTemplates/FreeText/FreeTextQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/MultipleChoice/MultipleChoiceQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/SingleChoice/SingleChoiceQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/YesNoChoice/YesNoQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/Range/RangeQuestion.fxml",
            "/GUI/QuestionnaireBuilder/QuestionTemplates/Rank/RankQuestion.fxml",
    };

    private ObservableList<QuestionnairePointer> allQuestionnairePointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> matchedQuestionnairePointers
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Setup QuestionToolbar buttons
        this.saveNewQuestionButton = new Button("Save New");
        this.cancelNewQuestionButton = new Button("Cancel");
        this.saveChangesQuestionButton = new Button("Save Changes");
        this.deleteExistingQuestionButton = new Button("Delete");
        this.clearQuestionFieldsButton = new Button("Clear");

        // Saving a questoinnaire draft
        this.saveDraftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveQuestionnaire();
                fetchDraftQuestionnaires();
            }
        });

        // Set Actions for QuestionToolbar buttons
        this.saveNewQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveConstructedQuestion();
            }
        });
        this.cancelNewQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                questionTypeChooser.getSelectionModel().select(0);
            }
        });
        this.saveChangesQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO: Provide Action
            }
        });
        this.deleteExistingQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO: Provide Action
            }
        });
        this.clearQuestionFieldsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                questionTypeController.clearInputFields();
            }
        });

        // Questionnaire Title Field
        questionnaireTitleField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                deployButton.setDisable(!(questionnaireTitleField.getText().length() > 0));
                draftQuestionnaire.setTitle(questionnaireTitleField.getText());
            }
        });

        // Setup Left Menu List
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
                            setupViewForEditingExistingQuestionnaire();
                        }
                    }
                }
        );

        this.questionTreeView.setCellFactory(new Callback<TreeView<Question>, TreeCell<Question>>() {
            @Override
            public TreeCell<Question> call(TreeView<Question> questionTreeView) {
                return new TreeCell<Question>() {
                    @Override
                    protected void updateItem(Question question, boolean aBool) {
                        super.updateItem(question, aBool);
                        if (question != null) {
                            setText("Title: " + question.getTitle() + "  Description: " + question.getDescription());
                        }
                    }
                };
            }
        });
        this.questionTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Question>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Question>> observableValue, TreeItem<Question> old_item, TreeItem<Question> new_item) {
                if (new_item != null) {
                    Question question = new_item.getValue();

                    questionTypeChooser.getSelectionModel().select(0);
                    setupViewForEditingQuestion();

                    if (question.getClass() == TextQuestion.class) {
                        System.out.println(new_item.getValue().getTitle() + " is a Free Text Question");
                        setQuestionTypeView(0);
                    }
                    if (question.getClass() == SelectOneQuestion.class) {
                        System.out.println(new_item.getValue().getTitle() + " is a Select One Question");
                    }
                    if (question.getClass() == SelectManyQuestion.class) {
                        System.out.println(new_item.getValue().getTitle() + " is a Select Many Question");
                    }
                    if (question.getClass() == YesNoQuestion.class) {
                        System.out.println(new_item.getValue().getTitle() + " is a Yes or No Question");
                    }
                    if (question.getClass() == RankQuestion.class) {
                        System.out.println(new_item.getValue().getTitle() + " is a Rank Question");
                    }
                    if (question.getClass() == RangeQuestion.class) {
                        System.out.println(new_item.getValue().getTitle() + " is a Range Question");
                    }
                }
            }
        });

        // Setup the questionTypeChooser Control
        this.questionTypeChooser.setItems(FXCollections.observableArrayList(questionTypeOptions));
        this.questionTypeChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                clearTreeViewSelection();
                if (newNumber.intValue() > 1) {
                    setupViewForAddingQuestion();
                    setQuestionTypeView(newNumber.intValue());
                } else {
                    setQuestionEditingViewVisible(false);
                }
            }
        });
        this.questionTypeChooser.getSelectionModel().select(0);

        this.questionTreeView.setShowRoot(false);

        // When the questionnaire view is first started nothing is being edited so do not show editing controls
        setQuestionnaireEditingViewVisible(false);
        // Same goes for the question editing view
        setQuestionEditingViewVisible(false);

        // Fetch the data for the left menu
        fetchDraftQuestionnaires();
    }

    // Left Menu Methods

    // Fetching Menu data [QuestionnairePointer(s)]
    public void fetchDraftQuestionnaires() {
        try {
            this.allQuestionnairePointers.setAll(DataLayer.getQuestionnairePointersForState(0));
            searchInputChangedAction();
        } catch (SQLException | NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void searchInputChangedAction() {
        String searchTerm = questionnaireSearchField.getText();
        questionnairePointerListView.getSelectionModel().clearSelection();
        if (searchTerm == null || searchTerm.equals("") ) {
            questionnairePointerListView.setItems(allQuestionnairePointers);
        } else {
            questionnairePointerListView.setItems(fuzzySearchQuestionnairePointerUsingSearchTerm(searchTerm));
        }
    }

    public ObservableList<QuestionnairePointer> fuzzySearchQuestionnairePointerUsingSearchTerm(String searchTerm) {
        matchedQuestionnairePointers.clear();
        searchTerm = searchTerm.trim().toLowerCase();
        for (QuestionnairePointer pointer : allQuestionnairePointers) {
            if (pointer.getTitle().toLowerCase().startsWith(searchTerm) ||
                    pointer.getTitle().toLowerCase().contains(searchTerm)) {
                matchedQuestionnairePointers.add(pointer);
            }
        }
        return matchedQuestionnairePointers;
    }

    public void questionnaireListViewSelectNone() {
        this.questionnairePointerListView.getSelectionModel().clearSelection();
        this.questionnaireSearchField.requestFocus();
    }

    public void setupViewForBuildingNewQuestionnaire() {
        questionTypeChooser.getSelectionModel().select(0);
        questionnairePointerListView.getSelectionModel().clearSelection();
        setQuestionnaireEditingViewVisible(true);
        draftQuestionnaire = new Questionnaire("", 0);
        questionnaireTitleField.setText("");
        isExistingQuestionnaire = false;
        deployButton.setDisable(true);
        populateTree();
    }

    public void setupViewForEditingExistingQuestionnaire() {
        try {
            QuestionnairePointer pointer = questionnairePointerListView.getSelectionModel().getSelectedItem();
            draftQuestionnaire = DataLayer.getQuestionnaireWithPointer(pointer);
            questionnaireTitleField.setText(draftQuestionnaire.getTitle());
            // TODO: Setup Question Tree
            System.out.println(draftQuestionnaire.toString());
            questionTypeChooser.getSelectionModel().select(0);
            setQuestionnaireEditingViewVisible(true);
            isExistingQuestionnaire = true;
            deployButton.setDisable(!(draftQuestionnaire.getTitle().length() > 0));
            populateTree();
        } catch (NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    // Questionnaire Construction View (Right View) Methods

    public void setQuestionnaireEditingViewVisible(boolean visible) {
        this.questionnaireStackPane.getChildren().clear();
        if (visible) {
            this.questionnaireStackPane.getChildren().add(this.questionnaireEditingView);
        }
    }

    // Question Editor Methods

    public void setupViewForAddingQuestion() {
        setQuestionEditingViewVisible(true);
        questionToolbar.getItems().setAll(saveNewQuestionButton, cancelNewQuestionButton, flexibleSpace, clearQuestionFieldsButton);
    }

    public void setupViewForEditingQuestion() {
        setQuestionEditingViewVisible(true);
        questionToolbar.getItems().setAll(saveChangesQuestionButton, deleteExistingQuestionButton, flexibleSpace, clearQuestionFieldsButton);
    }

    public void setQuestionEditingViewVisible(boolean visible) {
        this.questionToolbar.setVisible(visible);
        this.requiredCheckBox.setVisible(visible);
        this.questionTypeIdentifierLabel.setVisible(visible);
        if (!visible) {
            this.questionStackPane.getChildren().clear();
            this.questionTypeController = null;
        }
    }

    // Question Tree View Methods

    public void populateTree() {
        TreeItem<Question> rootItem = new TreeItem<>(null);
        rootItem.setExpanded(true);
        questionTreeView.setRoot(rootItem);
        for (Question question : this.draftQuestionnaire.getQuestions()) {
            TreeItem<Question> leaf = new TreeItem<>(question);
            questionTreeView.getRoot().getChildren().add(leaf);
            // TODO: recursive call to all dependent questions
        }
    }

    public void clearTreeViewSelection() {
        this.questionTreeView.getSelectionModel().clearSelection();
    }

    // Question ToolBar Actions

    public void saveConstructedQuestion() {
        Question question = questionTypeController.getConstructedQuestion("some id", requiredCheckBox.isSelected());
        if (question != null) {
            draftQuestionnaire.addQuestion(question);
            System.out.println(draftQuestionnaire.toString());
            populateTree();
        }
    }

    // Setting Question StackPane view

    public void setQuestionTypeView(int viewIndex) {
        String viewPath = questionViewPaths[viewIndex];
        if (viewPath != null && viewPath.length() > 0) {
            questionStackPane.getChildren().clear();
            try {

                URL viewPathURL = getClass().getResource(viewPath);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(viewPathURL);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                AnchorPane pane = (AnchorPane) fxmlLoader.load();

                AnchorPane.setTopAnchor(pane, 0.0);
                AnchorPane.setBottomAnchor(pane, 0.0);
                AnchorPane.setRightAnchor(pane, 0.0);
                AnchorPane.setLeftAnchor(pane, 0.0);

                questionTypeController = fxmlLoader.getController();
                questionTypeIdentifierLabel.setText(questionTypeController.getQuestionTypeIdentifier());

                questionStackPane.getChildren().add(0, pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            questionStackPane.getChildren().clear();
        }
    }

    public void saveQuestionnaire()
    {
        boolean success;
        try
        {
            success = DataLayer.addQuestionnaire(draftQuestionnaire);
        }
        catch (SQLException e)
        {
            success = false;
            e.printStackTrace();
        }
        if(!success)
        {
            new AlertDialog((Stage)root.getScene().getWindow(), "There was an error saving the questionnaire. Try again.", AlertDialog.ICON_INFO).showAndWait();
        }
    }

}
