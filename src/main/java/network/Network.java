package network;

import app.Game;
import com.esotericsoftware.kryonet.Connection;
import player.Player;
import projectCard.ProgramCard;

import java.util.ArrayList;

public class Network {

    private static Server server;
    private static Client client;

    //region START METHODS
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
    //endregion

    //region CLIENT METHODS
    public static void disconnectClient() {
        client.disconnect();
        client = null;
    }

    public static void setGameReferenceForClient(Game game) {
        client.setGame(game);
    }

    public static Boolean hostingServer() {
        return server != null;
    }

    public static Boolean gameStarted() {
        return client.getGameStarted();
    }

    public static int getMyId() {
        return client.getId();
    }

    public static ArrayList<ProgramCard> getCurrentProgramCards() {
        while(true){
            if(!client.getCurrentHand().getCards().equals(new ArrayList<ProgramCard>()))
                break;
        }
        return client.getCurrentHand().getCards();
    }
    //endregion

    //region SERVER METHODS
    public static void sendPlayerListToClients() {
        server.sendPlayerListToClients();
    }

    public static void startGame(){
        server.startGame();
    }

    public static void dealCardsToPlayers(){
        server.dealCardsToPlayers();
    }

    public static ArrayList<Connection> getAllClientsOnServer() {
        return server != null ? server.getClients() : new ArrayList<>();
    }

    public static ArrayList<Player> getPlayersOnServer() {
        return server.getPlayers();
    }
    //endregion
}
