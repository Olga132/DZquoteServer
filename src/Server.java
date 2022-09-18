import service.Sender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private int limit;          // limit connect
    private int port;           // number port
    private SimpleClient[] simpleClients;
    ExecutorService threadPool = null;

    protected Server() {
        port = 0;
        limit = 0;
    }

    public Server(int port, int limit) {
        this.port = port;
        this.limit = limit + 1;

        threadPool = Executors.newFixedThreadPool(limit);
        simpleClients = new SimpleClient[limit];

        for (int i = 0; i < simpleClients.length; i++) {
            simpleClients[i] = new SimpleClient();
        }

    }

    // start server
    public void runServer() throws IOException {
        ServerSocket server = null;
        try {

            System.out.println("Starting server ");
            server = new ServerSocket(port, limit);

            // cycle work server
            while (true) {

                Socket client = server.accept();   // connect new client

                SimpleClient simpleClient = getSimpleClient();
                if (simpleClient != null) {

                    simpleClient.prepareConnectClient(client);

                    threadPool.execute(() -> {
                        try {
                            simpleClient.processClient();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                } else {
                    Sender sender = new Sender(client);
                    sender.sendMsg("Connection limit exceeded. Try connecting later.");
                    client.close();
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Server exception: " + e);
        } finally {
            if (server != null && !server.isClosed()) {
                server.close();
            }
        }
    }

    // get free performer
    private SimpleClient getSimpleClient() {
        for (SimpleClient sc : simpleClients) {
            if (sc.isFree()) {
                return sc;
            }
        }
        return null;
    }

}
