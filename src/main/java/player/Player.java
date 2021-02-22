package player;


import projectCard.Card;
import projectCard.Value;

import java.util.ArrayList;

public class Player {

    private int playerNr;
    private int lifeToken;
    private ArrayList<Card> cards;
    private int damageToken;

    public Player(){
        this.lifeToken = 3;
        this.damageToken = 0;
    }

    public int getHealth() {
        return lifeToken;
    }

    public void takeDamage() {
        damageToken += 1;
        if (damageToken == 9) {
            //reset position
            loseLifeToken();
            //check for more life tokens
            
        }
    }

    public int getDamageTokens() {
        return damageToken;
    }

    public void loseLifeToken() {
        lifeToken -= 1;
        if (lifeToken != 0) {
            resetPosition();
            damageToken = 0;
        }
    }

    private void resetPosition() {
        //TODO
    }
}
