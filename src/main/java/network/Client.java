package network;

import Models.GameStateModel;
import app.Game;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

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
                if (object instanceof GameStateModel) {
                    GameStateModel response = (GameStateModel) object;
                    //TODO Handle incoming updated GameState
                } else if (object instanceof Integer){
                    id = (Integer) object;
                    System.out.println("Connected to server with id: " + id);
                }
            }
        });
    }

    public void disconnect(){
        client.close();
    }

    public Integer getId(){
        return id;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
