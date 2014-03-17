package GUI.ConnectTablets;

import Sockets.ConnectionHandler;
import Sockets.SocketProcess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * Created by Faizan on 05/03/14.
 *
 */
public class SettingControlsController implements Initializable
{

    @FXML private ListView tabletList;
    @FXML private Button disconnectAll;
    @FXML private Hyperlink refresh;

    private final ObservableList<SocketProcess> allConnections = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.disconnectAll = new Button("Disconnect All");

        allConnections.setAll(ConnectionHandler.getConnections());

        this.tabletList.setItems(allConnections);

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
    }

    public void disconnectAllTablets()
    {
        System.out.println("Disconnecting tablets.");
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
        System.out.println(ConnectionHandler.getConnections().size());
        this.tabletList.getItems().clear();
        this.allConnections.setAll(ConnectionHandler.getConnections());
        this.tabletList.setItems(allConnections);
    }
}