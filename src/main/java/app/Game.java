package app;

import Models.PlayerModel;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
import projectCard.Value;

import java.util.*;

public class Game extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer laserLayer;
    private List<TiledMapTileLayer.Cell> laserTextures;
    private Board gameBoard;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private final HashMap<Integer, List<TiledMapTileLayer.Cell>> playerTextures = new HashMap<>();
    private List<Player> players = new ArrayList<>();
    private int boardWidth;
    private int boardHeight;

    public Game(){
        Network.setGameReferenceForClient(this);
    }

    //region SETUP
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        Gdx.input.setInputProcessor(this);
        loadTextures();

        // Setup map and layers
        map = new TmxMapLoader().load("src/assets/VaultMap.tmx");

        // Load map into a board object
        gameBoard = new Board(map);

        setupCameraAndRenderer();

        if (Network.hostingServer()) {
            Network.sendPlayerListToClients();
        }

        // CARDS
        System.out.println("LOADING GAME...");
        time(3000);
    }

    private void setupCameraAndRenderer() {
        // Setup camera
        TiledMapTileLayer boardLayer = gameBoard.getLayer(Tile.Board);
        playerLayer = gameBoard.getLayer(Tile.Player);
        laserLayer = gameBoard.getLayer(Tile.Player);
        boardWidth = gameBoard.getNumColumns();
        boardHeight = gameBoard.getNumRows();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth, boardHeight);
        camera.position.x = (float) boardWidth / 2;
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / boardLayer.getTileHeight());
        renderer.setView(camera);
    }

    /**
     * Load all .png files from assets folder
     */
    private void loadTextures() {

        // LOAD PLAYER TEXTURES
        List<String> imgNames = Arrays.asList("player_1.png", "player_2.png", "player_3.png", "player_4.png", "player_5.png", "player_6.png");

        List<List<TiledMapTileLayer.Cell>> allTextures = new ArrayList<>();

        for(String img : imgNames){
            TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
            TiledMapTileLayer.Cell playerWonCell = new TiledMapTileLayer.Cell();
            TiledMapTileLayer.Cell playerDiedCell = new TiledMapTileLayer.Cell();

            Texture playerTexture = new Texture("src/assets/players/" + img);
            TextureRegion[][] textureRegion = TextureRegion.split(playerTexture, 300, 300);

            playerCell.setTile(new StaticTiledMapTile(textureRegion[0][0]));
            playerDiedCell.setTile(new StaticTiledMapTile(textureRegion[0][2]));
            playerWonCell.setTile(new StaticTiledMapTile(textureRegion[0][1]));

            List<TiledMapTileLayer.Cell> playerTextures = new ArrayList<>();

            playerTextures.add(playerCell);
            playerTextures.add(playerWonCell);
            playerTextures.add(playerDiedCell);

            allTextures.add(playerTextures);
        }

        // LOAD LASER TEXTURES
        TiledMapTileLayer.Cell laserHorizontal = new TiledMapTileLayer.Cell();
        TiledMapTileLayer.Cell laserVertical = new TiledMapTileLayer.Cell();
        Texture laserTexture = new Texture("src/assets/lazers/laser.png");
        TextureRegion[][] textureRegion = TextureRegion.split(laserTexture, 300, 300);
        laserVertical.setTile(new StaticTiledMapTile(textureRegion[0][0]));
        laserHorizontal.setTile(new StaticTiledMapTile(textureRegion[0][1]));
        laserTextures = new ArrayList<>();

        laserTextures.add(laserHorizontal);
        laserTextures.add(laserVertical);

        for(int i = 1; i <= 6; i++){
            playerTextures.put(i, allTextures.get(i - 1));
        }
    }

    /**
     * This method takes in a player and checks its status.
     * Return correct texture based on isAlive, isWinner or returns normal texture.
     * @param player
     * @return - correct player texture
     */
    private TiledMapTileLayer.Cell getPlayerTexture(Player player) {

        List<TiledMapTileLayer.Cell> textures = playerTextures.get(player.getId());

        if (player.getDirection() == Direction.UP) {
            for (TiledMapTileLayer.Cell texture : textures) {
                texture.setRotation(1);
            }
        } else if (player.getDirection() == Direction.DOWN) {
            for (TiledMapTileLayer.Cell texture : textures) {
                texture.setRotation(3);
            }
        } else if (player.getDirection() == Direction.LEFT) {
            for (TiledMapTileLayer.Cell texture : textures) {
                texture.setRotation(2);
            }
        } else {
            // Keep texture as it is
            for (TiledMapTileLayer.Cell texture : textures) {
                texture.setRotation(0);
            }
        }

        if (player.getFlagScore() == 3)
            return textures.get(1);
        else if (!player.isAlive())
            return textures.get(2);
        else {
            return textures.get(0);
        }
    }
    //endregion

    //region NETWORK
    public void updatePlayer(PlayerModel playerModel) {
        players.get(playerModel.getId()-1).setNewPlayerState(playerModel);
    }

    public void setPlayerList(ArrayList<Player> players) {
        this.players = players;
    }
    //endregion

    //region ROUND
    /**
     * A. Reveal Program Cards
     * B. Robots Move
     * C. Board Elements Move
     * D. Lasers Fire
     * E. Touch Checkpoints
     */
    public void round() {
        while (true) {
            int playersDone = 0;
            for (Player p : players) {
                if (p.programCards.size() == 0){
                    playersDone += 1;
                }
            }
            if (playersDone == players.size()) {
                break;
            }
            movePlayers();
            boardElementsTurn();
            drawLasers();
            checkForFlags();
        }
        //playersChooseCards();
        //selectPlayerTurn();
        //revealCards();
    }

    /**
     * This method loops through all players, all draw lasers in available cells in front of player.
     * Laser should stop if hit player or wall.
     */
    private void drawLasers(){
        /*
        for(Player p : players){
            List<Vector2> line = getNextCells(p);
            for(Vector2 cell : line){
                if(p.getDirection() == Direction.DOWN || p.getDirection() == Direction.UP)
                    laserLayer.setCell((int) cell.x, (int) cell.y, laserTextures.get(1));
                else {
                    laserLayer.setCell((int) cell.x, (int) cell.y, laserTextures.get(0));
                }
            }
        }

         */
    }

    private void movePlayers() {
        for (Player p : players) {
            if(p.programCards.size() != 0) {
                time(500);
                System.out.println(p.getCurrentCard());
                playerTurn(p);
                time(700);
            }
        }
    }

    /**
     * Take a turn based on current card.
     *
     * @param player
     */
    public void playerTurn(Player player) {
        Value cardValue = player.getCurrentCard().getValue();
        Vector2 position = player.getPosition();
        Vector2 nextPosition = player.getNextCell(true);
        playerLayer.setCell((int) position.x, (int) position.y, null);
        int startX = (int) position.x, startY = (int) position.y;

        if (cardValue == Value.U_TURN) {
            player.rotate(Direction.RIGHT);
            player.rotate(Direction.RIGHT);
        }
        else if (cardValue == Value.ROTATE_RIGHT) {
            player.rotate(Direction.RIGHT);
        }
        else if (cardValue == Value.ROTATE_LEFT) {
            player.rotate(Direction.LEFT);
        }
        else if (cardValue == Value.MOVE_ONE) {
            playerLayer.setCell((int) position.x, (int) position.y, null);
            if (validMove(position, nextPosition)) {
                player.move();
                updatePlayerState(player);
            }
        }
        else if (cardValue == Value.MOVE_TWO) {
            for (int step = 0; step < 2; step++) {
                playerLayer.setCell((int) position.x, (int) position.y, null);
                if (validMove(position, nextPosition)) {
                    player.move();
                    updatePlayerState(player);
                    nextPosition = player.getNextCell(true);
                    if (checkForHole(player)){
                        break;
                    }
                }
            }

        }
        else if (cardValue == Value.MOVE_THREE){
            for (int step = 0; step < 3; step++){
                playerLayer.setCell((int) position.x, (int) position.y, null);
                if (validMove(position, nextPosition)) {
                    player.move();
                    updatePlayerState(player);
                    nextPosition = player.getNextCell(true);
                    if (checkForHole(player)){
                        break;
                    }
                }
            }
        }
        else if (cardValue == Value.BACK_UP){
            playerLayer.setCell((int) position.x, (int) position.y, null);
            if(validMove(position, player.getNextCell(false))){
                player.backUp();
                updatePlayerState(player);
            }
        }

        playerLayer.setCell(startX, startY, null);
        System.out.println(player.information());
        player.useCurrentCard();
    }

    private void checkForFlags() {
        for (Player p : players) {
            updatePlayerFlagState(p);
        }
    }

    private void updatePlayerFlagState(Player player) {
        float xPos = player.getPosition().x;
        float yPos = player.getPosition().y;

        // Loop through layers player is on
        List<Tile> tilesOnPos = gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y);
        for (Tile layer : tilesOnPos) {
            if (layer.equals(Tile.FlagOne)) {
                if (player.getFlagScore() == 0) {
                    System.out.println(player.getId() + " captured flag one!");
                    player.registerFlag();
                }
            } else if (layer.equals(Tile.FlagTwo)) {
                if (player.getFlagScore() == 1) {
                    System.out.println(player.getId() + " captured flag two!");
                    player.registerFlag();
                }

            } else if (layer.equals(Tile.FlagThree)) {
                if (player.getFlagScore() == 2) {
                    System.out.println(player.getId() + " captured flag three!");
                    player.registerFlag();
                }
            }
        }

    }

    private void boardElementsTurn() {
        // Move board elements
        for (Player player : players) {
            playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, null);
        }
        gameBoard.conveyorBeltMove(players);

        for (Player p : players) {
            updatePlayerState(p);

        }

        // Fire lasers
        System.out.println("___LASERS FIRE");
    }

    /**
     * Loop through tiles on cell for each movement.
     * Change playerState based on tile (change position, take damage or loose life)
     * @param player
     */
    public void updatePlayerState(Player player) {

        playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, null);

        float xPos = player.getPosition().x;
        float yPos = player.getPosition().y;
        if (xPos < 0 || xPos > boardWidth || yPos < 0 || yPos > boardHeight) {
            player.loseLifeToken();
            System.out.println("Player: " + player.getId() + "Lost LIFE");
        }
        // Loop through layers player is on
        List<Tile> tilesOnPos = gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y);

        for (Tile layer : tilesOnPos) {
            if (layer.equals(Tile.Hole)) {
                System.out.println(player.getId() + " is on hole, lost one token. ");
                player.loseLifeToken();
            } else if (layer.equals(Tile.LaserHorizontal) || layer.equals(Tile.LaserVertical)) {
                System.out.println(player.getId() + " took one damage.");
                player.takeDamage();
            } else if (layer.equals(Tile.PushWallBottom)){
                player.setPosition((int) xPos, (int) yPos + 1);
            } else if (layer.equals(Tile.PushWallTop)){
                player.setPosition((int) xPos, (int) yPos - 1);
            } else if (layer.equals(Tile.PushWallLeft)){
                player.setPosition((int) xPos + 1, (int) yPos);
            } else if (layer.equals(Tile.PushWallRight)){
                player.setPosition((int) xPos - 1, (int) yPos);
            }
        }
        render();
        Network.sendUpdatedPlayerModel(player.getModel());

    }

    private boolean checkForHole(Player player) {
        List<Tile> tilesOnPos = gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y);
        for (Tile layer : tilesOnPos) {
            if (layer.equals(Tile.Hole)) {
                System.out.println(player.getName() + " is on hole, lost one token. ");
                player.loseLifeToken();
                return true;
            }
        }
        return false;
    }
    //endregion

    /**
     * This method checks a movement from a postion to next cell.
     * Check for walls.
     *
     * @param from - from position
     * @param to - to position
     * @return - true if possible move, false otherwise .
     */
    private boolean validMove(Vector2 from, Vector2 to){

        List<Tile> currentTile = gameBoard.getTilesOnCell(from.x, from.y);
        List<Tile> newTile = gameBoard.getTilesOnCell(to.x, to.y);

        if (from.y < to.y) {
            if (currentTile.contains(Tile.WallTop) ||
                    currentTile.contains(Tile.WallTopRight) ||
                    currentTile.contains(Tile.WallTopLeft) ||
                    newTile.contains(Tile.WallBottom) ||
                    newTile.contains(Tile.WallBottomRight) ||
                    newTile.contains(Tile.WallBottomLeft) ||
                    currentTile.contains(Tile.PushWallTop) ||
                    newTile.contains(Tile.PushWallBottom))
                return false;
        }
        else if (from.y > to.y) {
            if (currentTile.contains(Tile.WallBottom) ||
                    currentTile.contains(Tile.WallBottomRight) ||
                    currentTile.contains(Tile.WallBottomLeft) ||
                    newTile.contains(Tile.WallTop) ||
                    newTile.contains(Tile.WallTopRight) ||
                    newTile.contains(Tile.WallTopLeft) ||
                    currentTile.contains(Tile.PushWallBottom) ||
                    newTile.contains(Tile.PushWallTop))
                return false;
        }
        else if (from.x < to.x) {
            if (currentTile.contains(Tile.WallRight) ||
                    currentTile.contains(Tile.WallTopRight) ||
                    currentTile.contains(Tile.WallBottomRight) ||
                    currentTile.contains(Tile.PushWallRight) ||
                    newTile.contains(Tile.PushWallLeft) ||
                    newTile.contains(Tile.WallLeft) ||
                    newTile.contains(Tile.WallTopLeft) ||
                    newTile.contains(Tile.WallBottomLeft))
                return false;
        }
        else if (from.x > to.x) {
            if (currentTile.contains(Tile.WallLeft) ||
                    currentTile.contains(Tile.WallBottomLeft) ||
                    currentTile.contains(Tile.WallTopLeft) ||
                    newTile.contains(Tile.WallRight) ||
                    newTile.contains(Tile.WallTopRight) ||
                    newTile.contains(Tile.WallBottomRight) ||
                    currentTile.contains(Tile.PushWallLeft) ||
                    newTile.contains(Tile.PushWallRight))
                return false;
        } else if(from == to) {
            return false;
        }

        return true;
        
    }

    /**
     *
     * @param player
     * @return - true if there is a player on current player's next cell .
     */
    private boolean playerOnNextCell(Player player) {
        Vector2 nextCell = player.getNextCell(true);

        return (getPlayerOnCell(nextCell) != null);
    }

    /**
     * This method loops through cells in front of player, based on player direction.
     * Stops if method hits a wall or player. Used to draw lasers.
     *
     * @param player - current player
     * @return - list of available cells (in straight line)
     */
    public List<Vector2> availableCellsInFrontOfPlayer(Player player) {
        List<Vector2> cells = new ArrayList<>();

        Vector2 fromPos = player.getPosition();
        Vector2 nextPos = player.getNextCell(true);

        while(validMove(fromPos, nextPos)){
            cells.add(nextPos);
            fromPos = nextPos;

            Vector2 nextCell = new Vector2();
            switch (player.getDirection()) {
                case UP:
                    nextCell.x = nextPos.x;
                    nextCell.y = nextPos.y + 1;
                    break;
                case DOWN:
                    nextCell.x = nextPos.x;
                    nextCell.y = nextPos.y - 1;
                    break;
                case LEFT:
                    nextCell.x = nextPos.x - 1;
                    nextCell.y = nextPos.y;
                    break;
                case RIGHT:
                    nextCell.x = nextPos.x + 1;
                    nextCell.y = nextPos.y;
                    break;
            }
            if(nextCell.x < 0 || nextCell.x > boardWidth || nextCell.y < 0 || nextCell.y > boardHeight){
                break;
            }
            nextPos = nextCell;

        }

        return cells;
    }

    /**
     * Used for pushing multiple players.
     *
     * @param player
     * @return - list of players which stand in front of current player.
     */
    public List<Player> getAllPlayersInLine(Player player) {
        List<Player> neighbours = new ArrayList<>();

        List<Vector2> nextCells = availableCellsInFrontOfPlayer(player);
        for (Vector2 v : nextCells) {
            if (getPlayerOnCell(v) != null) {
                neighbours.add(getPlayerOnCell(v));
            }
        }

        return neighbours;
    }

    /**
     * This method push a list of players in current player's direction.
     *
     * @param player - pusher
     * @param allPlayers - pushed players
     */
    public void push(Player player, List<Player> allPlayers) {

        Collections.reverse(allPlayers);

        for (Player p : allPlayers) {
            p.moveDirection(player.getDirection());
        }
        player.move();
    }

    /**
     * Loops through all players in front of current player.
     * Check if last player in line, can be push in current player's direction.
     *
     * @param player
     * @param playersOnLine - true if last player can be pushed.
     * @return
     */
    public boolean canPush(Player player, List<Player> playersOnLine) {
        if (playersOnLine.size() > 0) {
            Player lastPlayer = playersOnLine.get(playersOnLine.size() - 1);
            Direction lastPlayerDirection = lastPlayer.getDirection();
            lastPlayer.setDirection(player.getDirection());
            if (validMove(lastPlayer.getPosition(), lastPlayer.getNextCell(true))) {
                lastPlayer.setDirection(lastPlayerDirection);
                return true;
            } else {
                lastPlayer.setDirection(lastPlayerDirection);
                return false;
            }
        }
        return false;
    }

    public Player getPlayerOnCell(Vector2 cell) {
        for (Player player : players) {
            if (player.getPosition().equals(cell)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void revealCards() {
        //TODO show cards on gameboard
    }

    private void playersChooseCards() {
        //TODO
        // check if all players are ready then continue
        // if only one player left, set timer
    }

    private void selectPlayerTurn() {
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getCurrentCard().getPriority(), p1.getCurrentCard().getPriority());
            }
        });
    }



    //region UTILITIES
    private void time(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    //endregion

    //region OVERRIDE

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        for (Player p : players) {
            playerLayer.setCell((int) p.getPosition().x, (int) p.getPosition().y, getPlayerTexture(p));
            clearPlayerTexture(p);
        }
    }

    public void clearPlayerTexture(Player player){
        int x = (int) player.getPosition().x;
        int y = (int) player.getPosition().y;
        for (int i = x - 3; i < x + 3; i++){
            for (int j = y - 3; j < y + 3; j++){
                for (Player p : players){
                    if(!(i == p.getPosition().x && j == p.getPosition().y || !(i == x && j == y))){
                        playerLayer.setCell(i, j, null);
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int i, int i1) {
    }

    //endregion

}