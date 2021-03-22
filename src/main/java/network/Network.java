package network;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;

public class Network {

    private static Server server;
    private static Client client;

    public static void startServer(int port) {
        server = new Server(port);
    }

    public static boolean makeNewClient(String IpAddress, int port) {
        try {
            client = new Client(IpAddress, port);
            return true;
        } catch (Exception e) {
            System.out.println("Could not connect new client to server with exception: \n" + e.toString());
            return false;
        }
    }

    public static void disconnectClient() {
        client.disconnect();
        client = null;
    }

    public static ArrayList<Connection> getAllClientsOnServer() {
        return server.getClients();
    }

    /*
    public static Client getClient() {
        return client;
    }
     public static Server getServer() {
        return server;
    }
     */
}
