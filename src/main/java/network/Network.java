package network;

import app.Game;
import com.esotericsoftware.kryonet.Connection;
import player.Player;
import projectCard.ProgramCard;

import java.util.ArrayList;

public class Network {

    private static Server server;
    private static Client client;

    public static void startServer(int port) {
        server = new Server(port);
    }

    public static boolean makeNewClient(String IpAddress, int port) {
        try {
            client = new Client(IpAddress, port);
            return true;
        } catch (Exception e) {
            System.out.println("Could not connect new client to server with exception: \n" + e.toString());
            return false;
        }
    }

    public static void disconnectClient() {
        client.disconnect();
        client = null;
    }

    public static ArrayList<Connection> getAllClientsOnServer() {
        return server != null ? server.getClients() : new ArrayList<>();
    }

    public static ArrayList<Player> getPlayersOnServer() {
        return server.getPlayers();
    }

    public static void setGameReferenceForClient(Game game) {
        client.setGame(game);
    }

    public static void sendToServer(Object obj) {
        client.sendTCP(obj);
    }

    public static Boolean hostingServer() {
        return server != null;
    }

    public static void sendPlayerListToClients() {
        server.sendPlayerListToClients();
    }

    public static Boolean gameStarted() {
        return client.getGameStarted() != null;
    }

    public static int getMyId() {
        return client.getId();
    }

    public static void dealCardsToPlayers(){
        server.dealCardsToPlayers();
    }

    public static ArrayList<ProgramCard> getCurrentProgramCards() {
        while(true){
            if(!client.getCurrentHand().getCards().equals(new ArrayList<ProgramCard>()))
                break;
        }
        return client.getCurrentHand().getCards();
    }

    /*
    public static Client getClient() {
        return client;
    }
     public static Server getServer() {
        return server;
    }
     */
}
