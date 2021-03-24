package network;

import Models.GameStateModel;
import Models.PlayerModel;
import app.Tile;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private com.esotericsoftware.kryonet.Server server;
    private ArrayList<Connection> clients;
    private HashMap<Integer, Connection> clientIdTable;
    private ArrayList<Player> players;

    public Server(int port) {
        server = new com.esotericsoftware.kryonet.Server();
        server.start();
        try {
            server.bind(port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        clients = new ArrayList<>();
        players = new ArrayList<>();
        clientIdTable = new HashMap<>();

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerModel) {
                    PlayerModel updatedPlayerModel = (PlayerModel) object;
                    System.out.println("Received updated PlayerModel from connection : " + connection);
                    sendToAllClients(updatedPlayerModel, connection);
                }
            }

            public void connected(Connection connection) {
                if (!clients.contains(connection)) {
                    clients.add(connection);
                    sendId(connection);
                    players.add(new Player(connection.getID(), null, null));
                }

                sendToAllClients(players, null);
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

    public void sendPlayerListToClients() {
        sendToAllClients(players, null);
    }

    public void sendToAllClients(Object obj, Connection sender) {
        for (Connection client : clients) {
            try {
                if (client != sender) {
                    server.sendToTCP(client.getID(), obj);
                }
            } catch (Exception e) {
                System.out.println("Could not send message to client: " + client);
            }

        }
    }
}
