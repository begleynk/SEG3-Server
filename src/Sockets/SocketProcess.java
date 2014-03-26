package Sockets;
import java.net.*;
import java.io.*;
import java.util.Date;

/**
 * Created by Niklas Begley on 10/02/2014.
 *
 * Each connection coming into the server is given a separate SocketProcess thread which handles the requests.
 *
 */
public class SocketProcess extends Thread {

    private Socket socket;
    private Date startTime;

    public SocketProcess(Socket socket)
    {
        this.socket = socket;
        this.startTime = new Date();
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void run()
    {
        try
        (
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))
        )
        {
            String inputLine, outputLine, message;
            while(true)
            {
                while (!(inputLine = in.readLine()).endsWith("END")) { }

                message = inputLine.substring(0, inputLine.length() - "END".length()); // Remove END

                outputLine = SocketAPI.getResponseFor(message);
                out.println(outputLine);

                if (outputLine.equals("Close"))
                {
                    break;
                }
            }
            ConnectionHandler.closeConnection(this);
        }
        catch (SocketException e)
        {
            System.out.println("Closed socket " + socket.getInetAddress());
        }
        catch (NullPointerException e)
        {
            // Closes the connection if the tablet is unable to close the connection properly.
            try
            {
                ConnectionHandler.closeConnection(this);
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws IOException
    {
        try
        {
            this.socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Error closing connection");
            e.printStackTrace();
            throw e;
        }
    }

}
