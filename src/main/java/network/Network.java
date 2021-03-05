package network;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;

public class Network {

    private static Server server;
    private static Client client;

    public static void startServer(int port){
        server =  new Server(port);
    }

    public static void makeNewClient(String IpAddress, int port){
        client = new Client(IpAddress, port);
    }

    public static Client getClient() {
        return client;
    }

    public static Server getServer(){
        return server;
    }

    public static ArrayList<Connection> getAllClientsOnServer(){
        return server != null ? server.getClients() : null;
    }
}
