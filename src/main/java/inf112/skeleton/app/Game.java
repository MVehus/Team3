package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Game extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private final TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerWonCell = new TiledMapTileLayer.Cell();
    private final TiledMapTileLayer.Cell playerDiedCell = new TiledMapTileLayer.Cell();
    private final Vector2 playerPos = new Vector2();
    private int BoardWidth;
    private int BoardHeight;

    @Override
    public boolean keyUp(int keycode) {
        playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);
        if (keycode == Input.Keys.UP) {
            playerPos.y = (playerPos.y == BoardHeight-1) ? BoardHeight-1 : playerPos.y+1;
        }
        else if (keycode == Input.Keys.DOWN) {
            playerPos.y = (playerPos.y == 0) ? 0 : playerPos.y-1;
        }
        else if (keycode == Input.Keys.LEFT) {
            playerPos.x = (playerPos.x == 0) ? 0 : playerPos.x-1;
        }
        else if (keycode == Input.Keys.RIGHT) {
            playerPos.x = (playerPos.x == BoardWidth-1) ? BoardWidth-1 : playerPos.x+1;
        }
        return super.keyUp(keycode);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        Gdx.input.setInputProcessor(this);

        map = new TmxMapLoader().load("src/assets/VaultMap.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("FlagOne");

        BoardWidth = boardLayer.getWidth();
        BoardHeight = boardLayer.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BoardWidth, BoardHeight);
        camera.position.x = (float) BoardWidth/2;
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map, 1/boardLayer.getTileHeight());
        renderer.setView(camera);

        Texture playerTexture = new Texture("src/assets/player.png");
        TextureRegion[][] textureRegion = TextureRegion.split(playerTexture, 300, 300);
        playerCell.setTile(new StaticTiledMapTile(textureRegion[0][0]));
        playerWonCell.setTile(new StaticTiledMapTile(textureRegion[0][2]));
        playerDiedCell.setTile(new StaticTiledMapTile(textureRegion[0][1]));
        playerPos.x = 0;
        playerPos.y = 0;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        renderer.render();

        playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerCell);
        if (holeLayer.getCell((int) playerPos.x, (int) playerPos.y) != null) {
            playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerDiedCell);
        }
        else if (flagLayer.getCell((int) playerPos.x, (int) playerPos.y) != null) {
            playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerWonCell);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
