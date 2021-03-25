package network;

import Models.GameStateModel;
import Models.PlayerModel;
import app.Game;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Client {

    Game game;
    com.esotericsoftware.kryonet.Client client;
    private Integer id;

    public Client(String IpAddress, int port) {
        client = new com.esotericsoftware.kryonet.Client();
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
                    if (game != null) {
                        game.updatePlayer(updatedPlayerModel);
                    }

                } else if (object instanceof Integer) {
                    id = (Integer) object;
                    System.out.println("Connected to server with id: " + id);

                } else if (object instanceof ArrayList) {
                    if (game != null) {
                        game.setPlayerList((ArrayList<Player>) object);
                    }
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
            System.out.println("Could not send object to server with excpetion: \n" + e.toString());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
