import Sockets.SocketServer;

/**
 * Created by Me on 04/02/2014.
 */
public class FooMar {
    public static void main(String[] args) {
        System.out.println("Enesay");

        SocketServer server = new SocketServer(4000);
        server.start();
    }
}