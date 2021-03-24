package network;

import Models.GameStateModel;
import Models.PlayerModel;
import app.Game;
import com.badlogic.gdx.Gdx;
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
                        Gdx.app.postRunnable(() -> game.updatePlayer(updatedPlayerModel));
                    }

                } else if (object instanceof Integer) {
                    id = (Integer) object;
                    System.out.println("Connected to server with id: " + id);

                } else if (object instanceof ArrayList) {
                    if (game != null) {
                        //Gdx.app.postRunnable(() -> game.setPlayerList((ArrayList<Player>) object));
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
