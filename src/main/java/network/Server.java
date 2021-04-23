package network;

import Models.PlayerModel;
import Utilities.StartPositionUtility;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import player.Player;
import projectCard.CardDeck;
import projectCard.Hand;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.ArrayList;

public class Server {
    private final com.esotericsoftware.kryonet.Server server;
    private final ArrayList<Connection> clients;
    private final ArrayList<Player> players;
    private Boolean gameStarted;
    private CardDeck cardDeck;

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
        cardDeck = new CardDeck();

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerModel) {
                    PlayerModel updatedPlayerModel = (PlayerModel) object;
                    System.out.println("Server: Received updated PlayerModel from connection : " + connection);
                    players.get(connection.getID() - 1).setNewPlayerState(updatedPlayerModel);
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
                    players.add(new Player(connection.getID(), StartPositionUtility.getStartPosition(connection.getID() - 1)));
                }
            }

            public void disconnected(Connection connection) {
                clients.remove(connection);
                players.remove(players.get(connection.getID()-1));
            }
        });
    }

    public ArrayList<Connection> getClients() {
        return clients;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void sendId(Connection connection) {
        try {
            connection.sendTCP(connection.getID());
        } catch (Exception e) {
            System.out.println("Server: Could not send id to client with exception: \n" + e.toString());
        }
    }

    public void sendPlayerListToClients() {
        sendToAllClients(players);
    }

    public void sendToClient(Connection connection, Object obj) {
        try {
            connection.sendTCP(obj);
        } catch (Exception e) {
            System.out.println("Server: Could not send message: " + obj.toString() + " to client: " + connection + "with exception:\n" + e.toString());
        }
    }

    public void sendToAllClients(Object obj) {
        for (Connection client : clients) {
            sendToClient(client, obj);
        }
    }

    public void dealCardsToPlayers(){
        for (Player player : players) {
            sendToClient(clients.get(player.getId()-1), new Hand(cardDeck.drawCards(9-player.getNumDamageTokens())));
        }

        cardDeck = new CardDeck();
    }

    public void startGame(){
        sendToAllClients(Boolean.TRUE);
    }

    public void shutdown(){
        server.close();
    }

    private Boolean isCardRequest(Object obj) {
        if (((String) obj).substring(0, ((String) obj).length() - 1).equalsIgnoreCase("CARDREQUEST")) {
            String lastChar = ((String) obj).substring(((String) obj).length() - 1);
            try {
                Integer.parseInt(lastChar);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Cardrequest needs a specified number of cards at the end of the string" +
                        " Thrown with exception: " + e.toString());
                return false;
            }
        }
        return false;
    }

    private Boolean resetDeckRequest(Object obj) {
        return ((String) obj).equalsIgnoreCase("RESETDECK");
    }
}
