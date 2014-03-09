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
    private ArrayList<SocketProcess> socketProcesses;
    private ServerSocket serverSocket;
    private boolean listening;

    public SocketServer(int port) {
        this.port = port;
        this.socketProcesses = new ArrayList<SocketProcess>();
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.listening = true;

            while (listening) {
                // Starts new socket processes when a new connection comes in
                SocketProcess aProcess = new SocketProcess(serverSocket.accept());
                aProcess.start();
                socketProcesses.add(aProcess);
                System.out.println("Received a new connection.");
                System.out.println("IP: " + serverSocket.getInetAddress());
                System.out.println("PORT: " + serverSocket.getLocalPort());
            }
        } catch (IOException e) {
            System.out.println("Problem, yo.");
            System.out.println(e.getMessage());
            System.out.println(e.hashCode());
        }
    }

    @Override
    public void run() {
        this.start();
    }

    public void endProcesses() {
        for(SocketProcess aProcess : socketProcesses) {
            aProcess.endProcess();
        }
        socketProcesses.clear();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
