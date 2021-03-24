package Models;

import app.Direction;
import com.badlogic.gdx.math.Vector2;
import projectCard.ProgramCard;

import java.io.Serializable;

public class PlayerModel implements Serializable {

    private int id;
    private Vector2 position;
    private int lifeTokens;
    private int damageTokens;
    private int flagsTaken;
    private Direction direction;
    private ProgramCard currentCard;

    public PlayerModel(int id, Vector2 position, int lifeTokens, int damageTokens, int flagsTaken, Direction direction, ProgramCard currentCard) {
        this.id = id;
        this.position = position;
        this.lifeTokens = lifeTokens;
        this.damageTokens = damageTokens;
        this.flagsTaken = flagsTaken;
        this.direction = direction;
        this.currentCard = currentCard;
    }

    public int getId() {
        return id;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getLifeTokens() {
        return lifeTokens;
    }

    public int getDamageTokens() {
        return damageTokens;
    }

    public int getFlagsTaken() {
        return flagsTaken;
    }

    public Direction getDirection() {
        return direction;
    }

    public ProgramCard getCurrentCard() {
        return currentCard;
    }

}
