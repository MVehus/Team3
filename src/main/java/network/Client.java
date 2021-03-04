package network;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public Client(String IPAddress, int port) {
        // Tries to connect the client to the server.
        try {
            socket = new Socket(IPAddress, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");

            //Incoming message handler
            new Thread(() -> {
                while (true) {
                    try {
                        //TODO Handle new GameState when one is received.
                        System.out.println(in.readObject().toString());
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }).start();

            // Sends updated GameState to Server when a move is made.
            while (true) {
                //TODO Sends out updated GameState when a move is made.
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("92.221.197.177", 32401);
    }
}
