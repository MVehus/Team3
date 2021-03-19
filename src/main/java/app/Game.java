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
            playerPos.y = (playerPos.y == boardHeight -1) ? boardHeight -1 : playerPos.y+1;
        }
        else if (keycode == Input.Keys.DOWN) {
            playerPos.y = (playerPos.y == 0) ? 0 : playerPos.y-1;
        }
        else if (keycode == Input.Keys.LEFT) {
            playerPos.x = (playerPos.x == 0) ? 0 : playerPos.x-1;
        }
        else if (keycode == Input.Keys.RIGHT) {
            playerPos.x = (playerPos.x == boardWidth -1) ? boardWidth -1 : playerPos.x+1;
        }
        return super.keyUp(keycode);
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

        // Load player textures
        Texture playerTexture = new Texture("src/assets/player.png");
        TextureRegion[][] textureRegion = TextureRegion.split(playerTexture, 300, 300);
        playerCell.setTile(new StaticTiledMapTile(textureRegion[0][0]));
        playerWonCell.setTile(new StaticTiledMapTile(textureRegion[0][2]));
        playerDiedCell.setTile(new StaticTiledMapTile(textureRegion[0][1]));

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
            //player.setLayers(getLayersOnPosition(player.getPosition()));
            //System.out.println(player.getLayers());

            //updatePlayerState(player);

            // Tegn spiller på brettet
            playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, getPlayerState(player));
        }
    }

    public void updatePlayerState(Player player){
        List<String> layers = player.getLayers();

        // Loop through layers player is on
        for(String layer : layers){
            if(layer.equals("Hole")){
                player.loseLifeToken();
            }
            else if (layer.equals("LaserHorizontal") || layer.equals("LaserVertical")){
                player.takeDamage();
            }
            else if (layer.equals("FlagOne")){
                if(player.getFlagScore() == 0)
                    player.registerFlag();
            }
            else if (layer.equals("FlagTwo")){
                if(player.getFlagScore() == 1)
                    player.registerFlag();
            }
            else if (layer.equals("FlagThree")){
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
