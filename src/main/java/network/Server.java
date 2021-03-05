package network;

import Models.GameStateModel;
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
                if (!clients.contains(connection)) {
                    clients.add(connection);
                }

                if (object instanceof GameStateModel) {
                    GameStateModel request = (GameStateModel) object;
                    System.out.println(request.toString());

                    //TODO Handle and notify all clients of updated GameState
                }

            }
        });
    }

    public ArrayList<Connection> getClients() {
        return clients;
    }

    public void sendToAllClients(Object obj){
        for(Connection client : clients){
            client.sendTCP(obj);
        }
    }
}
