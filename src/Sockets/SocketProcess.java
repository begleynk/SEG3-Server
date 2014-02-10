package Sockets;
import java.net.*;
import java.io.*;

/**
 * Created by NiklasBegley on 10/02/2014.
 *
 * Each connection coming into the server is given a separate SocketProcess thread which handles the requests.
 */
public class SocketProcess extends Thread {

    private Socket socket;

    public SocketProcess(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try(
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        )
        {
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null)
            {
                outputLine = SocketAPI.getResponseFor(inputLine);
                out.println(outputLine);

                if (outputLine.equals("Close"))
                    break;
            }
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
