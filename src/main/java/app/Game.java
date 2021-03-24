package app;

import Models.GameStateModel;
import Models.PlayerModel;
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
import network.Network;
import player.Player;
import projectCard.CardDeck;
import projectCard.Value;

import java.util.*;

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

    private HashMap<Integer, List<TiledMapTileLayer.Cell>> playerTextures = new HashMap<>();
    private List<Player> players = new ArrayList<>();

    private boolean game = true;    // game = true så lenge ingen har vunnet, når en vinner setter vi game = false og avslutter spillet.

    private int boardWidth;
    private int boardHeight;

    @Override
    public boolean keyUp(int keycode) {
        Player currentPlayer = players.get(0);
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
                currentPlayer.resetPosition();
            }
            else if(gameBoard.getTilesOnCell(nextPos.x, nextPos.y).contains(Tile.Player)){
                System.out.println(currentPlayer.getName() + "has neighbor");
            }
            else {
                currentPlayer.move();
                //Network.sendToServer(currentPlayer.getModel());
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
                currentPlayer.resetPosition();
            }
            else if(gameBoard.getTilesOnCell(nextPos.x, nextPos.y).contains(Tile.Player)){
                System.out.println(currentPlayer.getName() + "has neighbor");
            }
            else {
                currentPlayer.move();
                //Network.sendToServer(currentPlayer.getModel());
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
                currentPlayer.resetPosition();
            }
            else if(gameBoard.getTilesOnCell(nextPos.x, nextPos.y).contains(Tile.Player)){
                System.out.println(currentPlayer.getName() + "has neighbor");
            }
            else{
                currentPlayer.move();
                //Network.sendToServer(currentPlayer.getModel());
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
                //Network.sendToServer(currentPlayer.getModel());
            }
        }
        System.out.println(currentPlayer.getName() + " at " + gameBoard.getTilesOnCell(playerPos.x, playerPos.y));

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

        setupCameraAndRenderer();

        startPositions = gameBoard.getTileLocations(Tile.RobotStart);
        System.out.println(startPositions);

        // SET UP CLIENT
        //Network.setGameReferenceForClient(this);


        // TEST SPILLERE
        Player player1 = new Player(1, "André", new Vector2(2,2));
        players.add(player1);

        Player player2 = new Player(2, "Bård", new Vector2(2,3));
        players.add(player2);


        //if (Network.hostingServer()){ Network.sendPlayerListToClients(); }

        //for(Player player : players) player.setPosition((int) startPositions.get(player.getId()).x, (int) startPositions.get(player.getId()).y);

        loadTextures(players);

        // CARDS
        deck = new CardDeck();

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

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        // SPILLERE VELGER KORT I DENNE FASEN
        //chooseCards();

        // RUNDE: BEVEG KORT FRA CURRENTCARD
        //round();

        for(Player p : players)
            playerLayer.setCell((int) p.getPosition().x, (int) p.getPosition().y, getPlayerTexture(p));
    }

    public void updatePlayer(PlayerModel playerModel){
        players.get(playerModel.getId()).setNewPlayerState(playerModel);
    }

    public void setPlayerList(ArrayList<Player> players){
        this.players = players;
    }

    private void chooseCards() {
        //TODO
    }

    /**
     * A. Reveal Program Cards
     * B. Robots Move
     * C. Board Elements Move
     * D. Lasers Fire
     * E. Touch Checkpoints
     */
    public void round(){

        // Move players based on current card
        // Sort players by priority
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                if (p1.getCurrentCard().getPriority() < p2.getCurrentCard().getPriority())
                    return 1;
                if (p1.getCurrentCard().getPriority() > p2.getCurrentCard().getPriority())
                    return -1;
                return 0;
            }
        });

        for(Player p : players){
            playerTurn(p);
            playerLayer.setCell((int) p.getPosition().x, (int) p.getPosition().y, getPlayerTexture(p));
        }

        // Move board elements
        gameBoard.conveyorBeltMove(players);

        // Fire lasers
        System.out.println("___LASERS FIRE");

        // Touch checkpoints
        System.out.println("___TOUCH CHECKPOINTS");
    }

    private void time(int n) {
        try {
            Thread.sleep(n);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
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

        Value cardValue = player.getCurrentCard().getValue();
        System.out.println(player.getName() + " " + cardValue);

        if(cardValue == Value.U_TURN){
            time(1000);
            player.rotate(Direction.RIGHT);
            player.rotate(Direction.RIGHT);
            updatePlayerState(player);
        }
        else if(cardValue == Value.ROTATE_RIGHT){
            time(1000);
            player.rotate(Direction.RIGHT);
            updatePlayerState(player);
        }
        else if(cardValue == Value.ROTATE_LEFT){
            time(1000);
            player.rotate(Direction.LEFT);
            updatePlayerState(player);
        }
        else if(cardValue == Value.MOVE_ONE){
            if(canMove(player))
                time(1000);
                player.move();
                updatePlayerState(player);
        }
        else if(cardValue == Value.MOVE_TWO){

            for(int step = 0; step < 2 ; step++){
                if(canMove(player)){
                    time(1000);
                    player.move();
                    updatePlayerState(player);
                }
            }
        }
        else if(cardValue == Value.MOVE_THREE){
            for(int step = 0; step < 3 ; step++){
                if(canMove(player)){
                    time(1000);
                    player.move();
                    updatePlayerState(player);
                }
            }
        }
        else if(cardValue == Value.BACK_UP){
            time(1000);
            System.out.println("BACKUP NOT IMPLEMENTED");
        }

        player.getCards().remove(player.getCurrentCard());

    }

    private TiledMapTileLayer.Cell getPlayerTexture(Player player){

        List<TiledMapTileLayer.Cell> textures = playerTextures.get(player.getId());

        if(player.getDirection() == Direction.UP){
            for(TiledMapTileLayer.Cell texture : textures){
                texture.setRotation(1);
            }
        }
        else if(player.getDirection() == Direction.DOWN){
            for(TiledMapTileLayer.Cell texture : textures){
                texture.setRotation(3);
            }
        }
        else if(player.getDirection() == Direction.LEFT){
            for(TiledMapTileLayer.Cell texture : textures){
                texture.setRotation(2);
            }
        }
        else {
            // Keep texture as it is
            for(TiledMapTileLayer.Cell texture : textures){
                texture.setRotation(0);
            }
        }

        if(player.getFlagScore() == 3)
            return textures.get(1);
        else if(!player.isAlive())
            return textures.get(2);
        else {
            return textures.get(0);
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

    /**
     * Jeg vet denne koden er rotete.. skal få ryddet opp senere
     *
     * @param players
     */
    private void loadTextures(List<Player> players) {
        TiledMapTileLayer.Cell player1_cell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player1_wonCell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player1_diedCell = new TiledMapTileLayer.Cell();

        TiledMapTileLayer.Cell player2_cell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player2_wonCell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player2_diedCell = new TiledMapTileLayer.Cell();

        TiledMapTileLayer.Cell player3_cell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player3_wonCell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player3_diedCell = new TiledMapTileLayer.Cell();

        TiledMapTileLayer.Cell player4_cell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player4_wonCell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player4_diedCell = new TiledMapTileLayer.Cell();

        TiledMapTileLayer.Cell player5_cell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player5_wonCell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player5_diedCell = new TiledMapTileLayer.Cell();

        TiledMapTileLayer.Cell player6_cell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player6_wonCell = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell player6_diedCell = new TiledMapTileLayer.Cell();

        List<List<TiledMapTileLayer.Cell>> allTextures = new ArrayList<>();
        // 1
        Texture player1Texture = new Texture("src/assets/players/player_1.png");
        TextureRegion[][] texture1Region = TextureRegion.split(player1Texture, 300, 300);
        player1_cell.setTile(new StaticTiledMapTile(texture1Region[0][0]));
        player1_diedCell.setTile(new StaticTiledMapTile(texture1Region[0][2]));
        player1_wonCell.setTile(new StaticTiledMapTile(texture1Region[0][1]));
        List<TiledMapTileLayer.Cell> player1Textures = new ArrayList<>();
        player1Textures.add(player1_cell);
        player1Textures.add(player1_wonCell);
        player1Textures.add(player1_diedCell);

        // 2
        Texture player2Texture = new Texture("src/assets/players/player_2.png");
        TextureRegion[][] texture2Region = TextureRegion.split(player2Texture, 300, 300);
        player2_cell.setTile(new StaticTiledMapTile(texture2Region[0][0]));
        player2_diedCell.setTile(new StaticTiledMapTile(texture2Region[0][2]));
        player2_wonCell.setTile(new StaticTiledMapTile(texture2Region[0][1]));
        List<TiledMapTileLayer.Cell> player2Textures = new ArrayList<>();
        player2Textures.add(player2_cell);
        player2Textures.add(player2_wonCell);
        player2Textures.add(player2_diedCell);

        // 3
        Texture player3Texture = new Texture("src/assets/players/player_3.png");
        TextureRegion[][] texture3Region = TextureRegion.split(player3Texture, 300, 300);
        player3_cell.setTile(new StaticTiledMapTile(texture3Region[0][0]));
        player3_diedCell.setTile(new StaticTiledMapTile(texture3Region[0][2]));
        player3_wonCell.setTile(new StaticTiledMapTile(texture3Region[0][1]));
        List<TiledMapTileLayer.Cell> player3Textures = new ArrayList<>();
        player3Textures.add(player3_cell);
        player3Textures.add(player3_wonCell);
        player3Textures.add(player3_diedCell);

        // 4
        Texture player4Texture = new Texture("src/assets/players/player_4.png");
        TextureRegion[][] texture4Region = TextureRegion.split(player4Texture, 300, 300);
        player4_cell.setTile(new StaticTiledMapTile(texture4Region[0][0]));
        player4_diedCell.setTile(new StaticTiledMapTile(texture4Region[0][2]));
        player4_wonCell.setTile(new StaticTiledMapTile(texture4Region[0][1]));
        List<TiledMapTileLayer.Cell> player4Textures = new ArrayList<>();
        player4Textures.add(player4_cell);
        player4Textures.add(player4_wonCell);
        player4Textures.add(player4_diedCell);

        // 5
        Texture player5Texture = new Texture("src/assets/players/player_5.png");
        TextureRegion[][] texture5Region = TextureRegion.split(player5Texture, 300, 300);
        player5_cell.setTile(new StaticTiledMapTile(texture5Region[0][0]));
        player5_diedCell.setTile(new StaticTiledMapTile(texture5Region[0][2]));
        player5_wonCell.setTile(new StaticTiledMapTile(texture5Region[0][1]));
        List<TiledMapTileLayer.Cell> player5Textures = new ArrayList<>();
        player5Textures.add(player5_cell);
        player5Textures.add(player5_wonCell);
        player5Textures.add(player5_diedCell);

        // 6
        Texture player6Texture = new Texture("src/assets/players/player_6.png");
        TextureRegion[][] texture6Region = TextureRegion.split(player6Texture, 300, 300);
        player6_cell.setTile(new StaticTiledMapTile(texture6Region[0][0]));
        player6_diedCell.setTile(new StaticTiledMapTile(texture6Region[0][2]));
        player6_wonCell.setTile(new StaticTiledMapTile(texture6Region[0][1]));
        List<TiledMapTileLayer.Cell> player6Textures = new ArrayList<>();
        player6Textures.add(player6_cell);
        player6Textures.add(player6_wonCell);
        player6Textures.add(player6_diedCell);

        allTextures.add(player1Textures);
        allTextures.add(player2Textures);
        allTextures.add(player3Textures);
        allTextures.add(player4Textures);
        allTextures.add(player5Textures);
        allTextures.add(player6Textures);

        for(Player player : players){
            playerTextures.put(player.getId(), allTextures.get(player.getId() - 1));
            System.out.println(player.getId() + " " + allTextures.get(player.getId() - 1));
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
