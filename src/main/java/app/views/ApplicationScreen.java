package app.views;

import app.Game;
import app.ScreenOrchestrator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import network.Network;
import player.Player;

public class ApplicationScreen extends AbstractScreen {
    private final Game game = new Game();
    private int width;
    private int height;

    private Image cardSlotsTop;
    private Image cardSlotsMiddle;
    private Image cardSlotsBottom;

    public ApplicationScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        game.create();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        initializeCardSlots();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Game side of screen
        Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()*2/3,Gdx.graphics.getHeight() );
        game.render();

        //UI side of screen
        Gdx.gl.glViewport( Gdx.graphics.getWidth()*2/3,0,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight() );

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void initializeCardSlots() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/cardSlots.jpg"));
        cardSlotsTop = new Image(new SpriteDrawable(texture));
        cardSlotsTop.setWidth(width-40);
        cardSlotsTop.setHeight(200);
        cardSlotsTop.setPosition(20, 430);
        cardSlotsTop.setColor(Color.LIGHT_GRAY);

        cardSlotsMiddle = new Image(new SpriteDrawable(texture));
        cardSlotsMiddle.setWidth(width-40);
        cardSlotsMiddle.setHeight(200);
        cardSlotsMiddle.setPosition(20, 230);
        cardSlotsMiddle.setColor(Color.LIGHT_GRAY);

        cardSlotsBottom = new Image(new SpriteDrawable(texture));
        cardSlotsBottom.setWidth(width-40);
        cardSlotsBottom.setHeight(200);
        cardSlotsBottom.setPosition(20, 20);

        stage.addActor(cardSlotsTop);
        stage.addActor(cardSlotsMiddle);
        stage.addActor(cardSlotsBottom);
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
