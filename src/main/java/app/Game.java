package app;

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
import projectCard.ProgramCard;
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
    private final HashMap<Integer, List<TiledMapTileLayer.Cell>> playerTextures = new HashMap<>();
    private List<Player> players = new ArrayList<>();
    private int boardWidth;
    private int boardHeight;

    @Override
    public boolean keyUp(int keycode) {
        
        Player currentPlayer = players.get(Network.getMyId() - 1);

        //Player currentPlayer = players.get(0);

        Vector2 playerPos = currentPlayer.getPosition();

        playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);

        System.out.println(currentPlayer.toString());
        //System.out.println("Next cell available: " + playerOnNextCell(currentPlayer));
        //System.out.println(currentPlayer.getPosition() + " | " + getNextCells(currentPlayer, 2));
        System.out.println("All neighbours : " + getAllPlayersInLine(currentPlayer));
        for (Player p : players) {
            updatePlayerState(p);
            System.out.println(p.getName() + " on " + p.getPosition());
        }

        if (keycode == Input.Keys.UP) {
            if (currentPlayer.getDirection() != Direction.UP) {
                currentPlayer.setDirection(Direction.UP);
                System.out.println(currentPlayer.getName() + " new direction " + currentPlayer.getDirection());
            } else {
                if (playerOnNextCell(currentPlayer)) {
                    System.out.println("Player on next cell: " + getPlayerOnCell(currentPlayer.getNextCell()));
                    if (canPush(currentPlayer, getAllPlayersInLine(currentPlayer))) {
                        push(currentPlayer, getAllPlayersInLine(currentPlayer));
                        Network.sendToServer(currentPlayer.getModel());
                    } else {
                        System.out.println("Cannot push");
                    }
                    //push(currentPlayer, getAllPlayersInLine(currentPlayer));
                } else if (!canMove(currentPlayer)) {
                    System.out.println("Wall");
                } else {
                    System.out.println("MOVE");
                    currentPlayer.move();
                    Network.sendToServer(currentPlayer.getModel());
                }
            }
        } else if (keycode == Input.Keys.DOWN) {

            if (currentPlayer.getDirection() != Direction.DOWN) {
                currentPlayer.setDirection(Direction.DOWN);
            } else {
                if (playerOnNextCell(currentPlayer)) {
                    System.out.println("Player on next cell: " + getPlayerOnCell(currentPlayer.getNextCell()));
                    if (canPush(currentPlayer, getAllPlayersInLine(currentPlayer))) {
                        push(currentPlayer, getAllPlayersInLine(currentPlayer));
                        Network.sendToServer(currentPlayer.getModel());

                    } else {
                        System.out.println("Cannot push");
                    }

                } else if (!canMove(currentPlayer)) {
                    System.out.println("Wall");
                } else {
                    System.out.println("MOVE");
                    currentPlayer.move();
                    Network.sendToServer(currentPlayer.getModel());
                }
            }

        } else if (keycode == Input.Keys.LEFT) {
            if (currentPlayer.getDirection() != Direction.LEFT) {
                currentPlayer.setDirection(Direction.LEFT);
            } else {
                if (playerOnNextCell(currentPlayer)) {
                    System.out.println("Player on next cell: " + getPlayerOnCell(currentPlayer.getNextCell()));
                    if (canPush(currentPlayer, getAllPlayersInLine(currentPlayer))) {
                        push(currentPlayer, getAllPlayersInLine(currentPlayer));
                        Network.sendToServer(currentPlayer.getModel());

                    } else {
                        System.out.println("Cannot push");
                    }
                    //push(currentPlayer, getAllPlayersInLine(currentPlayer));
                } else if (!canMove(currentPlayer)) {
                    System.out.println("Wall");
                } else {
                    System.out.println("MOVE");
                    currentPlayer.move();
                    Network.sendToServer(currentPlayer.getModel());
                }
            }
        } else if (keycode == Input.Keys.RIGHT) {

            if (currentPlayer.getDirection() != Direction.RIGHT) {
                currentPlayer.setDirection(Direction.RIGHT);
            } else {
                if (playerOnNextCell(currentPlayer)) {
                    System.out.println("Player on next cell: " + getPlayerOnCell(currentPlayer.getNextCell()));
                    if (canPush(currentPlayer, getAllPlayersInLine(currentPlayer))) {
                        push(currentPlayer, getAllPlayersInLine(currentPlayer));
                        Network.sendToServer(currentPlayer.getModel());

                    } else {
                        System.out.println("Cannot push");
                    }
                    //push(currentPlayer, getAllPlayersInLine(currentPlayer));
                } else if (!canMove(currentPlayer)) {
                    System.out.println("Wall");
                } else {
                    System.out.println("MOVE");
                    currentPlayer.move();
                    Network.sendToServer(currentPlayer.getModel());
                }
            }
        }

        render();
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        return super.keyUp(keycode);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        Gdx.input.setInputProcessor(this);

        // TEST SPILLERE
        Player player1 = new Player(1, "André", new Vector2(1, 1));
        players.add(player1);

        Player player2 = new Player(2, "Test-1", new Vector2(2, 9));
        players.add(player2);
        Player player3 = new Player(3, "Test-2", new Vector2(3, 9));
        players.add(player3);
        Player player4 = new Player(4, "Test-3", new Vector2(1, 9));
        players.add(player4);

        // Setup map and layers
        map = new TmxMapLoader().load("src/assets/VaultMap.tmx");

        // Load map into a board object
        gameBoard = new Board(map);

        setupCameraAndRenderer();

        startPositions = gameBoard.getTileLocations(Tile.RobotStart);
        //System.out.println(startPositions);

        // SET UP CLIENT
        Network.setGameReferenceForClient(this);


        if (Network.hostingServer()) {
            Network.sendPlayerListToClients();
        }

        // SET UP CLIENT
        Network.setGameReferenceForClient(this);

        if (Network.hostingServer()) {
            Network.sendPlayerListToClients();
        }

        //for(Player player : players) player.setPosition((int) startPositions.get(player.getId()).x, (int) startPositions.get(player.getId()).y);

        loadTextures(players);

        // CARDS
        deck = new CardDeck();
    }

    private boolean canMove(Player player) {
        Vector2 currentPos = player.getPosition();
        List<Tile> currentTile = gameBoard.getTilesOnCell(currentPos.x, currentPos.y);
        Vector2 newPos = player.getNextCell();
        List<Tile> newTile = gameBoard.getTilesOnCell(newPos.x, newPos.y);

        if (currentPos.y < newPos.y) {
            if (currentTile.contains(Tile.WallTop) || currentTile.contains(Tile.WallTopRight) || currentTile.contains(Tile.WallTopLeft) ||
                    newTile.contains(Tile.WallBottom) || newTile.contains(Tile.WallBottomRight) || newTile.contains(Tile.WallBottomLeft) ||
                    currentTile.contains(Tile.PushWallTop) || newTile.contains(Tile.PushWallBottom))
                return false;
        } else if (currentPos.y > newPos.y) {
            if (currentTile.contains(Tile.WallBottom) || currentTile.contains(Tile.WallBottomRight) || currentTile.contains(Tile.WallBottomLeft) ||
                    newTile.contains(Tile.WallTop) || newTile.contains(Tile.WallTopRight) || newTile.contains(Tile.WallTopLeft) ||
                    currentTile.contains(Tile.PushWallBottom) || newTile.contains(Tile.PushWallTop))
                return false;
        } else if (currentPos.x < newPos.x) {
            if (currentTile.contains(Tile.WallRight) || currentTile.contains(Tile.WallTopRight) || currentTile.contains(Tile.WallBottomRight) ||
                    newTile.contains(Tile.WallLeft) || newTile.contains(Tile.WallTopLeft) || newTile.contains(Tile.WallBottomLeft) ||
                    currentTile.contains(Tile.PushWallRight) || newTile.contains(Tile.PushWallLeft))
                return false;
        } else if (currentPos.x > newPos.x) {
            if (currentTile.contains(Tile.WallLeft) || currentTile.contains(Tile.WallBottomLeft) || currentTile.contains(Tile.WallTopLeft) ||
                    newTile.contains(Tile.WallRight) || newTile.contains(Tile.WallTopRight) || newTile.contains(Tile.WallBottomRight) ||
                    currentTile.contains(Tile.PushWallLeft) || newTile.contains(Tile.PushWallRight))
                return false;
        }
        return true;
    }

    private boolean playerOnNextCell(Player player) {
        Vector2 nextCell = player.getNextCell();

        return (getPlayerOnCell(nextCell) != null);
    }

    public List<Vector2> getNextCells(Player player, int distance) {
        int xPos = (int) player.getPosition().x;
        int yPos = (int) player.getPosition().y;

        List<Vector2> cells = new ArrayList<>();

        for (int i = 1; i <= distance; i++) {
            Vector2 nextCell = new Vector2();
            switch (player.getDirection()) {
                case UP:
                    nextCell.x = xPos;
                    nextCell.y = yPos + i;
                    break;
                case DOWN:
                    nextCell.x = xPos;
                    nextCell.y = yPos - i;
                    break;
                case LEFT:
                    nextCell.x = xPos - i;
                    nextCell.y = yPos;
                    break;
                case RIGHT:
                    nextCell.x = xPos + i;
                    nextCell.y = yPos;
                    break;
            }
            if (!(nextCell.x >= boardWidth || nextCell.x < 0 || nextCell.y >= boardHeight || nextCell.y < 0)) {
                cells.add(nextCell);
            }
        }

        return cells;
    }

    public List<Player> getAllPlayersInLine(Player player) {
        List<Player> neigbours = new ArrayList<>();

        List<Vector2> nextCells = getNextCells(player, players.size());
        for (Vector2 v : nextCells) {
            if (getPlayerOnCell(v) != null) {
                neigbours.add(getPlayerOnCell(v));
            }
        }

        return neigbours;
    }

    public void push(Player player, List<Player> allPlayers) {

        Collections.reverse(allPlayers);

        for (Player p : allPlayers) {
            p.moveDirection(player.getDirection());
        }
        player.move();
    }

    public boolean canPush(Player player, List<Player> playersOnLine) {
        if (playersOnLine.size() > 0) {
            Player lastPlayer = playersOnLine.get(playersOnLine.size() - 1);
            Direction lastPlayerDirection = lastPlayer.getDirection();
            lastPlayer.setDirection(player.getDirection());
            if (canMove(lastPlayer)) {
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

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        // SPILLERE VELGER KORT I DENNE FASEN
        //chooseCards();

        // RUNDE: BEVEG KORT FRA CURRENTCARD
        //round();

        if (!players.isEmpty()) {
            for (Player p : players) {
                playerLayer.setCell((int) p.getPosition().x, (int) p.getPosition().y, getPlayerTexture(p));
            }
        }
    }

    public void updatePlayer(PlayerModel playerModel) {
        players.get(playerModel.getId()).setNewPlayerState(playerModel);
    }

    public void setPlayerList(ArrayList<Player> players) {
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
    public void round() {

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

        for (Player p : players) {
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
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void updatePlayerState(Player player) {

        float xPos = player.getPosition().x;
        float yPos = player.getPosition().y;
        if (xPos < 0 || xPos > boardWidth || yPos < 0 || yPos > boardHeight) {
            player.loseLifeToken();
            System.out.println("Lost LIFE");
        }

        // Loop through layers player is on
        List<Tile> tilesOnPos = gameBoard.getTilesOnCell(player.getPosition().x, player.getPosition().y);
        for (Tile layer : tilesOnPos) {
            if (layer.equals(Tile.Hole)) {
                System.out.println(player.getName() + " is on hole, lost one token. ");
                player.loseLifeToken();
            } else if (layer.equals(Tile.LaserHorizontal) || layer.equals(Tile.LaserVertical)) {
                System.out.println(player.getName() + " took one damage.");
                player.takeDamage();
            } else if (layer.equals(Tile.FlagOne)) {
                if (player.getFlagScore() == 0) {
                    System.out.println(player.getName() + " captured flag one!");
                    player.registerFlag();
                }
            } else if (layer.equals(Tile.FlagTwo)) {
                if (player.getFlagScore() == 1) {
                    System.out.println(player.getName() + " captured flag two!");
                    player.registerFlag();
                }

            } else if (layer.equals(Tile.FlagThree)) {
                if (player.getFlagScore() == 2) {
                    System.out.println(player.getName() + " captured flag three!");
                    player.registerFlag();
                }
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
    }

    public void playerTurn(Player player) {

        Value cardValue = player.getCurrentCard().getValue();
        System.out.println(player.getName() + " " + cardValue);

        if (cardValue == Value.U_TURN) {
            time(1000);
            player.rotate(Direction.RIGHT);
            player.rotate(Direction.RIGHT);
            updatePlayerState(player);
        } else if (cardValue == Value.ROTATE_RIGHT) {
            time(1000);
            player.rotate(Direction.RIGHT);
            updatePlayerState(player);
        } else if (cardValue == Value.ROTATE_LEFT) {
            time(1000);
            player.rotate(Direction.LEFT);
            updatePlayerState(player);
        } else if (cardValue == Value.MOVE_ONE) {
            if (canMove(player))
                time(1000);
            player.move();
            updatePlayerState(player);
        } else if (cardValue == Value.MOVE_TWO) {

            for (int step = 0; step < 2; step++) {
                if (canMove(player)) {
                    time(1000);
                    player.move();
                    updatePlayerState(player);
                }
            }
        } else if (cardValue == Value.MOVE_THREE) {
            for (int step = 0; step < 3; step++) {
                if (canMove(player)) {
                    time(1000);
                    player.move();
                    updatePlayerState(player);
                }
            }
        } else if (cardValue == Value.BACK_UP) {
            time(1000);
            System.out.println("BACKUP NOT IMPLEMENTED");
        }

        player.getCards().remove(player.getCurrentCard());

    }

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
        renderer = new OrthogonalTiledMapRenderer(map, 1 / boardLayer.getTileHeight());
        renderer.setView(camera);
    }

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

        for (Player player : players) {
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int i, int i1) {
    }
}