package Sockets;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NiklasBegley on 10/03/2014.
 *
 * Keeps track of all open connections currently in the system and gives methods to interact with them.
 */
public class ConnectionHandler
{
    private static ArrayList<SocketProcess> connections = new ArrayList<SocketProcess>();

    public static ArrayList<SocketProcess> getConnections()
    {
        return connections;
    }

    public static void addConnection(SocketProcess process)
    {
        connections.add(process);
        process.start();

        System.out.println("Received a new connection.");
        System.out.println("NAME: " + process.getName());
        System.out.println("ID: " + process.getId());

        for(SocketProcess foo: connections)
        {
            System.out.println("***** NEW CONNECTION *****");
            System.out.println(foo.getId());
            System.out.println(foo.getState());
            System.out.println(foo.getName());
            System.out.println(foo.isAlive());
            System.out.println(foo.hashCode());
        }
    }

    public static boolean closeConnection(SocketProcess process) throws IOException
    {
        boolean closed = false;

        if(!connections.isEmpty())
        {
            for(SocketProcess proc: connections)
            {
                if(process.hashCode() == proc.hashCode())
                {
                    try
                    {
                        process.closeConnection();
                        connections.remove(proc);
                        closed = true;
                    }
                    catch (IOException e)
                    {
                        System.err.println("Error closing connection");
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
            if(closed)
            {
                return true;
            }
        }
        return false;
    }


    public static void closeAllConnections() throws IOException
    {
        for(SocketProcess process: connections)
        {
            try
            {
                process.closeConnection();
            }
            catch (IOException e)
            {
                System.err.println("Error closing connection");
                e.printStackTrace();
                throw e;
            }
        }
        connections.clear();
    }
}
