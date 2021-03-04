package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public Client(String IPAddress, int port) {
        // Tries to connect the client to the server.
        try {
            socket = new Socket(IPAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected to server");

            //Incoming message handler
            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(in.readLine());
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }).start();

            // Checks for keyboard input and sends to server
            while (true) {
                String userInput = stdIn.readLine();
                out.println(userInput);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("92.221.197.177", 32401);
    }
}
