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

        if (keycode == Input.Keys.UP) {
            if (currentPlayer.getDirection() != Direction.UP) {
                currentPlayer.setDirection(Direction.UP);
                return super.keyUp(keycode);
            }
        } else if (keycode == Input.Keys.DOWN) {
            if (currentPlayer.getDirection() != Direction.DOWN) {
                currentPlayer.setDirection(Direction.DOWN);
                return super.keyUp(keycode);
            }
        } else if (keycode == Input.Keys.LEFT) {
            if (currentPlayer.getDirection() != Direction.LEFT) {
                currentPlayer.setDirection(Direction.LEFT);
                return super.keyUp(keycode);
            }
        } else if (keycode == Input.Keys.RIGHT) {
            if (currentPlayer.getDirection() != Direction.RIGHT) {
                currentPlayer.setDirection(Direction.RIGHT);
                return super.keyUp(keycode);
            }

        }

        // IF NO ROTATION
        if (playerOnNextCell(currentPlayer)) {
            if (canPush(currentPlayer, getAllPlayersInLine(currentPlayer))) {
                push(currentPlayer, getAllPlayersInLine(currentPlayer));
            } else {
                System.out.println("Cannot push");
            }
        } else if (canMove(currentPlayer)) {
            currentPlayer.move();
        }


        // UPDATE ALLE PLAYERS
        for (Player p : players) {
            updatePlayerState(p);
        }

        render();

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

        // SET UP CLIENT
        Network.setGameReferenceForClient(this);

        if (Network.hostingServer()) {
            Network.sendPlayerListToClients();
        }

        List<Vector2> startPositions = gameBoard.getTileLocations(Tile.RobotStart);


        // TEST PLAYERS
        /*Player p1 = new Player(1, startPositions.get(0));
        players.add(p1);
        Player p2 = new Player(2, startPositions.get(1));
        players.add(p2);
        Player p3 = new Player(3, startPositions.get(2));
        players.add(p3);
        Player p4 = new Player(4, startPositions.get(3));
        players.add(p4);
        Player p5 = new Player(5, startPositions.get(4));
        players.add(p5);
        Player p6 = new Player(6, startPositions.get(5));
        players.add(p6);*/

        // CARDS
        deck = new CardDeck();

        System.out.println("LOADING TEXTURES...");
        //time(7000); // Må vente på at spillere skal connecte før den laster inn.
        loadTextures(players);
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
        List<Player> neighbours = new ArrayList<>();

        List<Vector2> nextCells = getNextCells(player, players.size());
        for (Vector2 v : nextCells) {
            if (getPlayerOnCell(v) != null) {
                neighbours.add(getPlayerOnCell(v));
            }
        }

        return neighbours;
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

    public List<Player> getPlayers() {
        return players;
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
        //TODO (Oblig 4)
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
            render();
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
            Thread.sleep(n);
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
        Player currentPlayer = players.get(Network.getMyId() - 1);
        Vector2 playerPos = currentPlayer.getPosition();
        playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);

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

        System.out.println(player.information());
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

        for (Player player : players) {
            playerTextures.put(player.getId(), allTextures.get(player.getId() - 1));
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