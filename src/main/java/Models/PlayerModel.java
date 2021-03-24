package Models;

import com.badlogic.gdx.math.Vector2;
import projectCard.ProgramCard;

import java.io.Serializable;

public class PlayerModel implements Serializable {

    private int id;
    private Vector2 position;
    private int lifeTokens;
    private int damageTokens;
    private ProgramCard currentCard;

    public PlayerModel(int id, Vector2 position, int lifeTokens, int damageTokens, ProgramCard currentCard) {
        this.id = id;
        this.position = position;
        this.lifeTokens = lifeTokens;
        this.damageTokens = damageTokens;
        this.currentCard = currentCard;
    }

    public int getId() {
        return id;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getLifeTokens() {
        return lifeTokens;
    }

    public void setLifeTokens(int lifeTokens) {
        this.lifeTokens = lifeTokens;
    }

    public int getDamageTokens() {
        return damageTokens;
    }

    public void setDamageTokens(int damageTokens) {
        this.damageTokens = damageTokens;
    }

    public ProgramCard getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(ProgramCard currentCard) {
        this.currentCard = currentCard;
    }
}
