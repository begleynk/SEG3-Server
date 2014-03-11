package GUI.ConnectTablets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan on 05/03/14.
 *
 */
public class SettingControlsController implements Initializable {

    // textfield and listview from GUI
    @FXML private TextField questionSearchField;
    @FXML private ListView questionListView;
    @FXML private ToolBar rightPaneToolBar;
    @FXML private Label ipaddress;
    @FXML private Label port;
    @FXML private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ipaddress.setText("hello");
        //port.setText("the port yo");
    }

    //get ippaddress
    //get port set



}// end class:




//############################################################################################################
//############################################################################################################
//############################################################################################################
//############################################################################################################
//############################################################################################################
//############################################################################################################
//############################################################################################################
//############################################################################################################
//############################################################################################################

/*
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
}

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
}


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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}


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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

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
}

*/
