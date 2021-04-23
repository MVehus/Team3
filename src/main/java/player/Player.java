package player;

import Models.PlayerModel;
import app.Direction;
import com.badlogic.gdx.math.Vector2;
import projectCard.ProgramCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private final int id;
    private final String name;
    private Vector2 position;
    private final Vector2 backUpPosition;
    private Direction direction;
    public ArrayList<ProgramCard> cards = new ArrayList<>();
    public final ArrayList<ProgramCard> programCards = new ArrayList<>(5);
    private int lifeTokens;
    private int damageTokens;
    private int flagsTaken;
    private boolean isAlive;
    private Vector2 checkPointPosition;

    /**
     * Construct a new player object, initialize fields .
     * @param id - player ID
     * @param position - start position
     */
    public Player(int id, Vector2 position) {
        this.id = id;
        this.name = "Player: " + id;
        this.isAlive = true;
        this.position = position;
        this.backUpPosition = new Vector2(position.x, position.y);
        this.checkPointPosition = position; // Checkpoint position as start position until flag taken
        this.direction = Direction.RIGHT;
        this.lifeTokens = 3;
        this.damageTokens = 0;
        this.flagsTaken = 0;
    }

    /**
     * @return - players ID
     */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //region MOVEMENT & ROTATION

    /**
     * Move player according to his direction
     */
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

    /**
     * BackUp movement, move opposite direction.
     */
    public void backUp(){
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        switch (this.direction) {
            case UP:
                setPosition(xPos, yPos - 1);
                break;
            case DOWN:
                setPosition(xPos, yPos + 1);
                break;
            case LEFT:
                setPosition(xPos + 1, yPos);
                break;
            case RIGHT:
                setPosition(xPos - 1, yPos);
                break;
        }

    }

    /**
     * Move player in direction, used in force movements (as push)
     * @param dir - direction movement
     */
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

    /**
     * Rotate player 90 degrees
     * @param dir - direction
     */
    public void rotate(Direction dir) {

        if(dir == Direction.RIGHT){
            if (direction == Direction.UP)
                setDirection(Direction.RIGHT);
            else if (direction == Direction.RIGHT)
                setDirection(Direction.DOWN);
            else if (direction == Direction.DOWN)
                setDirection(Direction.LEFT);
            else if (direction == Direction.LEFT)
                setDirection(Direction.UP);
        } else if (dir == Direction.LEFT){
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


    //endregion

    //region NETWORK
    /**
     * Sets/Updates the field values of the player object according to given new player model
     * @param playerModel New player model
     */
    public void setNewPlayerState(PlayerModel playerModel) {
        this.position = playerModel.getPosition();
        this.lifeTokens = playerModel.getLifeTokens();
        this.damageTokens = playerModel.getDamageTokens();
        this.flagsTaken = playerModel.getFlagsTaken();
        this.direction = playerModel.getDirection();
    }

    /**
     * Return a PlayerModel object with the field variable values of the this player object.
     * @return PlayerModel object of this player
     */
    public PlayerModel getModel() {
        return new PlayerModel(id, position, lifeTokens, damageTokens, flagsTaken, direction, getCurrentCard());
    }

    /**
     * To string, identifying the player with an id
     * @return The id as a string
     */
    public String toString() {
        return Integer.toString(getId());
    }
    //endregion

    //region CARD METHODS

    /**
     *
     * @return The first card in the hand of the player.
     */
    public ProgramCard getCurrentCard() {
        if (programCards.size() == 0)
            return null;
        return programCards.get(0);
    }

    /**
     * Removes the current card
     * @return The removed card
     */
    public ProgramCard useCurrentCard() {
        return programCards.remove(0);
    }

    /**
     *
     * @return all (9-1) cards dealt during a round
     */
    public List<ProgramCard> getCards() {
        return cards;
    }

    /**
     *
     * @return The number og locked program cards based on how much damage is taken.
     */
    public int numLockedProgramCards() {
        if (getNumDamageTokens() <= 9 && getNumDamageTokens() >= 5) {
            return getNumDamageTokens() - 4;
        }
        return 0;
    }

    /**
     * Adds a card to the 5 selected program cards to run in a round.
     * @param card The card to be added.
     */
    public void addProgramCard(ProgramCard card) {
        programCards.add(card);
    }

    //endregion

    /**
     *
     * @return True if the player is alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     *
     * @return Number of life tokens.
     */
    public int getHealth() {
        return lifeTokens;
    }

    /**
     * Increments the number of damage tokens taken, and if it reaches 9, you loose a life token
     */
    public void takeDamage() {
        damageTokens += 1;
        if (damageTokens == 10) {
            loseLifeToken();
        }
    }

    /**
     *
     * @return - num damageTokens
     */
    public int getNumDamageTokens() {
        return damageTokens;
    }

    /**
     * Loose a life token
     */
    public void loseLifeToken() {
        if (lifeTokens > 1) {
            lifeTokens -= 1;
            damageTokens = 0;
            System.out.println("Lost life");
            setPosition((int) backUpPosition.x,(int) backUpPosition.y);
        }
        else {
            lifeTokens -= 1;
            isAlive = false;
        }
    }

    /**
     *
     * @param forwards - true if forwards, false if backwards
     * @return - next cell according to direction.
     */
    public Vector2 getNextCell(boolean forwards){
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        Vector2 nextCell = new Vector2();
        if(forwards){
            // RETURN NEXT CELL
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
        } else {
            // RETURN LAST CELL (USED FOR BACKUP MOVEMENT)
            switch (this.direction) {
                case UP:
                    nextCell.x = xPos;
                    nextCell.y = yPos - 1;
                    break;
                case DOWN:
                    nextCell.x = xPos;
                    nextCell.y = yPos + 1;
                    break;
                case LEFT:
                    nextCell.x = xPos + 1;
                    nextCell.y = yPos;
                    break;
                case RIGHT:
                    nextCell.x = xPos - 1;
                    nextCell.y = yPos;
                    break;
            }
        }

        return nextCell;
    }

    /**
     * Update position
     * @param x - xPos
     * @param y - yPos
     */
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

    /**
     * Set new direction
     * @param newDirection - new direction
     */
    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    /**
     * Mark checkpoint (flags)
     */
    public void markPosAsCheckpoint(){
        checkPointPosition = this.getPosition();
    }

    public Vector2 getCheckPointPosition(){
        return checkPointPosition;
    }

    /**
     * Reset player, change position and reset health status
     */
    public void reset() {
        if(lifeTokens <= 0){
            isAlive = true;
            position = checkPointPosition;
        }
    }

    public int getFlagScore() {
        return flagsTaken;
    }

    /**
     * Capture flag
     */
    public void registerFlag() {
        flagsTaken++;
    }


}
