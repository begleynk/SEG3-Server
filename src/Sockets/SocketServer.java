package Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Niklas Begley on 10/02/2014.
 *
 * Starts a socket server and creates new threads to handle requests as they come in.
 *
 */
public class SocketServer implements Runnable {

    private final int port;
    private ServerSocket serverSocket;
    private boolean listening;

    public SocketServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.listening = true;

            while (listening) {
                // Starts new socket processes when a new connection comes in
                SocketProcess aProcess = new SocketProcess(serverSocket.accept());
                ConnectionHandler.addConnection(aProcess);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        this.start();
    }

    public void stopListening() {
        this.listening = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
