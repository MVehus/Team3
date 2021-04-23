package network;

import Models.PlayerModel;
import app.Game;
import com.esotericsoftware.kryonet.Connection;
import player.Player;
import projectCard.ProgramCard;

import java.util.ArrayList;

public class Network {

    private static Server server;
    private static Client client;

    //region START METHODS

    /**
     * Starts a server on its own thread.
     * @param port The port number for the server
     */
    public static void startServer(int port) {
        server = new Server(port);
    }

    /**
     * Initialized and starts a new client, and tries to connect it up to a server.
     * @param IpAddress Ip of the server the client will connect to
     * @param port The port of the server the client will connect to
     * @return True if connected to server successfully, false otherwise
     */
    public static boolean startClient(String IpAddress, int port) {
        try {
            client = new Client(IpAddress, port);
            return true;
        } catch (Exception e) {
            System.out.println("Could not connect new client to server with exception: \n" + e);
            return false;
        }
    }
    //endregion

    //region CLIENT METHODS

    /**
     * Sets a reference to the Game-class in the Client class, to make it possible to
     * communicate between Game instance running locally with the client.
     * @param game The locally running Game instance
     */
    public static void setGameReferenceForClient(Game game) {
        client.setGame(game);
    }

    /**
     * Method to determine whether or not you are hosting the server.
     * @return True if you are hosting the server, false otherwise
     */
    public static Boolean hostingServer() {
        return server != null;
    }

    /**
     * Method to determine whether or not the game has started.
     * @return True if the Game has been started by host, false otherwise
     */
    public static Boolean gameStarted() {
        return client.getGameStarted();
    }

    /**
     * Sends a updated player model to the server
     * @param playerModel The updated player model.
     */
    public static void sendUpdatedPlayerModel(PlayerModel playerModel){
        client.sendTCP(playerModel);
    }

    /**
     *
     * @return The id of the player
     */
    public static int getMyId() {
        return client.getId();
    }

    /**
     * Method to access the current hand of cards stored stored in the client, after
     * being given it by the server.
     * @return The current hand for the player
     */
    public static ArrayList<ProgramCard> getCurrentProgramCards() {
        while(true){
            if(!client.getCurrentHand().getCards().equals(new ArrayList<ProgramCard>()))
                break;
        }
        return client.getCurrentHand().getCards();
    }
    //endregion

    //region SERVER METHODS

    /**
     * Sends the list of the players in the game to all clients connected to the server.
     */
    public static void sendPlayerListToClients() {
        server.sendPlayerListToClients();
    }

    /**
     * Signals to all the clients that the game has now started.
     */
    public static void startGame(){
        server.startGame();
    }

    /**
     * Deals a new hand of cards to all the clients connected to the server.
     */
    public static void dealCardsToPlayers(){
        server.dealCardsToPlayers();
    }

    /**
     *
     * @return If hosting return a list of all the clients on the server else an empty list
     */
    public static ArrayList<Connection> getAllClientsOnServer() {
        return server != null ? server.getClients() : new ArrayList<>();
    }


    public static ArrayList<Player> getPlayersOnServer() {
        return server.getPlayers();
    }

    /**
     * Shuts down the server.
     */
    public static void shutdownServer(){
        server.shutdown();
    }
    //endregion
}
