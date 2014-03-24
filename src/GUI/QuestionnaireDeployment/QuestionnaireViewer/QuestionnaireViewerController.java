package GUI.QuestionnaireDeployment.QuestionnaireViewer;

import GUI.QuestionnaireDeployment.QuestionnaireViewer.QuestionViews.QuestionTypeViewController;
import Helpers.GUI.PaneHelper;
import ModelObjects.Questionnaire;
import ModelObjects.Questions.Question;
import ModelObjects.Questions.Types.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by James Bellamy on 24/03/2014.
 *
 */
public class QuestionnaireViewerController implements Initializable {

    @FXML private Label questionnaireTitleLabel;
    @FXML private TreeView<Question> questionTreeView;

    @FXML private CheckBox requiredCheckBox;
    @FXML private Label questionTypeIdentifierLabel;
    @FXML private StackPane questionStackPane;

    private Questionnaire questionnaire;

    // File paths for each of the question type views
    private final String[] questionViewPaths = {
            null,
            "/GUI/QuestionnaireDeployment/QuestionnaireViewer/QuestionViews/BasicQuestion/basicQuestionView.fxml",
            "/GUI/QuestionnaireDeployment/QuestionnaireViewer/QuestionViews/ChoiceQuestion/choiceQuestionView.fxml",
            "/GUI/QuestionnaireDeployment/QuestionnaireViewer/QuestionViews/RangeQuestion/rangeQuestionView.fxml",
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupQuestionTreeView();
        setQuestionControlsVisible(false);
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
                questionSelected(newItem);
            }
        });
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;

        this.questionnaireTitleLabel.setText(questionnaire.getTitle());
        populateTree();
    }

    public void populateTree() {
        questionTreeView.getSelectionModel().clearSelection();
        questionTreeView.getRoot().getChildren().clear();
        for (Question question : this.questionnaire.getQuestions()) {
            question.setCondition("");
            TreeItem<Question> leaf = new TreeItem<>(question);
            questionTreeView.getRoot().getChildren().add(leaf);
            leaf.setExpanded(true);
            generateTreeItemChildren(leaf, question);
        }
    }

    public void generateTreeItemChildren(TreeItem<Question> parent, Question question) {
        for (String key : question.getDependantQuestionsMap().keySet()) {
            List<Question> questions = question.getDependantQuestionsMap().get(key);
            for (Question subQuestion : questions) {
                subQuestion.setCondition(key);
                TreeItem<Question> subQuestionTreeItem = new TreeItem<>(subQuestion);
                subQuestionTreeItem.setExpanded(true);
                parent.getChildren().add(subQuestionTreeItem);
                if (subQuestion.hasDependantQuestions()) {
                    generateTreeItemChildren(subQuestionTreeItem, subQuestion);
                }
            }
        }
    }

    public void clearTreeViewSelection() {
        this.questionTreeView.getSelectionModel().clearSelection();
    }

    public void questionSelected(TreeItem<Question> newItem) {
        if (newItem != null) {
            setQuestionControlsVisible(true);
            setQuestionTypeViewForQuestion(newItem.getValue());
        } else {
            setQuestionControlsVisible(false);
        }
    }

    public void setQuestionControlsVisible(boolean visible) {
        this.requiredCheckBox.setVisible(visible);
        this.questionTypeIdentifierLabel.setVisible(visible);
        this.questionStackPane.setVisible(visible);
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
                QuestionTypeViewController questionTypeViewController = fxmlLoader.getController();
                Question question = this.questionTreeView.getSelectionModel().getSelectedItem().getValue();
                questionTypeViewController.setQuestion(question);
                if (question.getClass() == TextQuestion.class) {
                    questionTypeIdentifierLabel.setText("Free Text Question: ");
                }
                if (question.getClass() == SelectOneQuestion.class) {
                    questionTypeIdentifierLabel.setText("Single Choice Question: ");
                }
                if (question.getClass() == YesNoQuestion.class) {
                    questionTypeIdentifierLabel.setText("Yes or No Question: ");
                }
                if (question.getClass() == SelectManyQuestion.class) {
                    questionTypeIdentifierLabel.setText("Multiple Choice Question: ");
                }
                if (question.getClass() == RankQuestion.class) {
                    questionTypeIdentifierLabel.setText("Rank Question: ");
                }
                if (question.getClass() == RangeQuestion.class) {
                    questionTypeIdentifierLabel.setText("Range Question: ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setQuestionTypeViewForQuestion(Question question) {

        if (question.getClass() == TextQuestion.class) {
            setQuestionTypeView(1);
        }
        if (question.getClass() == SelectManyQuestion.class) {
            setQuestionTypeView(2);
        }
        if (question.getClass() == SelectOneQuestion.class) {
            setQuestionTypeView(2);
        }
        if (question.getClass() == YesNoQuestion.class) {
            setQuestionTypeView(2);
        }
        if (question.getClass() == RangeQuestion.class) {
            setQuestionTypeView(3);
        }
        if (question.getClass() == RankQuestion.class) {
            setQuestionTypeView(2);
        }
    }
}
