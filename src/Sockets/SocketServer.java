package Sockets;
import java.net.*;
import java.io.*;

/**
 * Created by NiklasBegley on 10/02/2014.
 *
 * Starts a socket server and creates new threads to handle requests as they come in.
 */
public class SocketServer implements Runnable
{

    private final int port;

    public SocketServer(int port)
    {
        this.port = port;
    }

    public void start()
    {
        try
        (
            ServerSocket serverSocket = new ServerSocket(this.port);
        )
        {
            boolean listening = true;

            while (listening)
            {
                // Starts new socket processes when a new connection comes in
                new SocketProcess(serverSocket.accept()).start();
                System.out.println("Received a new connection.");
                System.out.println("IP: " + serverSocket.getInetAddress());
                System.out.println("PORT: " + serverSocket.getLocalPort());
            }
        }
        catch (IOException e)
        {
            System.out.println("Problem, yo.");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        this.start();
    }
}
