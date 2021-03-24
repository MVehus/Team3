package network;

import Models.PlayerModel;
import Utilities.StartPositionUtility;
import com.badlogic.gdx.Gdx;
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
    private Boolean gameStarted;

    public Server(int port) {
        server = new com.esotericsoftware.kryonet.Server();
        NetworkUtilities.setUpKryo(server.getKryo());
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
                    players.get(connection.getID()-1).setNewPlayerState(updatedPlayerModel);
                    sendToAllClients(updatedPlayerModel);
                } else if (object instanceof Boolean) {
                    gameStarted = (Boolean) object;
                    sendToAllClients(object);
                }
            }

            public void connected(Connection connection) {
                if (!clients.contains(connection)) {
                    clients.add(connection);
                    sendId(connection);
                    players.add(new Player(connection.getID(), null, StartPositionUtility.getStartPosition(connection.getID() - 1)));
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

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void sendId(Connection connection) {
        try {
            connection.sendTCP(connection.getID());
        } catch (Exception e) {
            System.out.println("Could not send id to client with exception: \n" + e.toString());
        }
    }

    public void sendPlayerListToClients() {
        //Gdx.app.postRunnable(() -> sendToAllClients(players));
        sendToAllClients(players);
    }

    public void sendToAllClients(Object obj) {
        for (Connection client : clients) {
            try {
                client.sendTCP(obj);
            } catch (Exception e) {
                System.out.println("Could not send message: " + obj.toString() + " to client: " + client + "\n" + e.toString());
            }

        }
    }
}
