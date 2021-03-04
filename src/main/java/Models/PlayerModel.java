package Models;

import projectCard.ProgramCard;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerModel implements Serializable {

    private int lifeTokens;
    private int damageTokens;
    private ArrayList<ProgramCard> programCards;
    private ProgramCard currentCard;

    public PlayerModel() {
        lifeTokens = 3;
        damageTokens = 0;
        programCards = null;
        currentCard = null;
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

    public ArrayList<ProgramCard> getProgramCards() {
        return programCards;
    }

    public void setProgramCards(ArrayList<ProgramCard> programCards) {
        this.programCards = programCards;
    }

    public ProgramCard getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(ProgramCard currentCard) {
        this.currentCard = currentCard;
    }
}
