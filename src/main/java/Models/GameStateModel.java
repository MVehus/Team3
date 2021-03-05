package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStateModel implements Serializable {

    private ArrayList<PlayerModel> playerModels;

    public GameStateModel(ArrayList<PlayerModel> playerModels){
        this.playerModels = playerModels;
    }

    public ArrayList<PlayerModel> getPlayerModels() {
        return playerModels;
    }

    public PlayerModel getPlayer(int id){
        for(PlayerModel playerModel : playerModels){
            if(playerModel.getId() == id){
                return playerModel;
            }
        }
        return null;
    }
}
