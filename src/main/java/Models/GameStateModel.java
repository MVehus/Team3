package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStateModel implements Serializable {

    private ArrayList<PlayerModel> playerModels;

    public GameStateModel(ArrayList<PlayerModel> playerModels){
        this.playerModels = playerModels;
    }

}
