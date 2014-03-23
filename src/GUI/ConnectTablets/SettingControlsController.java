package GUI.ConnectTablets;

import Helpers.IPHelper;
import Sockets.ConnectionHandler;
import Sockets.SocketProcess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Faizan on 05/03/14.
 *
 */
public class SettingControlsController implements Initializable
{

    @FXML private Parent root;

    @FXML private ListView<SocketProcess> tabletList;

    @FXML private Label iNetAddressLabel;
    @FXML private Label startTimeLabel;
    @FXML private Label portNumberLabel;
    @FXML private Label connectionNameLabel;
    @FXML private Label connectionIdLabel;

    @FXML private Label myIPAddressLabel;

    @FXML private AnchorPane detailsPane;
    @FXML private Label noTabletSelectedLabel;

    private final ObservableList<SocketProcess> allConnections = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setupTabletList();

        this.tabletList.setItems(allConnections);
        refreshTabletList();
    }

    public void setupTabletList()
    {
        tabletList.setCellFactory(new Callback<ListView<SocketProcess>, ListCell<SocketProcess>>()
        {
            @Override
            public ListCell<SocketProcess> call(ListView<SocketProcess> p)
            {
                return new ListCell<SocketProcess>()
                {
                    @Override
                    protected void updateItem(SocketProcess connection, boolean aBool)
                    {
                        super.updateItem(connection, aBool);
                        if (connection != null)
                        {
                            Socket socket = connection.getSocket();
                            setText("" + socket.getInetAddress());
                        }
                    }

                };
            }
        });

        tabletList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SocketProcess>() {
            public void changed(ObservableValue<? extends SocketProcess> ov, SocketProcess old_val, SocketProcess new_val) {
                showConnectionDetails(new_val);
            }
        });
    }

    public void viewMyIP()
    {
        //server IP
        try
        {
            myIPAddressLabel.setText("My IP Address: " + IPHelper.getIP());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Unknown Host: " + e);
            myIPAddressLabel.setText("IP address could not be found.");
        }
    }
    
    public void disconnectTablet()
    {
        SocketProcess connection = tabletList.getSelectionModel().getSelectedItem();
        try
        {
            ConnectionHandler.closeConnection(connection);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        refreshTabletList();
    }

    public void disconnectAllTablets()
    {
        try
        {
            ConnectionHandler.closeAllConnections();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        refreshTabletList();
    }

    public void refreshTabletList()
    {
        this.allConnections.setAll(ConnectionHandler.getConnections());
        setRightPaneVisible(false);
        viewMyIP();
    }

    public void showConnectionDetails(SocketProcess connection)
    {
        if(connection != null)
        {
            setRightPaneVisible(true);
            Socket socket = connection.getSocket();
            iNetAddressLabel.setText("" + socket.getInetAddress());
            startTimeLabel.setText(connection.getStartTime().toString());
            portNumberLabel.setText("" + socket.getPort());
            connectionNameLabel.setText(connection.getName());
            connectionIdLabel.setText("" + connection.getId());
        }
        else
        {
            setRightPaneVisible(false);
        }
    }

    public void setRightPaneVisible(boolean visible)
    {
        detailsPane.setVisible(visible);
        noTabletSelectedLabel.setVisible(!visible);
    }
}