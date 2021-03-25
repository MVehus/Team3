package network;

import Models.PlayerModel;
import app.Direction;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import projectCard.ProgramCard;

import java.util.ArrayList;

public class NetworkUtilities {

    public static void setUpKryo(Kryo kryo){
        kryo.register(PlayerModel.class, new JavaSerializer());
        kryo.register(Integer.class, new JavaSerializer());
        kryo.register(ArrayList.class, new JavaSerializer());
        kryo.register(player.Player.class, new JavaSerializer());
        kryo.register(Direction.class, new JavaSerializer());
        kryo.register(Boolean.class);
        kryo.register(ProgramCard.class);
    }
}
