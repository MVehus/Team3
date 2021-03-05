package network;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;

public class Network {

    private static Server server;

    public static void startServer(int port){
        server =  new Server(port);
    }

    public static Client makeNewClient(String IpAddress, int port){
        return new Client(IpAddress, port);
    }

    public static Server getServer(){
        return server;
    }

    public static ArrayList<Connection> getClients(){
        return server.getClients();
    }
}
