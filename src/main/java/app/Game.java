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
import player.Player;
import projectCard.CardDeck;
import projectCard.ProgramCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMapTileLayer playerLayer;
    private Board gameBoard;
    private List<Vector2> startPositions;
    private CardDeck deck;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private final TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerWonCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerDiedCell = new TiledMapTileLayer.Cell();

    private List<Player> currentPlayers = new ArrayList<>();
    private boolean game = true;    // game = true så lenge ingen har vunnet, når en vinner setter vi game = false og avslutter spillet.

    private int boardWidth;
    private int boardHeight;

    @Override
    public boolean keyUp(int keycode) {
        Player currentPlayer = currentPlayers.get(0);
        Vector2 playerPos = currentPlayer.getPosition();

        Vector2 nextPos = currentPlayer.getNextCell();

        playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);

        if (keycode == Input.Keys.UP) {
            currentPlayer.setDirection(Direction.UP);

            if (!canMove(currentPlayer)) {
                System.out.println("Wall");
            }
            else if (playerPos.y == boardHeight-1) {
                currentPlayer.loseLifeToken();
            }
            else if(gameBoard.getTilesOnCell(nextPos.x, nextPos.y).contains(Tile.Player)){
                System.out.println(currentPlayer.getName() + "has neighbor");
            }
            else {
                currentPlayer.move();
            }
        }

        else if (keycode == Input.Keys.DOWN) {
            currentPlayer.setDirection(Direction.DOWN);
            if (!canMove(currentPlayer)) {
                System.out.println("Wall");
            }
            else if (playerPos.y == 0) {
                System.out.println("Test");
                currentPlayer.loseLifeToken();
            }
            else if(gameBoard.getTilesOnCell(nextPos.x, nextPos.y).contains(Tile.Player)){
                System.out.println(currentPlayer.getName() + "has neighbor");
            }
            else {
                currentPlayer.move();
            }
        }

        else if (keycode == Input.Keys.LEFT) {
            currentPlayer.setDirection(Direction.LEFT);
            if (!canMove(currentPlayer)) {
                System.out.println("Wall");
            }
            else if (playerPos.x == 0) {
                System.out.println("Test");
                currentPlayer.loseLifeToken();
            }
            else if(gameBoard.getTilesOnCell(nextPos.x, nextPos.y).contains(Tile.Player)){
                System.out.println(currentPlayer.getName() + "has neighbor");
            }
            else{
                currentPlayer.move();
            }

        }
        else if (keycode == Input.Keys.RIGHT) {
            currentPlayer.setDirection(Direction.RIGHT);
            if (!canMove(currentPlayer)) {
                System.out.println("Wall");
                //Do nothing
            }
            else if (playerPos.x == boardWidth-1) {
                System.out.println("Test");
                currentPlayer.loseLifeToken();
            }
            else if(gameBoard.getTilesOnCell(nextPos.x, nextPos.y).contains(Tile.Player)){
                System.out.println(currentPlayer.getName() + "has neighbor");
            }
            else {
                currentPlayer.move();
            }
        }
        System.out.println(currentPlayer.getName() + " at " + gameBoard.getTilesOnCell(playerPos.x, playerPos.y));
        gameBoard.conveyorBeltMove(currentPlayers);
        //checkForHole(currentPlayer);
        return super.keyUp(keycode);
    }

    private boolean canMove(Player player){
        Vector2 currentPos = player.getPosition();
        List<Tile> currentTile = gameBoard.getTilesOnCell(currentPos.x, currentPos.y);
        Vector2 newPos = player.getNextCell();
        List<Tile> newTile = gameBoard.getTilesOnCell(newPos.x, newPos.y);

        if(currentPos.y < newPos.y){
            if (currentTile.contains(Tile.WallTop) || currentTile.contains(Tile.WallTopRight) || currentTile.contains(Tile.WallTopLeft) ||
                    newTile.contains(Tile.WallBottom)  || newTile.contains(Tile.WallBottomRight)  || newTile.contains(Tile.WallBottomLeft) ||
                    currentTile.contains(Tile.PushWallTop) || newTile.contains(Tile.PushWallBottom))
                return false;
        }
        else if (currentPos.y > newPos.y) {
            if (currentTile.contains(Tile.WallBottom) || currentTile.contains(Tile.WallBottomRight) || currentTile.contains(Tile.WallBottomLeft) ||
                    newTile.contains(Tile.WallTop)  || newTile.contains(Tile.WallTopRight)  || newTile.contains(Tile.WallTopLeft) ||
                    currentTile.contains(Tile.PushWallBottom) || newTile.contains(Tile.PushWallTop))
                return false;
        }
        else if (currentPos.x < newPos.x) {
            if (currentTile.contains(Tile.WallRight) || currentTile.contains(Tile.WallTopRight) || currentTile.contains(Tile.WallBottomRight) ||
                    newTile.contains(Tile.WallLeft)  || newTile.contains(Tile.WallTopLeft)  || newTile.contains(Tile.WallBottomLeft) ||
                    currentTile.contains(Tile.PushWallRight) || newTile.contains(Tile.PushWallLeft))
                return false;
        }
        else if (currentPos.x > newPos.x) {
            if (currentTile.contains(Tile.WallLeft) || currentTile.contains(Tile.WallBottomLeft) || currentTile.contains(Tile.WallTopLeft) ||
                    newTile.contains(Tile.WallRight)  || newTile.contains(Tile.WallTopRight)  || newTile.contains(Tile.WallBottomRight) ||
                    currentTile.contains(Tile.PushWallLeft) || newTile.contains(Tile.PushWallRight))
                return false;
        }
        return true;
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

        setupCameraAndRenderer();

        // Load player textures
        Texture playerTexture = new Texture("src/assets/player.png");
        TextureRegion[][] textureRegion = TextureRegion.split(playerTexture, 300, 300);
        playerCell.setTile(new StaticTiledMapTile(textureRegion[0][0]));
        playerWonCell.setTile(new StaticTiledMapTile(textureRegion[0][2]));
        playerDiedCell.setTile(new StaticTiledMapTile(textureRegion[0][1]));

        startPositions = gameBoard.getTileLocations(Tile.RobotStart);

        //PLAYERS
        Player player1 = new Player("André", startPositions.get(0));
        currentPlayers.add(player1);
        Player player2 = new Player("Bård", startPositions.get(1));
        currentPlayers.add(player2);
        Player player3 = new Player("Are", startPositions.get(2));
        currentPlayers.add(player3);

        // CARDS
        deck = new CardDeck();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        for (Player player : currentPlayers){
            // Oppdater hvilke layers spiller står på
            //player.setLayers(gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y));

            updatePlayerState(player);

            // Tegn spiller på brettet
            playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, getPlayerTexture(player));
        }
    }

    private TiledMapTileLayer.Cell getPlayerTexture(Player player){
        if(player.getFlagScore() == 3)
            return playerWonCell;
        else if(!player.isAlive())
            return playerDiedCell;
        else {
            return playerCell;
        }
    }

    /**
     * A. Reveal Program Cards
     * B. Robots Move
     * C. Board Elements Move
     * D. Lasers Fire
     * E. Touch Checkpoints
     */
    public void round(){
        // Choose cards
        System.out.println("Deck contains: " + deck.getDeckSize());
        for(Player p : currentPlayers){
            p.drawProgramCards(deck);
            System.out.println(p.getName() + " cards : " + p.getProgramCards());
        }

        // Move robots based on current card

        // Sorter spillere basert på høyest prioritet
        currentPlayers.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                if (p1.getCurrentCard().getPriority() > p2.getCurrentCard().getPriority())
                    return 1;
                if (p1.getCurrentCard().getPriority() < p2.getCurrentCard().getPriority())
                    return -1;
                return 0;
            }
        });

        for(Player p : currentPlayers){
            System.out.println();
        }

        // Move board elements

        // Fire lasers

        // Touch checkpoints
    }

    public void updatePlayerState(Player player){

        // Loop through layers player is on
        List<Tile> tilesOnPos = gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y);
        for(Tile layer : tilesOnPos){
            if(layer.equals(Tile.Hole)){
                System.out.println(player.getName() + " is on hole, lost one token. ");
                player.loseLifeToken();
            }
            else if (layer.equals(Tile.LaserHorizontal) || layer.equals(Tile.LaserVertical)){
                System.out.println(player.getName() + " took one damage.");
                player.takeDamage();
            }
            else if (layer.equals(Tile.FlagOne)){
                if(player.getFlagScore() == 0)
                    System.out.println(player.getName() + " captured flag one!");
                    player.registerFlag();
            }
            else if (layer.equals(Tile.FlagTwo)){
                if(player.getFlagScore() == 1)
                    System.out.println(player.getName() + " captured flag two!");
                    player.registerFlag();
            }
            else if (layer.equals(Tile.FlagThree)){
                if(player.getFlagScore() == 2)
                    System.out.println(player.getName() + " captured flag three!");
                    player.registerFlag();
            }
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

    private void setupCameraAndRenderer() {
        // Setup camera
        TiledMapTileLayer boardLayer = gameBoard.getLayer(Tile.Board);
        playerLayer = gameBoard.getLayer(Tile.Player);
        boardWidth = gameBoard.getNumColumns();
        boardHeight = gameBoard.getNumRows();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth, boardHeight);
        camera.position.x = (float) boardWidth / 2;
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map, 1/ boardLayer.getTileHeight());
        renderer.setView(camera);
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
