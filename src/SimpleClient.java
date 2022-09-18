import security.Authentication;
import logging.ConnectionLogger;
import service.QuoteGenerator;
import service.Receiver;
import service.Sender;

import java.io.IOException;
import java.net.Socket;

public class SimpleClient {

    private boolean isFree;
    private Socket socket;
    private  int countQuote = 0; // limit count quote

    Authentication auth = new Authentication();

    public boolean isFree() {
        return isFree;
    }

    public SimpleClient() {
        isFree = true;
        socket = null;
    }

    public void prepareConnectClient(Socket socket) throws Exception {
        if (!isFree) {
            throw new Exception("Not free SimpleClient");
        }
        this.socket = socket;
        isFree = false;

    }

    public void processClient() throws IOException {

        Sender sender = null;
        Receiver receiver = null;


        try {
            sender = new Sender(socket);
            receiver = new Receiver(socket);

            auth.authentication(sender, receiver);

            ConnectionLogger.LOGGER.info("client connected, port " + socket.getPort());

            sender.sendMsg("Enter 1 for quote or 2 for exit");

            while (true) {

                String msg = receiver.receiveMsg();

                if (msg.equalsIgnoreCase("1") && countQuote <= 3) {

                    countQuote++;
                    String quote = QuoteGenerator.getRandomQuote();
                    sender.sendMsg(quote);
                    ConnectionLogger.LOGGER.info("Quote for client " + socket.getPort() + " : " + quote);

                } else if (msg.equalsIgnoreCase("2") || countQuote > 3) {

                    if (msg.equalsIgnoreCase("2")) {
                        sender.sendMsg("Bye");

                    } else {
                        sender.sendMsg("Quote limit used up. You are disabled. Bye.");

                    }

                    ConnectionLogger.LOGGER.info("Disabling a client " + socket.getPort());
                    countQuote = 0;
                    break;

                } else {

                    sender.sendMsg("Invalid message");

                }
            }
        } catch (IOException e) {

            System.out.println("Something wrong during simpleClient " + e.getMessage());

        } finally {
            if (sender != null) {
                sender.close();
            }

            if (receiver != null) {
                receiver.close();
            }

            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

            // release of the performer
            socket = null;
            isFree = true;
        }

    }

}
