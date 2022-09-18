package security;

import service.Receiver;
import service.Sender;

import java.io.IOException;

public class Authentication {
    private boolean auth = false;

    private static String login = "user";
    private static String password = "pass123";

    public void authentication(Sender sender, Receiver receiver) throws IOException {

        while(!auth) {
            sender.sendMsg("Enter login");
            String loginMsg = receiver.receiveMsg();
            sender.sendMsg("Enter password");
            String passMsg = receiver.receiveMsg();

            if (loginMsg.equalsIgnoreCase(login) && passMsg.equalsIgnoreCase(password)) {
                sender.sendMsg("authorization passed");
                auth = true;
            } else {
                sender.sendMsg("authorization failed");
            }

        }
    }
}
