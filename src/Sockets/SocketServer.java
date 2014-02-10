package Sockets;
import java.net.*;
import java.io.*;

/**
 * Created by NiklasBegley on 10/02/2014.
 */
public class SocketServer
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
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        )
        {

            // Initiate conversation with client
            while (true)
            {
                if (in.readLine() != null)
                {
                    out.println("I'm echoing: " + in.readLine());
                    out.println("foo");
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Problem, yo.");
            System.out.println(e.getMessage());
        }
    }

}
