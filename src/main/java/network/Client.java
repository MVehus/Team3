package network;

import Models.GameStateModel;
import Models.PlayerModel;
import app.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import player.Player;
import projectCard.Hand;
import projectCard.ProgramCard;

import javax.crypto.spec.PSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Client {

    private Game game;
    com.esotericsoftware.kryonet.Client client;
    private Integer id;
    private Boolean gameStarted = false;
    private Hand currentHand;

    public Client(String IpAddress, int port) {
        client = new com.esotericsoftware.kryonet.Client();
        NetworkUtilities.setUpKryo(client.getKryo());
        client.start();
        try {
            client.connect(5000, IpAddress, port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerModel) {
                    PlayerModel updatedPlayerModel = (PlayerModel) object;
                    System.out.println("Player " + connection.getID() + " updated");
                    if (game != null) {
                        game.updatePlayer(updatedPlayerModel);
                    } else {
                        System.out.println("Game is not initialized in Client class.");
                    }

                } else if (object instanceof Integer) {
                    id = (Integer) object;
                    //System.out.println("Connected to server with id: " + id);

                } else if (object instanceof ArrayList) {
                    if (game != null) {
                        game.setPlayerList((ArrayList<Player>) object);
                    } else {
                        System.out.println("Game is not initialized in Client class.");
                    }

                } else if (object instanceof Boolean) {
                    gameStarted = (Boolean) object;

                } else if (object instanceof Hand) {
                    currentHand = (Hand) object;
                }
            }
        });
    }

    public void disconnect() {
        client.close();
    }

    public void sendTCP(Object obj) {
        try {
            client.sendTCP(obj);
            System.out.println("Object: " + obj.toString() + " sent to server.");
        } catch (Exception e) {
            System.out.println("Could not send object to server with excpetion: \n" + e.toString());
        }
    }

    public Integer getId() {
        return id;
    }

    public Boolean getGameStarted() {
        return gameStarted;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Hand getCurrentHand() {
        return currentHand == null ? new Hand(new ArrayList<ProgramCard>()) : currentHand;
    }
}
