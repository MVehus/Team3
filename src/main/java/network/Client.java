package network;

import Models.PlayerModel;
import app.Game;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import player.Player;
import projectCard.Hand;
import projectCard.ProgramCard;

import java.io.IOException;
import java.util.ArrayList;

public class Client {

    private Game game;
    com.esotericsoftware.kryonet.Client client;
    private Integer id;
    private Boolean gameStarted = false;
    private Hand currentHand;

    public Client(String IpAddress, int port) throws IOException {
        client = new com.esotericsoftware.kryonet.Client();
        NetworkUtilities.setUpKryo(client.getKryo());
        client.start();
        client.connect(5000, IpAddress, port);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerModel) {
                    PlayerModel updatedPlayerModel = (PlayerModel) object;
                    System.out.println("Client: Player " + updatedPlayerModel.getId() + " updated");
                    System.out.println();
                    game.updatePlayer(updatedPlayerModel);

                } else if (object instanceof Integer) {
                    id = (Integer) object;

                } else if (object instanceof ArrayList) {
                    game.setPlayerList((ArrayList<Player>) object);

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
        } catch (Exception e) {
            System.out.println("Client: Could not send object to server with excpetion: \n" + e.toString());
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
