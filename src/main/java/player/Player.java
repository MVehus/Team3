package player;


import projectCard.ProgramCard;

import java.util.ArrayList;

public class Player {

    private int playerNr;
    private int lifeToken;
    private int damageToken;
    public ArrayList<ProgramCard> playerCards = new ArrayList<ProgramCard>();
    //private ArrayList<ProgramCard> program = new ArrayList<ProgramCard>();
    private String name;
    private String ip;

    public Player(String name) {
        this.name = name;
        this.lifeToken = 3;
        this.damageToken = 0;

    }

    public int getHealth() {
        return lifeToken;
    }

    public String getName(){
        return name;
    }

    public void takeDamage() {
        damageToken += 1;
        if (damageToken == 10) {
            //reset position
            loseLifeToken();
            //check for more life tokens
        }
    }

    public int getNumDamageTokens() {
        return damageToken;
    }

    public void loseLifeToken() {
        lifeToken -= 1;
        if (lifeToken != 0) {
            resetPosition();
            damageToken = 0;
        }
    }

    public void drawProgramCards() {
        //TODO
    }


    public int numLockedProgramCards() {
            if(getNumDamageTokens() <= 9 && getNumDamageTokens() >= 5){
                return getNumDamageTokens() - 4;
            }

            return 0;
    }


    private void resetPosition() {
        //TODO
    }


}
