package GUI.QuestionnaireBuilder;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import GUI.QuestionnaireBuilder.QuestionTemplates.QuestionTypeController;
import Helpers.GUI.FlexibleToolbarSpace;
import Helpers.GUI.PaneHelper;
import Helpers.IDHelper;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    @FXML private SplitPane questionnaireSplitPane;

    // Questionnaire specific controls
    @FXML private TextField questionnaireTitleField;
    @FXML private Button deleteButton;
    @FXML private Button saveDraftButton;
    @FXML private TreeView<Question> questionTreeView;

    // Questionnaire Toolbar
    @FXML private ToolBar questionnaireToolbar;
    @FXML private Button makeDependButton;// = new Button("Make Depend");
    @FXML private Button makeBaseButton;
    private ChoiceBox<String> dependableQuestionsChooser = new ChoiceBox<>();
    private ComboBox<String> dependableConditionsChooser = new ComboBox<>();

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
    private Button saveNewQuestionButton = new Button("Save New"),
            cancelQuestionEditButton = new Button("Cancel"),
            saveChangesQuestionButton = new Button("Save Changes"),
            deleteExistingQuestionButton = new Button("Delete"),
            clearQuestionFieldsButton = new Button("Clear");

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

    private ObservableList<QuestionnairePointer> visibleQuestionnairePointers
            = FXCollections.observableArrayList();
    private ObservableList<QuestionnairePointer> offScreenQuestionnairePointers
            = FXCollections.observableArrayList();

    private ObservableList<Question> dependableQuestions
            = FXCollections.observableArrayList();
    private ObservableList<String> dependableQuestionOptions
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Setup GUI Controls
        setupButtonsAndActions();
        setupQuestionnairePointerListView();
        setupQuestionnaireToolbar();
        setupQuestionTreeView();
        setupQuestionTypeChooser();

        // When the questionnaire view is first started nothing is being edited so do not show editing controls
        endEditingQuestionnaire();

        // Fetch the data for the left menu
        fetchDraftQuestionnaires();
    }

    // GUI Initialisation

    public void setupButtonsAndActions() {

        // Actions specific to new questions
        this.saveNewQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveNewQuestion();
            }
        });

        // Actions specific to existing questions
        this.saveChangesQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveQuestionChanges();
            }
        });
        this.deleteExistingQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteQuestion();
            }
        });

        // Actions applicable to both cases
        this.cancelQuestionEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                endEditingQuestion();
            }
        });
        this.clearQuestionFieldsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                questionTypeController.clearInputFields();
                requiredCheckBox.setSelected(false);
            }
        });
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
                            setupViewForEditingExistingQuestionnaire();
                        } else {
                            endEditingQuestionnaire();
                        }
                    }
                }
        );

        this.questionnairePointerListView.setItems(visibleQuestionnairePointers);
    }

    public void setupQuestionnaireToolbar() {
        this.questionnaireToolbar.getItems().add(1, new FlexibleToolbarSpace());
        this.dependableQuestionsChooser.setItems(dependableQuestionOptions);
        this.dependableQuestionsChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                dependableConditionsChooser.getItems().clear();
                if (newIndex.intValue() >= 0) {
                    SelectOneQuestion dependableQuestion = (SelectOneQuestion) dependableQuestions.get(newIndex.intValue());
                    ObservableList<String> items = FXCollections.observableArrayList(dependableQuestion.getAnswerOptions());
                    dependableConditionsChooser.setItems(items);
                }
            }
        });
    }

    public void setupQuestionTreeView() {
        TreeItem<Question> rootItem = new TreeItem<>(null);
        rootItem.setExpanded(true);
        this.questionTreeView.setRoot(rootItem);
        this.questionTreeView.setShowRoot(false);
        this.questionTreeView.setCellFactory(new Callback<TreeView<Question>, TreeCell<Question>>() {
            @Override
            public TreeCell<Question> call(TreeView<Question> questionTreeView) {
                return new TreeCell<Question>() {
                    @Override
                    protected void updateItem(Question question, boolean aBool) {
                        super.updateItem(question, aBool);
                        if (question != null) {
                            setText("Title: " + question.getTitle() +
                                    "   Required: " + ((question.isRequired()) ? "Yes" : "No") +
                                    (question.getCondition().equals("") ? "" : "   Condition: " + question.getCondition()));
                        }
                    }
                };
            }
        });
        this.questionTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Question>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Question>> observableValue, TreeItem<Question> old_item, TreeItem<Question> newItem) {
            existingQuestionSelected(newItem);
            }
        });
    }

    public void setupQuestionTypeChooser() {
        this.questionTypeChooser.setItems(FXCollections.observableArrayList(questionTypeOptions));
        this.questionTypeChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                questionTreeView.getSelectionModel().clearSelection();
                if (newNumber.intValue() > 1) {
                    setupViewForAddingQuestion();
                    setQuestionTypeView(newNumber.intValue());
                } else {
                    setQuestionControlsVisible(false);
                }
            }
        });
    }

    // Left Menu Methods

    public void fetchDraftQuestionnaires() {
        try {
            this.offScreenQuestionnairePointers.clear();
            this.visibleQuestionnairePointers.clear();
            this.visibleQuestionnairePointers.addAll(DataLayer.getQuestionnairePointersForState(0));
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
        endEditingQuestionnaire();
    }

    // Questionnaire Data Methods

    public void saveQuestionnaire() {
        try {
            if (isExistingQuestionnaire) {
                DataLayer.updateQuestionnare(draftQuestionnaire);
            } else {
                DataLayer.addQuestionnaire(draftQuestionnaire);
                fetchDraftQuestionnaires();
            }
        } catch (SQLException | NoQuestionnaireException e) {
            Dialogs.showInformationDialog((Stage) root.getScene().getWindow(), "There was an error saving the questionnaire. Try again.");
            e.printStackTrace();
        }
        endEditingQuestionnaire();
    }

    public void deleteQuestionnaire() {
        if (isExistingQuestionnaire) {
            try {
                DataLayer.removeQuestionnaire(draftQuestionnaire);
                fetchDraftQuestionnaires();
                endEditingQuestionnaire();
            } catch (SQLException | NoQuestionnaireException e) {
                e.printStackTrace();
            }
        } else {
            endEditingQuestionnaire();
        }
    }

    public void titleFieldUpdated() {
        boolean titleHasText = (questionnaireTitleField.getText().length() > 0);
        saveDraftButton.setDisable(!titleHasText);
        draftQuestionnaire.setTitle(questionnaireTitleField.getText());
    }

    // Questionnaire Toolbar Methods

    public void makeDependantQuestionDialog() {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(new Label("Select a question"), 0, 0);
        grid.add(dependableQuestionsChooser, 1, 0);
        grid.add(new Label("Select a choice from that question to depend on"), 0, 1);
        grid.add(dependableConditionsChooser, 1, 1);

        Callback<Void, Void> myCallback = new Callback<Void, Void>() {
            @Override
            public Void call(Void param) {
                return null;
            }
        };

        Dialogs.DialogResponse response = Dialogs.showCustomDialog((Stage) root.getScene().getWindow(), grid,
                "Setting Dependant Question", "Choose a question for the question you are editing to depend upon.", Dialogs.DialogOptions.OK_CANCEL, myCallback);
        if (response.equals(Dialogs.DialogResponse.OK)) {
            Question dependableQuestion = dependableQuestions.get(dependableQuestionsChooser.getSelectionModel().getSelectedIndex());
            String dependableCondition = dependableConditionsChooser.getSelectionModel().getSelectedItem();
            if (dependableQuestion != null && dependableCondition != null) {
                TreeItem<Question> treeItem = questionTreeView.getSelectionModel().getSelectedItem();
                Question selectedQuestion  = treeItem.getValue();

                // Remove it from where it originated
                if (treeItem.getParent().equals(questionTreeView.getRoot())) {
                    draftQuestionnaire.getQuestions().remove(selectedQuestion);
                } else {
                    Question parentQuestion = treeItem.getParent().getValue();
                    parentQuestion.removeDependantQuestion(selectedQuestion);
                }

                // Place it where you have set it to go
                dependableQuestion.addDependentQuestion(dependableCondition, selectedQuestion);
                selectedQuestion.setCondition(dependableCondition);
                // Display the changes
                populateTree();
            }
        }
    }

    public void makeBaseQuestion() {
        TreeItem<Question> treeItem = questionTreeView.getSelectionModel().getSelectedItem();
        Question selectedQuestion  = treeItem.getValue();
        // Remove it from where it originated
        treeItem.getParent().getValue().removeDependantQuestion(selectedQuestion);
        // Place it where you have set it to go
        draftQuestionnaire.getQuestions().add(selectedQuestion);
        selectedQuestion.setCondition("");
        // Display the changes
        populateTree();
    }

    public void dependantQuestionSettingControlsEnabled(boolean enabled) {
        this.makeDependButton.setDisable(!enabled);
        if (!enabled) {
            dependableQuestionsChooser.getSelectionModel().select(0);
            dependableConditionsChooser.getItems().clear();
        }
    }

    public void generateDependableQuestionOptions() {
        dependableQuestionOptions.clear();
        for (Question question : dependableQuestions) {
            String title = question.getTitle();
            dependableQuestionOptions.add("Question: " + title);
        }
        dependableQuestionsChooser.getSelectionModel().select(0);
    }

    // Question ToolBar Actions

    public void saveNewQuestion() {
        Question question = getQuestionFromController();
        if (question != null) {
            // The new question is added as a root question (i.e not dependant)
            draftQuestionnaire.addQuestion(question);
            // General Cleanup
            endEditingQuestion();
            // The tree needs to be updated
            populateTree();
        } else {
            // If question is null, it has not been fully completed
            Dialogs.showInformationDialog((Stage) root.getScene().getWindow(), "Please fill out all of the input fields before saving.");
        }
    }

    public void saveQuestionChanges() {
        Dialogs.DialogResponse response = Dialogs.DialogResponse.YES;

        Question question = questionTreeView.getSelectionModel().getSelectedItem().getValue();
        Question editedQuestion = getQuestionFromController();

        if (editedQuestion == null) {
            Dialogs.showInformationDialog((Stage) root.getScene().getWindow(), "Please fill out all of the input fields before saving.");
        } else {

            if (question.getClass() == SelectOneQuestion.class) {
                response = Dialogs.showConfirmDialog((Stage)root.getScene().getWindow(),
                        "Do you want to continue?",
                        "If you have removed choices, some of the dependant questions may be deleted.",
                        "",
                        Dialogs.DialogOptions.YES_NO);
            }

            if (response.equals(Dialogs.DialogResponse.YES)) {
                // Update question with new edited values
                question.updateContents(editedQuestion);
                // Remove dependant questions if necessary for SelectOneQuestion(s)
                if (question.getClass() == SelectOneQuestion.class) {
                    SelectOneQuestion selectOneQuestion = (SelectOneQuestion) question;
                    ArrayList<String> keysToRemove = new ArrayList<>();
                    for (String key : question.getDependantQuestionsMap().keySet()) {
                        if (!selectOneQuestion.getAnswerOptions().contains(key)) {
                            keysToRemove.add(key);
                        }
                    }
                    for (String key : keysToRemove) {
                        question.getDependantQuestionsMap().remove(key);
                    }
                }
                populateTree();
                endEditingQuestion();
            }
        }
    }

    public void deleteQuestion() {
        Dialogs.DialogResponse response;
        TreeItem<Question> treeItem = questionTreeView.getSelectionModel().getSelectedItem();
        Question question = treeItem.getValue();

        String dialogMessage;
        if (question.hasDependantQuestions()) {
            dialogMessage = "Deleting this question will also delete all of it's dependant questions.";
        } else {
            dialogMessage = "You cannot undo this action.";
        }
        response = Dialogs.showConfirmDialog((Stage)root.getScene().getWindow(),
                "Do you want to continue?",
                dialogMessage,
                "",
                Dialogs.DialogOptions.YES_NO);

        if (response.equals(Dialogs.DialogResponse.YES)) {
            if (treeItem.getParent().equals(questionTreeView.getRoot())) {
                // Remove from root question array
                draftQuestionnaire.getQuestions().remove(question);
            } else {
                // Remove from parent's dependant questions array
                treeItem.getParent().getValue().removeDependantQuestion(question);
            }
            populateTree();
            endEditingQuestion();
        }
    }

    public Question getQuestionFromController() {
        try {
            return questionTypeController.getConstructedQuestion(IDHelper.generateRandomID(), requiredCheckBox.isSelected());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Question Tree View Methods

    public void existingQuestionSelected(TreeItem<Question> newItem) {
        if (newItem != null) {
            // Clear new question type chooser
            questionTypeChooser.getSelectionModel().select(0);
            // Setup view for editing the question selected
            setQuestionTypeViewForQuestion(newItem.getValue());
            setupViewForEditingQuestion(newItem);
            // Add existing question details to the controller's input fields
            questionTypeController.populateWithExistingQuestion(newItem.getValue());
        } else {
            setQuestionControlsVisible(false);
        }
    }

    public void populateTree() {
        dependableQuestions.clear();
        questionTreeView.getSelectionModel().clearSelection();
        questionTreeView.getRoot().getChildren().clear();
        for (Question question : this.draftQuestionnaire.getQuestions()) {
            question.setCondition("");
            TreeItem<Question> leaf = new TreeItem<>(question);
            questionTreeView.getRoot().getChildren().add(leaf);
            leaf.setExpanded(true);
            if (question.getClass() == YesNoQuestion.class || question.getClass() == SelectOneQuestion.class) {
                dependableQuestions.add(question);
            }
            generateTreeItemChildren(leaf, question);
        }
        generateDependableQuestionOptions();
    }

    public void generateTreeItemChildren(TreeItem<Question> parent, Question question) {
        for (String key : question.getDependantQuestionsMap().keySet()) {
            List<Question> questions = question.getDependantQuestionsMap().get(key);
            for (Question subQuestion : questions) {
                subQuestion.setCondition(key);
                TreeItem<Question> subQuestionTreeItem = new TreeItem<>(subQuestion);
                subQuestionTreeItem.setExpanded(true);
                parent.getChildren().add(subQuestionTreeItem);
                if (subQuestion.getClass() == YesNoQuestion.class || subQuestion.getClass() == SelectOneQuestion.class) {
                    dependableQuestions.add(subQuestion);
                }
                if (subQuestion.hasDependantQuestions()) {
                    generateTreeItemChildren(subQuestionTreeItem, subQuestion);
                }
            }
        }

    }

    public void clearTreeViewSelection() {
        this.questionTreeView.getSelectionModel().clearSelection();
    }

    // Questionnaire Context Transition Actions

    public void setupViewForBuildingNewQuestionnaire() {
        // Setup Questionniare
        questionnairePointerListView.getSelectionModel().clearSelection();
        draftQuestionnaire = new Questionnaire("", 0);
        isExistingQuestionnaire = false;
        prepareForEditingQuestionnaire();
    }

    public void setupViewForEditingExistingQuestionnaire() {
        try {
            // Fetch Questionniare
            QuestionnairePointer pointer = questionnairePointerListView.getSelectionModel().getSelectedItem();
            draftQuestionnaire = DataLayer.getQuestionnaireWithPointer(pointer);
            isExistingQuestionnaire = true;
            prepareForEditingQuestionnaire();
        } catch (NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    public void prepareForEditingQuestionnaire() {
        setQuestionnairePaneVisible(true);
        this.questionnaireTitleField.setText(draftQuestionnaire.getTitle());
        this.saveDraftButton.setDisable(!isExistingQuestionnaire);
        if (isExistingQuestionnaire) {
            this.saveDraftButton.setText("Save Changes");
            this.deleteButton.setText("Delete");
        } else {
            this.saveDraftButton.setText("Save Draft");
            this.deleteButton.setText("Cancel");
        }
        this.makeDependButton.setDisable(true);
        this.makeBaseButton.setDisable(true);
        populateTree();
        // Initially hides question editing controls
        this.questionTypeChooser.getSelectionModel().select(0);
    }

    public void endEditingQuestionnaire() {
        setQuestionnairePaneVisible(false);
        this.questionnaireTitleField.setText("");
        this.questionTreeView.getRoot().getChildren().clear();
        this.questionTypeChooser.getSelectionModel().select(0);
        this.draftQuestionnaire = null;
        this.questionnairePointerListView.getSelectionModel().clearSelection();
    }

    public void setQuestionnairePaneVisible(boolean visible) {
        // Shows / Hides Questionnaire Editing view
        this.questionnaireStackPane.getChildren().clear();
        if (visible) {
            this.questionnaireStackPane.getChildren().add(this.questionnaireSplitPane);
        }
    }

    // Question Context Transition Actions

    public void setupViewForAddingQuestion() {
        setQuestionControlsVisible(true);
        this.questionToolbar.getItems().setAll(saveNewQuestionButton, cancelQuestionEditButton, flexibleSpace, clearQuestionFieldsButton);
        // Ensure that the required control is enabled
        this.requiredCheckBox.setDisable(false);
    }

    public void setupViewForEditingQuestion(TreeItem<Question> item) {
        setQuestionControlsVisible(true);
        dependantQuestionSettingControlsEnabled(true);
        this.questionToolbar.getItems().setAll(saveChangesQuestionButton, deleteExistingQuestionButton, cancelQuestionEditButton, flexibleSpace, clearQuestionFieldsButton);
        // If a question's parent is root, you can edit the required value. Dependant questions cannot change this value.
        this.makeBaseButton.setDisable(item.getParent().equals(questionTreeView.getRoot()));
        this.requiredCheckBox.setDisable(!item.getParent().equals(questionTreeView.getRoot()));
        this.requiredCheckBox.setSelected(item.getValue().isRequired());
    }

    public void endEditingQuestion() {
        // Reset controls that start question editing
        this.questionTypeChooser.getSelectionModel().select(0);
        this.questionTreeView.getSelectionModel().clearSelection();
        // Clear values in question controls
        this.requiredCheckBox.setSelected(false);
        this.questionTypeIdentifierLabel.setText("");
        this.questionStackPane.getChildren().clear();
        this.questionToolbar.getItems().clear();
        // QuestionTypeController is no longer required
        this.questionTypeController = null;
        // Hide Question editing controls
        setQuestionControlsVisible(false);
        dependantQuestionSettingControlsEnabled(false);
    }

    public void setQuestionControlsVisible(boolean visible) {
        this.requiredCheckBox.setVisible(visible);
        this.questionTypeIdentifierLabel.setVisible(visible);
        this.questionStackPane.setVisible(visible);
        this.questionToolbar.setVisible(visible);
    }

    // Setting Question StackPane view

    public void setQuestionTypeView(int viewIndex) {
        questionStackPane.getChildren().clear();
        String viewPath = questionViewPaths[viewIndex];
        if (viewPath != null && viewPath.length() > 0) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(viewPath));
                Pane pane = PaneHelper.loadPaneForAnchorParentWithFXMLLoader(fxmlLoader);
                questionStackPane.getChildren().add(0, pane);
                questionTypeController = fxmlLoader.getController();
                questionTypeIdentifierLabel.setText(questionTypeController.getQuestionTypeIdentifier());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setQuestionTypeViewForQuestion(Question question) {

        if (question.getClass() == TextQuestion.class) {
            setQuestionTypeView(2);
        }
        if (question.getClass() == SelectManyQuestion.class) {
            setQuestionTypeView(3);
        }
        if (question.getClass() == SelectOneQuestion.class) {
            setQuestionTypeView(4);
        }
        if (question.getClass() == YesNoQuestion.class) {
            setQuestionTypeView(5);
        }
        if (question.getClass() == RangeQuestion.class) {
            setQuestionTypeView(6);
        }
        if (question.getClass() == RankQuestion.class) {
            setQuestionTypeView(7);
        }
    }

}
