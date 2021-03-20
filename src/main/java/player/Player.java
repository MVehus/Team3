package player;


import Models.PlayerModel;
import app.Direction;
import app.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import projectCard.ProgramCard;

import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Player {

    private int playerNr;
    private int lifeToken;
    private int damageToken;
    public ArrayList<ProgramCard> playerCards = new ArrayList<ProgramCard>();
    //private ArrayList<ProgramCard> program = new ArrayList<ProgramCard>();
    private String name;
    private String ip;
    private Vector2 position;
    private Direction direction;
    private int flagsTaken;
    private List<Tile> currentLayers;
    private boolean isAlive;

    private final TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerWonCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerDiedCell = new TiledMapTileLayer.Cell();

    public Player(String nameID, Vector2 position) {
        this.name = nameID;
        this.isAlive = true;
        this.position = position;
        this.direction = Direction.RIGHT;
        this.lifeToken = 3;
        this.damageToken = 0;
        this.flagsTaken = 0;
        this.currentLayers = null;

        // Load player textures
        Texture playerTexture = new Texture("src/assets/player.png");
        TextureRegion[][] textureRegion = TextureRegion.split(playerTexture, 300, 300);
        playerCell.setTile(new StaticTiledMapTile(textureRegion[0][0]));
        playerWonCell.setTile(new StaticTiledMapTile(textureRegion[0][2]));
        playerDiedCell.setTile(new StaticTiledMapTile(textureRegion[0][1]));
    }

    public boolean isAlive(){
        return isAlive;
    }

    public TiledMapTileLayer.Cell getPlayerState(){
        if(this.getFlagScore() == 3)
            return playerWonCell;
        else if(!isAlive)
            return playerDiedCell;
        else {
            return playerCell;
        }
    }
    public boolean canGo(Direction dir){
        return false;
    }

    public void move(Direction dir){
        int xPos = (int) position.x;
        int yPos = (int) position.y;
        switch (dir){
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

    public ProgramCard getCurrentCard(){
        // Gjøre dette på en annen måte? Queue?
        return playerCards.get(0);
    }

    public void setLayers(List<Tile> layers){
        currentLayers = layers;
    }

    public List<Tile> getLayers(){
        return currentLayers;
    }

    private void setPosition(int x, int y){
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
        isAlive = false;
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

    public String toString(){
        return "PlayerName: " + name + " on position (x: " + position.x + ", y: " + position.y + ") with " + lifeToken + " lifetokens and "
                + damageToken + " damage tokens. Has taken " + flagsTaken + " flags.";
    }


}
