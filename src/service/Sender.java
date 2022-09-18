package service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {

    private PrintWriter out;

    public Sender(Socket socket) throws IOException {
        out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                ),
                true
        );
    }

    public void close() {
        if(out != null){
            out.close();
            out = null;
        }

    }

    // sending messages
    public void sendMsg(String msg){
        if(out != null) {
            out.println(msg);

        }
    }
}
