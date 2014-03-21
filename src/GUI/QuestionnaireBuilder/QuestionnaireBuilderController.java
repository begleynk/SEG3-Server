package GUI.QuestionnaireBuilder;

import Accessors.DataLayer;
import Exceptions.NoQuestionnaireException;
import GUI.QuestionnaireBuilder.QuestionTemplates.QuestionTypeController;
import Helpers.GUI.FlexibleToolbarSpace;
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
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    @FXML private ChoiceBox<Question> dependableQuestionsChooser;
    @FXML private ChoiceBox<String> dependableConditionsChooser;
    @FXML private Button makeDependButton = new Button("Set");

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupButtonsAndActions();
        setupQuestionnairePointerListView();
        setupQuestionnaireToolbar();
        setupQuestionTreeView();
        setupQuestionTypeChooser();

        // When the questionnaire view is first started nothing is being edited so do not show editing controls
        endEditing();

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
        this.cancelQuestionEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cancelQuestionEdit();
            }
        });

        // Actions specific to existing questions
        this.saveChangesQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Question question = questionTreeView.getSelectionModel().getSelectedItem().getValue();
                Dialogs.DialogResponse response = Dialogs.DialogResponse.YES;
                Question constructedQuestion = null;
                try {
                    constructedQuestion = questionTypeController.getConstructedQuestion(IDHelper.generateRandomID(), requiredCheckBox.isSelected());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (constructedQuestion == null) {
                    Dialogs.showInformationDialog((Stage) root.getScene().getWindow(), "Please fill out all of the input fields before saving.");
                } else {

                    if (question.getClass() == SelectOneQuestion.class) {
                        response = Dialogs.showConfirmDialog((Stage)root.getScene().getWindow(),
                                "Do you want to continue?",
                                "If you have removed choices, some of the dependent questions may be deleted.",
                                "",
                                Dialogs.DialogOptions.YES_NO);
                    }
                    if (response.equals(Dialogs.DialogResponse.YES)) {

                        question.updateContents(constructedQuestion);

                        if (question.getClass() == SelectOneQuestion.class) {
                            SelectOneQuestion selectOneQuestion = (SelectOneQuestion) question;
                            ArrayList<String> keysToRemove = new ArrayList<>();
                            for (String key : question.getDependentQuestionsMap().keySet()) {
                                if (!selectOneQuestion.getAnswerOptions().contains(key)) {
                                    keysToRemove.add(key);
                                }
                            }
                            for (String key : keysToRemove) {
                                question.getDependentQuestionsMap().remove(key);
                            }
                        }

                        populateTree();
                        setQuestionEditingViewVisible(false);
                    }
                }
            }
        });
        this.deleteExistingQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TreeItem<Question> questionTreeItem = questionTreeView.getSelectionModel().getSelectedItem();
                Dialogs.DialogResponse response;
                if (questionTreeItem.getValue().hasDependentQuestions()) {
                    response = Dialogs.showConfirmDialog((Stage)root.getScene().getWindow(),
                        "Do you want to continue?",
                        "Deleting this question will also delete all of it's dependent questions.",
                        "",
                        Dialogs.DialogOptions.YES_NO);
                } else {
                    response = Dialogs.showConfirmDialog((Stage)root.getScene().getWindow(),
                            "Do you want to continue?",
                            "You cannot undo this action.",
                            "",
                            Dialogs.DialogOptions.YES_NO);
                }
                if (response.equals(Dialogs.DialogResponse.YES)) {
                    if (questionTreeItem.getParent().equals(questionTreeView.getRoot())) {
                        draftQuestionnaire.getQuestions().remove(questionTreeItem.getValue());
                    } else {
                        TreeItem<Question> parent = questionTreeItem.getParent();
                        parent.getValue().removeDependentQuestion(questionTreeItem.getValue());
                    }
                    populateTree();
                    setQuestionEditingViewVisible(false);
                }
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
                            endEditing();
                        }
                    }
                }
        );

        this.questionnairePointerListView.setItems(visibleQuestionnairePointers);
    }

    public void setupQuestionnaireToolbar() {
        this.questionnaireToolbar.getItems().add(new FlexibleToolbarSpace());
        this.dependableQuestionsChooser = new ChoiceBox<>();
        this.dependableConditionsChooser = new ChoiceBox<>();
        this.dependableQuestionsChooser.setItems(dependableQuestions);
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
                    setupViewForEditingQuestion(new_item.getParent());
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
                    questionTypeController.populateWithExistingQuestion(question);
                    requiredCheckBox.setSelected(question.isRequired());
                    dependentQuestionSettingControlsEnabled(true);
                } else {
                    setQuestionEditingViewVisible(false);
                }
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
                    setQuestionEditingViewVisible(false);
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
        endEditing();
    }

    // Preparation for Questionnaire Context Transitions

    public void prepareForEditing() {
        setQuestionnaireEditingPaneVisible(true);
        this.questionnaireTitleField.setText(draftQuestionnaire.getTitle());
        this.saveDraftButton.setDisable(!isExistingQuestionnaire);
        if (isExistingQuestionnaire) {
            this.saveDraftButton.setText("Save Changes");
            this.deleteButton.setText("Delete");
        } else {
            this.saveDraftButton.setText("Save Draft");
            this.deleteButton.setText("Cancel");
        }
        populateTree();
        this.questionTypeChooser.getSelectionModel().select(0);
        setQuestionEditingViewVisible(false);
    }

    public void endEditing() {
        setQuestionnaireEditingPaneVisible(false);
        this.questionnaireTitleField.setText("");
        this.questionTreeView.getRoot().getChildren().clear();
        this.questionTypeChooser.getSelectionModel().select(0);
        this.draftQuestionnaire = null;
        this.questionnairePointerListView.getSelectionModel().clearSelection();
    }

    public void setQuestionnaireEditingPaneVisible(boolean visible) {
        this.questionnaireStackPane.getChildren().clear();
        if (visible) {
            this.questionnaireStackPane.getChildren().add(this.questionnaireSplitPane);
        }
    }

    public void setupViewForBuildingNewQuestionnaire() {
        // Setup Questionniare
        questionnairePointerListView.getSelectionModel().clearSelection();
        draftQuestionnaire = new Questionnaire("", 0);
        isExistingQuestionnaire = false;
        prepareForEditing();
    }

    public void setupViewForEditingExistingQuestionnaire() {
        try {
            // Fetch Questionniare
            QuestionnairePointer pointer = questionnairePointerListView.getSelectionModel().getSelectedItem();
            draftQuestionnaire = DataLayer.getQuestionnaireWithPointer(pointer);
            isExistingQuestionnaire = true;
            prepareForEditing();
        } catch (NoQuestionnaireException e) {
            e.printStackTrace();
        }
    }

    // Questionnaire Toolbar Methods

    public void dependentQuestionSettingControlsEnabled(boolean enabled) {
        this.dependableQuestionsChooser.setDisable(!enabled);
        this.dependableConditionsChooser.setDisable(!enabled);
        this.makeDependButton.setDisable(!enabled);
        if (!enabled) {
            dependableQuestionsChooser.getSelectionModel().clearSelection();
            dependableConditionsChooser.getSelectionModel().clearSelection();
        }
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
        endEditing();
    }

    public void deleteQuestionnaire() {
        if (isExistingQuestionnaire) {
            try {
                DataLayer.removeQuestionnaire(draftQuestionnaire);
                fetchDraftQuestionnaires();
                endEditing();
            } catch (SQLException | NoQuestionnaireException e) {
                e.printStackTrace();
            }
        } else {
            endEditing();
        }
    }

    public void titleFieldUpdated() {
        boolean titleHasText = (questionnaireTitleField.getText().length() > 0);
        saveDraftButton.setDisable(!titleHasText);
        draftQuestionnaire.setTitle(questionnaireTitleField.getText());
    }

    // Question Tree View Methods

    public void populateTree() {
        dependableQuestions.clear();
        questionTreeView.getSelectionModel().clearSelection();
        questionTreeView.getRoot().getChildren().clear();
        for (Question question : this.draftQuestionnaire.getQuestions()) {
            TreeItem<Question> leaf = new TreeItem<>(question);
            questionTreeView.getRoot().getChildren().add(leaf);
            leaf.setExpanded(true);
            if (question.getClass() == YesNoQuestion.class || question.getClass() == SelectOneQuestion.class) {
                dependableQuestions.add(question);
            }
            generateTreeItemChildren(leaf, question);
        }
        System.out.println(draftQuestionnaire.toString());
    }

    public void generateTreeItemChildren(TreeItem<Question> parent, Question question) {
        for (String key : question.getDependentQuestionsMap().keySet()) {
            List<Question> questions = question.getDependentQuestionsMap().get(key);
            for (Question subQuestion : questions) {
                TreeItem<Question> subQuestionTreeItem = new TreeItem<>(subQuestion);
                subQuestionTreeItem.setExpanded(true);
                parent.getChildren().add(subQuestionTreeItem);
                if (subQuestion.getClass() == YesNoQuestion.class || subQuestion.getClass() == SelectOneQuestion.class) {
                    dependableQuestions.add(subQuestion);
                }
                if (subQuestion.hasDependentQuestions()) {
                    generateTreeItemChildren(subQuestionTreeItem, subQuestion);
                }
            }
        }

    }

    public void clearTreeViewSelection() {
        this.questionTreeView.getSelectionModel().clearSelection();
    }

    // Question Editor Methods

    public void setupViewForAddingQuestion() {
        setQuestionEditingViewVisible(true);
        questionToolbar.getItems().setAll(saveNewQuestionButton, cancelQuestionEditButton, flexibleSpace, clearQuestionFieldsButton);
        requiredCheckBox.setDisable(false);
        requiredCheckBox.setSelected(false);
    }

    public void setupViewForEditingQuestion(TreeItem<Question> parent) {
        questionTypeChooser.getSelectionModel().select(0);
        setQuestionEditingViewVisible(true);
        questionToolbar.getItems().setAll(saveChangesQuestionButton, deleteExistingQuestionButton, cancelQuestionEditButton, flexibleSpace, clearQuestionFieldsButton);
        requiredCheckBox.setDisable((!parent.equals(questionTreeView.getRoot())));
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

    // Question ToolBar Actions

    public void saveNewQuestion() {
        Question question = null;
        try {
            question = questionTypeController.getConstructedQuestion(IDHelper.generateRandomID(), requiredCheckBox.isSelected());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (question != null) {
            draftQuestionnaire.addQuestion(question);
            populateTree();
            // Called for convenience (not actually cancelling as already saved)
            cancelQuestionEdit();
            requiredCheckBox.setSelected(false);
        } else {
            Dialogs.showInformationDialog((Stage) root.getScene().getWindow(), "Please fill out all of the input fields before saving.");
        }
    }

    public void cancelQuestionEdit() {
        questionTreeView.getSelectionModel().clearSelection();
        questionTypeChooser.getSelectionModel().select(0);
        questionToolbar.getItems().clear();
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

}
