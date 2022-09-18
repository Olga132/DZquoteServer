package service;

import java.io.*;
import java.net.Socket;

public class Receiver {
    private BufferedReader in;

    public Receiver(Socket socket) throws IOException {
        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );
    }

    public void close() throws IOException {

        if(in != null){
            in.close();
            in = null;
        }

    }

    // receiving massages
    public String receiveMsg() throws IOException {
        if(in != null) {
            String msg = in.readLine();
            return msg;
        }
        return "";
    }
}