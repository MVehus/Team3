package player;


import board.Tile;
import projectCard.ProgramCard;

import java.util.ArrayList;

public class Player {

    private int playerNr;
    private int lifeToken;
    private int damageToken;
    public ArrayList<ProgramCard> playercards = new ArrayList<ProgramCard>();
    private ArrayList<ProgramCard> program = new ArrayList<ProgramCard>();
    private Tile backupPosition;
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

    public void drawPlayerCards () {
        //TODO
    }

    public int getLockedPositions() {
        if (getDamageTokens() >= 9) {
            return 5;
        }
        else if (getDamageTokens() >= 8) {
            return 4;
        }
        else if (getDamageTokens() >= 7) {
            return 3;
        }
        else if (getDamageTokens() >= 6) {
            return 2;
        }
        else if (getDamageTokens() >= 5) {
            return 1;
        }
        else {
            return 0;
        }
    }


    private void resetPosition() {
        //TODO
    }


}
