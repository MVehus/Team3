package player;

import Models.PlayerModel;
import app.Direction;
import com.badlogic.gdx.math.Vector2;
import projectCard.CardDeck;
import projectCard.ProgramCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Serializable {
    private final int id;
    private final String name;
    private Vector2 position;
    private Vector2 backupPosision;
    private Direction direction;
    public ArrayList<ProgramCard> cards = new ArrayList<ProgramCard>();
    public ArrayList<ProgramCard> programCards = new ArrayList<ProgramCard>(5);
    private int lifeTokens;
    private int damageTokens;
    private int flagsTaken;
    private boolean isAlive;
    private boolean powerDown;
    private boolean programCardDone;

    private Vector2 checkPointPosition;

    public Player(int id, String name, Vector2 position) {
        this.id = id;
        this.name = "Player: " + id;
        this.isAlive = true;
        this.position = position;
        this.backupPosision = new Vector2(position.x, position.y);
        this.checkPointPosition = position; // Checkpoint position as startposition until flag taken
        this.direction = Direction.RIGHT;
        this.lifeTokens = 3;
        this.damageTokens = 0;
        this.flagsTaken = 0;
        this.powerDown = false;
        this.programCardDone = false;
    }


    // PLAYERINFO

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setNewPlayerState(PlayerModel playerModel) {
        this.position = playerModel.getPosition();
        this.lifeTokens = playerModel.getLifeTokens();
        this.damageTokens = playerModel.getDamageTokens();
        this.flagsTaken = playerModel.getFlagsTaken();
        this.direction = playerModel.getDirection();
    }

    public PlayerModel getModel() {
        return new PlayerModel(id, position, lifeTokens, damageTokens, flagsTaken, direction, getCurrentCard());
    }

    public String toString() {
        return Integer.toString(getId());
    }

    // LIFE

    public boolean isAlive() {
        return isAlive;
    }

    public int getHealth() {
        return lifeTokens;
    }

    public void takeDamage() {
        damageTokens += 1;
        if (damageTokens == 10) {
            loseLifeToken();
        }
    }

    public int getNumDamageTokens() {
        return damageTokens;
    }

    public void loseLifeToken() {
        if (lifeTokens > 1) {
            lifeTokens -= 1;
            damageTokens = 0;
            System.out.println("Lost life");
            setPosition((int) backupPosision.x,(int) backupPosision.y);
        }
        else {
            lifeTokens -= 1;
            isAlive = false;
        }
    }

    // MOVEMENT AND POSITION

    public Vector2 getNextCell(){
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        Vector2 nextCell = new Vector2();
        switch (this.direction) {
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

    public void move() {
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        switch (this.direction) {
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

    public void moveDirection(Direction dir){
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        switch (dir) {
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

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public Vector2 getPosition() {
        if (lifeTokens == 0){
            return checkPointPosition;
        }
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public void markPosAsCheckpoint(){
        checkPointPosition = this.getPosition();
    }

    public Vector2 getCheckPointPosition(){
        return checkPointPosition;
    }

    public void reset() {
        if(lifeTokens <= 0){
            isAlive = true;
            position = checkPointPosition;
        }
    }

    public void rotate(Direction dir) {
        switch (dir) {
            case RIGHT:
                if (direction == Direction.UP)
                    setDirection(Direction.RIGHT);
                else if (direction == Direction.RIGHT)
                    setDirection(Direction.DOWN);
                else if (direction == Direction.DOWN)
                    setDirection(Direction.LEFT);
                else if (direction == Direction.LEFT)
                    setDirection(Direction.UP);

            case LEFT:
                if (direction == Direction.UP)
                    setDirection(Direction.LEFT);
                else if (direction == Direction.RIGHT)
                    setDirection(Direction.UP);
                else if (direction == Direction.DOWN)
                    setDirection(Direction.RIGHT);
                else if (direction == Direction.LEFT)
                    setDirection(Direction.DOWN);

        }
    }


    // GAMESTATUS

    public int getFlagScore() {
        return flagsTaken;
    }

    public void registerFlag() {
        flagsTaken++;
    }

    public boolean inPowerDown(){
        return powerDown;
    }

    public void setPowerDown() {
        powerDown = true;
    }

    public void powerDownComplete() {
        damageTokens = 0;
        powerDown = false;
    }

    // CARDS

    public ProgramCard getCurrentCard() {
        return cards.get(0);
    }

    public List<ProgramCard> getCards() {
        return cards;
    }

    public int numLockedProgramCards() {
        if (getNumDamageTokens() <= 9 && getNumDamageTokens() >= 5) {
            return getNumDamageTokens() - 4;
        }
        return 0;
    }

    public void setProgramCardDone(){
        programCardDone = true;
    }

    public void resetProgramCardDone(){
        programCardDone = false;
    }

    public boolean getProgramCardStatus() {
        return programCardDone;
    }

    public void selectCardForSlot(ProgramCard card, int slot) {
        programCards.add(slot, card);
        cards.remove(card);
    }

    public void addProgramCard(ProgramCard card) {
        cards.add(card);
    }

    public void removeCardFromSlot(ProgramCard card) {
        cards.add(card);
        programCards.remove(card);
    }

    public void clearAllSlots() {
        programCards.clear();
    }

    public void assignRandomCards() {
        Random rand = new Random();
        for (int i = 0; i < programCards.size()-numLockedProgramCards(); i++) {
            programCards.add(i, cards.remove(rand.nextInt(cards.size())));
        }
    }

















}
