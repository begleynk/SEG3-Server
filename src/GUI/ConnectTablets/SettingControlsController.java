package GUI.ConnectTablets;

import Helpers.IPHelper;
import ModelObjects.Patient;
import Sockets.ConnectionHandler;
import Sockets.SocketProcess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.rmi.UnknownHostException;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * Created by Faizan on 05/03/14.
 *
 */
public class SettingControlsController implements Initializable
{

    @FXML private ListView<SocketProcess> tabletList;

    @FXML private Label connectionData1;
    @FXML private Label connectionData2;
    @FXML private Label connectionData3;
    @FXML private Label connectionData4;
    @FXML private Label connectionData5;
    @FXML private Label myIPaddress;

    @FXML private AnchorPane detailsPane;
    @FXML private Label noTabletSelectedLabel;

    String myIP;
    private final ObservableList<SocketProcess> allConnections = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        //tablet connect
        allConnections.setAll(ConnectionHandler.getConnections());

        this.tabletList.setItems(allConnections);
        setRightPaneVisible(false);

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

        //view IP:
        viewMyIP();
    }

    public void viewMyIP()
    {
        //server IP
        try
        {

            myIPaddress.setText("My IP Address: " + IPHelper.getIP());

        }
        catch (Exception e)
        {

            e.printStackTrace();
            System.out.println("Unknown Host: " + e);
            myIPaddress.setText("IP address could not be found.");

        }

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

    public void refreshTabletList()
    {
        if(!tabletList.getItems().isEmpty())
        {
            tabletList.getItems().clear();
        }
        this.allConnections.setAll(ConnectionHandler.getConnections());
        this.tabletList.setItems(allConnections);
        setRightPaneVisible(false);
        viewMyIP();
    }

    public void showConnectionDetails(SocketProcess connection)
    {
        if(connection != null)
        {
            setRightPaneVisible(true);
            Socket socket = connection.getSocket();
            connectionData1.setText("" + socket.getInetAddress());
            connectionData2.setText(connection.getStartTime().toString());
            connectionData3.setText("" + socket.getPort());
            connectionData4.setText(connection.getName());
            connectionData5.setText("" + connection.getId());
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