
import java.io.IOException;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) throws IOException {

        LogManager.getLogManager().readConfiguration();   // start logger

        Server server = new Server(1024,5);     // create new server
        server.runServer();                               // run server

    }
}
