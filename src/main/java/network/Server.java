package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.ArrayList;

public class Server {
    private com.esotericsoftware.kryonet.Server server;
    private ArrayList<Connection> clients;

    public Server(int port) {
        server = new com.esotericsoftware.kryonet.Server();
        server.start();
        try {
            server.bind(port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        clients = new ArrayList<>();
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (!clients.contains(connection))
                    clients.add(connection);

                if (object instanceof String) {
                    String request = (String) object;
                    System.out.println(request);

                    String response = "response";
                    connection.sendTCP(response);
                }
            }
        });
    }

    public ArrayList<Connection> getClients() {
        return clients;
    }
}
