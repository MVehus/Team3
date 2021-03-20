package app;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import org.lwjgl.system.CallbackI;
import player.Player;
import projectCard.ProgramCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    // LAYERS
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;
    private Board gameBoard;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private final TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerWonCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerDiedCell = new TiledMapTileLayer.Cell();


    private List<Player> players = new ArrayList<>();
    //private Vector2 playerPos;

    private int boardWidth;
    private int boardHeight;

    @Override
    public boolean keyUp(int keycode) {
        Player currentPlayer = players.get(0);
        Vector2 playerPos = currentPlayer.getPosition();

        playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);

        if (keycode == Input.Keys.UP) {
            if (moveHasWall(playerPos.x, playerPos.y, playerPos.x, playerPos.y+1)) {
                System.out.println("Wall");
                //Do nothing
            }
            else if (playerPos.y == boardHeight-1) {
                System.out.println("Test");
                currentPlayer.loseLifeToken();
            }
            else {
                System.out.println("Move");
                playerPos.y = (playerPos.y == boardHeight -1) ? boardHeight -1 : playerPos.y+1;
            }
        }

        else if (keycode == Input.Keys.DOWN) {
            if (moveHasWall(playerPos.x, playerPos.y, playerPos.x, playerPos.y-1)) {
                System.out.println("Wall");
                //Do nothing
            }
            else if (playerPos.y == 0) {
                System.out.println("Test");
                currentPlayer.loseLifeToken();
            }
            else {
                System.out.println("Move");
                playerPos.y = (playerPos.y == 0) ? 0 : playerPos.y - 1;
            }
        }

        else if (keycode == Input.Keys.LEFT) {
            if (moveHasWall(playerPos.x, playerPos.y, playerPos.x-1, playerPos.y)) {
                System.out.println("Wall");
                //Do nothing
            }
            else if (playerPos.x == 0) {
                System.out.println("Test");
                currentPlayer.loseLifeToken();
            }
            else {
                System.out.println("Move");
                playerPos.x = (playerPos.x == 0) ? 0 : playerPos.x-1;
            }

        }
        else if (keycode == Input.Keys.RIGHT) {
            if (moveHasWall(playerPos.x, playerPos.y, playerPos.x+1, playerPos.y)) {
                System.out.println("Wall");
                //Do nothing
            }
            else if (playerPos.x == boardWidth-1) {
                System.out.println("Test");
                currentPlayer.loseLifeToken();
            }
            else {
                System.out.println("Move");
                playerPos.x = (playerPos.x == boardWidth -1) ? boardWidth -1 : playerPos.x+1;
            }
        }
        System.out.println(currentPlayer.getName() + " at " + gameBoard.getTilesOnCell(playerPos.x, playerPos.y));
        checkForHole(currentPlayer);
        return super.keyUp(keycode);
    }

    private boolean moveHasWall(float x, float y, float x1, float y1) {
        List<Tile> currentTile = gameBoard.getTilesOnCell(x, y);
        List<Tile> newTile     = gameBoard.getTilesOnCell(x1, y1);

        if (y < y1) {
            if (currentTile.contains(Tile.WallTop) || currentTile.contains(Tile.WallTopRight) || currentTile.contains(Tile.WallTopLeft) ||
                newTile.contains(Tile.WallBottom)  || newTile.contains(Tile.WallBottomRight)  || newTile.contains(Tile.WallBottomLeft) ||
                currentTile.contains(Tile.PushWallTop) || newTile.contains(Tile.PushWallBottom))
                return true;
            return false;
        }
        else if (y > y1) {
            if (currentTile.contains(Tile.WallBottom) || currentTile.contains(Tile.WallBottomRight) || currentTile.contains(Tile.WallBottomLeft) ||
                newTile.contains(Tile.WallTop)  || newTile.contains(Tile.WallTopRight)  || newTile.contains(Tile.WallTopLeft) ||
                currentTile.contains(Tile.PushWallBottom) || newTile.contains(Tile.PushWallTop))
                return true;
            return false;
        }
        else if (x < x1) {
            if (currentTile.contains(Tile.WallRight) || currentTile.contains(Tile.WallTopRight) || currentTile.contains(Tile.WallBottomRight) ||
                newTile.contains(Tile.WallLeft)  || newTile.contains(Tile.WallTopLeft)  || newTile.contains(Tile.WallBottomLeft) ||
                currentTile.contains(Tile.PushWallRight) || newTile.contains(Tile.PushWallLeft))
                return true;
            return false;
        }
        else if (x > x1) {
            if (currentTile.contains(Tile.WallLeft) || currentTile.contains(Tile.WallBottomLeft) || currentTile.contains(Tile.WallTopLeft) ||
                newTile.contains(Tile.WallRight)  || newTile.contains(Tile.WallTopRight)  || newTile.contains(Tile.WallBottomRight) ||
                currentTile.contains(Tile.PushWallLeft) || newTile.contains(Tile.PushWallRight))
                return true;
            return false;
        }
        return false;
    }

    private boolean checkForHole(Player player) {
        if (gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y).contains(Tile.Hole)) {
            player.loseLifeToken();
            return true;
        }
        return false;
    }


    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        Gdx.input.setInputProcessor(this);

        // Setup map and layers
        map = new TmxMapLoader().load("src/assets/VaultMap.tmx");

        // Load map into a board object
        gameBoard = new Board(map);

        // Setup camera
        boardLayer = gameBoard.getLayer(Tile.Board);
        playerLayer = gameBoard.getLayer(Tile.Player);
        boardWidth = gameBoard.getNumColumns();
        boardHeight = gameBoard.getNumRows();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth, boardHeight);
        camera.position.x = (float) boardWidth / 2;
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map, 1/boardLayer.getTileHeight());
        renderer.setView(camera);

        //PLAYERS
        Player player1 = new Player("André", new Vector2(0,0));
        players.add(player1);
        player1.move(Direction.UP);
        player1.move(Direction.UP);
        player1.move(Direction.UP);
        player1.move(Direction.RIGHT);
        player1.move(Direction.DOWN);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        for (Player player : players){
            // Oppdater hvilke layers spiller står på
            player.setLayers(gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y));
            //System.out.println(player.getLayers());

            updatePlayerState(player);

            // Tegn spiller på brettet
            playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, player.getPlayerState());
        }
    }

    public void updatePlayerState(Player player){

        // Loop through layers player is on
        for(Tile layer : player.getLayers()){
            if(layer.equals(Tile.Hole)){
                player.loseLifeToken();
            }
            else if (layer.equals(Tile.LaserHorizontal) || layer.equals(Tile.LaserVertical)){
                player.takeDamage();
            }
            else if (layer.equals(Tile.FlagOne)){
                if(player.getFlagScore() == 0)
                    player.registerFlag();
            }
            else if (layer.equals(Tile.FlagTwo)){
                if(player.getFlagScore() == 1)
                    player.registerFlag();
            }
            else if (layer.equals(Tile.FlagThree)){
                if(player.getFlagScore() == 2)
                    player.registerFlag();
            }
        }
    }

    public void updatePlayerPositions(){
        for (Player p : players){
            Vector2 position = p.getPosition();
            TiledMapTileLayer.Cell state = getPlayerState(p);
            playerLayer.setCell((int) position.x, (int) position.y, state);
        }
    }

    public TiledMapTileLayer.Cell getPlayerState(Player player){
        if (player.getFlagScore() >=3 ){
            return playerWonCell;
        }
        else if (player.getHealth() <= 0){
            return playerDiedCell;
        }
        else {
            return playerCell;
        }
    }

    public void playerTurn(Player player){

        ProgramCard card = player.getCurrentCard();

        switch (card.getValue()){
            //ROTATE TURNS
            case U_TURN:
                switch (player.getDirection()){
                    case UP:
                        player.setDirection(Direction.DOWN);
                    case DOWN:
                        player.setDirection(Direction.UP);
                    case RIGHT:
                        player.setDirection(Direction.LEFT);
                    case LEFT:
                        player.setDirection(Direction.RIGHT);
                }

            case ROTATE_LEFT:
                switch (player.getDirection()){
                    case UP:
                        player.setDirection(Direction.LEFT);
                    case DOWN:
                        player.setDirection(Direction.RIGHT);
                    case RIGHT:
                        player.setDirection(Direction.UP);
                    case LEFT:
                        player.setDirection(Direction.DOWN);
                }
            case ROTATE_RIGHT:
                switch (player.getDirection()){
                    case UP:
                        player.setDirection(Direction.RIGHT);
                    case DOWN:
                        player.setDirection(Direction.LEFT);
                    case RIGHT:
                        player.setDirection(Direction.DOWN);
                    case LEFT:
                        player.setDirection(Direction.UP);
                }
            // MOVE TURNS
            case MOVE_ONE:
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void resize(int i, int i1) { }
}
