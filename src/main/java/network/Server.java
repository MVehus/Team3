package network;

import Models.GameStateModel;
import Models.PlayerModel;
import app.Tile;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private com.esotericsoftware.kryonet.Server server;
    private ArrayList<Connection> clients;
    private HashMap<Integer, Connection> clientIdTable;
    private GameStateModel currentGameState;

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
                    if (!newGameState.equals(currentGameState)) {
                        currentGameState = newGameState;
                        sendToAllClients(newGameState, connection);
                    }
                }
            }

            public void connected(Connection connection) {
                if (!clients.contains(connection)) {
                    clients.add(connection);
                    sendId(connection);
                }
                if (!clientIdTable.containsValue(connection)) {
                    clientIdTable.put(connection.getID(), connection);
                }
            }

            public void disconnected(Connection connection) {
                clients.remove(connection);
            }
        });
    }

    public ArrayList<Connection> getClients() {
        return clients;
    }

    public HashMap<Integer, Connection> getClientIdTable() {
        return clientIdTable;
    }

    public void sendId(Connection connection){
        try {
            server.sendToTCP(connection.getID(), connection.getID());
        } catch (Exception e){
            System.out.println("Could not send id to client with exception: \n" + e.toString());
        }
    }

    public void sendToAllClients(Object obj, Connection sender) {
        for (Connection client : clients) {
            try {
                if (client != sender) {
                    client.sendTCP(obj);
                }
            } catch (Exception e) {
                System.out.println("Could not send message to client: " + client);
            }

        }
    }
}
