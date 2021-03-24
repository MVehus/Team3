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
                if (object instanceof GameStateModel) {
                    GameStateModel newGameState = (GameStateModel) object;
                    System.out.println("New GameState received from connection : " + connection);
                    sendToAllClients(newGameState);
                }
            }

            public void connected(Connection connection){
                if (!clients.contains(connection)) {
                    clients.add(connection);
                }
            }

            public void disconnected(Connection connection){
                clients.remove(connection);
            }
        });
    }

    public ArrayList<Connection> getClients() {
        return clients;
    }

    public void sendToAllClients(Object obj){
        for(Connection client : clients){
            try{
                client.sendTCP(obj);
            } catch (Exception e){
                System.out.println("Could not send message to client: " + client);
            }

        }
    }
}
