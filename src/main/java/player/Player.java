package player;


import app.Direction;
import app.Tile;
import com.badlogic.gdx.math.Vector2;
import projectCard.CardDeck;
import projectCard.ProgramCard;
import projectCard.Value;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private Vector2 position;
    private Direction direction;
    public ArrayList<ProgramCard> programCards = new ArrayList<ProgramCard>();
    private int lifeTokens;
    private int damageTokens;
    private int flagsTaken;
    private List<Tile> currentLayers;
    private boolean isAlive;

    public Player(String nameID, Vector2 position) {
        this.name = nameID;
        this.isAlive = true;
        this.position = position;
        this.direction = Direction.RIGHT;
        this.lifeTokens = 3;
        this.damageTokens = 0;
        this.flagsTaken = 0;
        this.currentLayers = null;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public Vector2 getNextCell(){
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        Vector2 nextCell = new Vector2();
        switch (this.direction){
            case UP:
                nextCell.x = xPos;
                nextCell.y = yPos + 1;
                break;
            case DOWN:
                nextCell.x = xPos;
                nextCell.y = yPos - 1;
                break;
            case LEFT:
                nextCell.x = xPos - 1;
                nextCell.y = yPos;
                break;
            case RIGHT:
                nextCell.x = xPos + 1;
                nextCell.y = yPos;
                break;
        }

        return nextCell;
    }

    public void move(){
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        switch (this.direction){
            case UP:
                setPosition(xPos, yPos + 1);
                break;
            case DOWN:
                setPosition(xPos, yPos - 1);
                break;
            case LEFT:
                setPosition(xPos - 1, yPos);
                break;
            case RIGHT:
                setPosition(xPos + 1, yPos);
                break;
        }
    }

    public void setLayers(List<Tile> layers){
        currentLayers = layers;
    }

    public List<Tile> getLayers(){
        return currentLayers;
    }

    public void setPosition(int x, int y){
        position.x = x ;
        position.y = y ;

    }

    public Vector2 getPosition(){
        return position;
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction newDirection){
        this.direction = newDirection;
    }

    public void registerFlag(){
        flagsTaken++;
    }

    public int getFlagScore(){
        return flagsTaken;
    }

    public int getHealth() {
        return lifeTokens;
    }

    public String getName(){
        return name;
    }

    public void takeDamage() {
        damageTokens += 1;
        if (damageTokens == 10) {
            //reset position
            loseLifeToken();
            //check for more life tokens
        }
    }

    public int getNumDamageTokens() {
        return damageTokens;
    }

    public void loseLifeToken() {
        isAlive = false;
        lifeTokens -= 1;
        if (lifeTokens != 0) {
            resetPosition();
            damageTokens = 0;
        }
    }

    public void drawProgramCards(CardDeck deck) {
        int numCards = 9 - damageTokens;
        programCards = deck.drawCards(numCards);
    }

    public ProgramCard getCurrentCard(){
        // Gjøre dette på en annen måte? Queue?
        return programCards.get(0);
    }

    public List<ProgramCard> getProgramCards(){
        return programCards;
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

    public String toString(){
        return "Player: " + name + " on position (x: " + position.x + ", y: " + position.y + ") with " + lifeTokens + " lifetokens and "
                + damageTokens + " damage tokens. Has taken " + flagsTaken + " flags.";
    }

    public void rotate(Direction dir){
        switch (dir){
            case RIGHT:
                if(direction == Direction.UP)
                    setDirection(Direction.RIGHT);
                else if(direction == Direction.RIGHT)
                    setDirection(Direction.DOWN);
                else if(direction == Direction.DOWN)
                    setDirection(Direction.LEFT);
                else if(direction == Direction.LEFT)
                    setDirection(Direction.UP);

            case LEFT:
                if(direction == Direction.UP)
                    setDirection(Direction.LEFT);
                else if(direction == Direction.RIGHT)
                    setDirection(Direction.UP);
                else if(direction == Direction.DOWN)
                    setDirection(Direction.RIGHT);
                else if(direction == Direction.LEFT)
                    setDirection(Direction.DOWN);

        }
    }

}
