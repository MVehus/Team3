package multiplayer;

import Utilities.IpChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<ServerSideConnection> clients;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Server IP: " + IpChecker.getIp() + "\nPort: " + port);
            System.out.println("Waiting for clients...");
            clients = new ArrayList<>();

            while (clients.size() < 8) {
                clientSocket = serverSocket.accept();
                ServerSideConnection ssc = new ServerSideConnection(clientSocket);
                clients.add(ssc);
                new Thread(ssc).start();
                System.out.println("Client connected");
            }
            System.out.println("Maximum number of players have joined the game. Not accepting more connections");

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void sendToAllClients(String str) {
        for (ServerSideConnection client : clients) {
            client.write(str);
        }
    }

    private class ServerSideConnection implements Runnable {

        private BufferedReader in;
        private PrintWriter out;
        Socket socket;

        public ServerSideConnection(Socket clientSocket) {
            socket = clientSocket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true) {
                    String inputLine;
                    if((inputLine = in.readLine()) != null){
                        System.out.println("Message: " + inputLine + " from " + clientSocket.toString());
                        //this.write(inputLine);
                        //Test if works.
                        for(ServerSideConnection client : clients){
                            write("Message received from user");
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        public void write(String str) {
            try {
                out.println(str);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }


    public static void main(String[] args) {
        Server server = new Server(32401);
    }
}
