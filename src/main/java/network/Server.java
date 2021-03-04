package network;

import Utilities.IpChecker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<ServerSideConnection> clients;
    private LinkedBlockingQueue<Object> messages;
    private boolean running;

    public Server(int port) {
        // Sets up the server and start a server socket.
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Server IP: " + IpChecker.getIp() + "\nPort: " + port);
            System.out.println("Waiting for clients...");
            running = true;
            clients = new ArrayList<>();
            messages = new LinkedBlockingQueue<>();

            //Handles incoming messages from clients
            new Thread(() -> {
                while (running) {
                    if (!messages.isEmpty()) {
                        try {
                            sendToAllClients(messages.take());
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                }
            }).start();

            // Accepts incoming connections up to 8 players and starts up a new thread with their ServerSideConnection.
            while (clients.size() < 8 && running) {
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

    /**
     * Sends a Object to all clients connected to the server.
     *
     * @param obj The Object to be sent
     */
    public void sendToAllClients(Object obj) {
        for (ServerSideConnection client : clients) {
            client.write(obj);
        }
    }

    public int getNumClients(){
        return clients.size();
    }

    public void shutdown(){
        sendToAllClients("Server shutdown");
        // Shuts down each client connection socket.
        for(ServerSideConnection client : clients){
            try {
                client.socket.close();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        // Shuts down server socket
        try {
            serverSocket.close();
        } catch (Exception e){
            System.out.println(e.toString());
        }
        running = false;
        System.exit(0);
    }


    private class ServerSideConnection implements Runnable {

        Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ServerSideConnection(Socket clientSocket) {
            socket = clientSocket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Object inputObject;
                    if ((inputObject = in.readObject()) != null) {
                        System.out.println("Object: " + inputObject.toString() + " from " + clientSocket.toString());
                        messages.put(inputObject);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        /**
         * Writes a message to the client connected by this ServerSideConnection
         *
         * @param obj The Object to be sent
         */
        public void write(Object obj) {
            try {
                out.writeObject(obj);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }


    public static void main(String[] args) {
        Server server = new Server(32401);
    }
}
