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
                System.out.println("Received " + object.toString() + " from " + connection);
                if (object instanceof PlayerModel) {
                    PlayerModel updatedPlayerModel = (PlayerModel) object;
                    System.out.println("Received updated PlayerModel from connection : " + connection);
                    players.get(connection.getID() - 1).setNewPlayerState(updatedPlayerModel);
                    sendToAllClients(updatedPlayerModel);
                } else if (object instanceof Boolean) {
                    gameStarted = (Boolean) object;
                    sendToAllClients(object);
                }
                /*
                else if (object instanceof String) {
                    if (isCardRequest(object)) {
                        String lastChar = ((String) object).substring(((String) object).length() - 1);
                        sendToClient(connection, cardDeck.drawCards(Integer.parseInt(lastChar)));
                    } else if (resetDeckRequest(object)) {
                        cardDeck = new CardDeck();
                    }


                }

                else {
                    System.out.println(object.toString() + " from " + connection + " not handled by server");
                }

                 */
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

    public ArrayList<Player> getPlayers() {
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

    public void sendToClient(Connection connection, Object obj) {
        try {
            connection.sendTCP(obj);
        } catch (Exception e) {
            System.out.println("Could not send message: " + obj.toString() + " to client: " + connection + "with exception:\n" + e.toString());
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
