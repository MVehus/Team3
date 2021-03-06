package network;

import Models.PlayerModel;
import app.Direction;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import projectCard.Hand;
import projectCard.ProgramCard;
import projectCard.Value;

import java.util.ArrayList;

public class NetworkUtilities {

    public static void setUpKryo(Kryo kryo){
        kryo.register(PlayerModel.class, new JavaSerializer());
        kryo.register(Integer.class, new JavaSerializer());
        kryo.register(ArrayList.class, new JavaSerializer());
        kryo.register(player.Player.class, new JavaSerializer());
        kryo.register(Direction.class, new JavaSerializer());
        kryo.register(Boolean.class, new JavaSerializer());
        kryo.register(ProgramCard.class, new JavaSerializer());
        kryo.register(String.class, new JavaSerializer());
        kryo.register(Value.class, new JavaSerializer());
        kryo.register(Hand.class, new JavaSerializer());
    }
}
