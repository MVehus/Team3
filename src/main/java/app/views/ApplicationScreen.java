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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import network.Network;
import player.Player;

public class ApplicationScreen extends AbstractScreen {
    private final Game game = new Game();
    private int width;
    private int height;
    private int gameWidth;

    private final Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));

    public ApplicationScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {
        game.create();
        gameWidth = Gdx.graphics.getWidth()*2/3;

        initCardSlots();
        initButtons();
        initDamageTokens();
        initFlags();
        initLifeTokens();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Game side of screen
        Gdx.gl.glViewport( 0,0,gameWidth,Gdx.graphics.getHeight() );
        game.render();

        //UI side of screen
        Gdx.gl.glViewport( 0,0,width,Gdx.graphics.getHeight() );
        //stage.setDebugAll(true);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void initCardSlots() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/cardSlots.jpg"));
        Image cardSlotsTop = new Image(new SpriteDrawable(texture));
        cardSlotsTop.setWidth(width-gameWidth);
        cardSlotsTop.setHeight((float) ((width-gameWidth)/5*4/3));
        cardSlotsTop.setPosition(gameWidth, (float) ((width-gameWidth)/5*4/3)*2+80);
        cardSlotsTop.setColor(Color.LIGHT_GRAY);

        Image cardSlotsMiddle = new Image(new SpriteDrawable(texture));
        cardSlotsMiddle.setWidth(width-gameWidth);
        cardSlotsMiddle.setHeight((float) ((width-gameWidth)/5*4/3));
        cardSlotsMiddle.setPosition(gameWidth, (float) ((width-gameWidth)/5*4/3)+80);
        cardSlotsMiddle.setColor(Color.LIGHT_GRAY);

        Image cardSlotsBottom = new Image(new SpriteDrawable(texture));
        cardSlotsBottom.setWidth(width-gameWidth);
        cardSlotsBottom.setHeight((float) ((width-gameWidth)/5*4/3));
        cardSlotsBottom.setPosition(gameWidth, 80);

        stage.addActor(cardSlotsTop);
        stage.addActor(cardSlotsMiddle);
        stage.addActor(cardSlotsBottom);
    }

    private void initButtons() {
        TextButton lockInButton = new TextButton("Lock in Cards", skin);
        lockInButton.setPosition(width-((width-gameWidth)/3), 10);
        lockInButton.setWidth((width-gameWidth)/3);
        lockInButton.setHeight(60);

        TextButton powerDownButton = new TextButton("Power Down", skin);
        powerDownButton.setPosition(gameWidth+20, 10);
        powerDownButton.setWidth((width-gameWidth)/3);
        powerDownButton.setHeight(60);

        lockInButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Lock in");
            }
        });

        powerDownButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Power down");
            }
        });

        stage.addActor(lockInButton);
        stage.addActor(powerDownButton);
    }

    private void initDamageTokens() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens0.png"));
        Image damageTokens = new Image(new SpriteDrawable(texture));
        damageTokens.setPosition(gameWidth, height-150);
        damageTokens.setWidth(width-gameWidth);
        damageTokens.setHeight((width-gameWidth)/10);

        stage.addActor(damageTokens);
    }

    private void initFlags() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/flags/flags0.png"));
        Image flags = new Image(new SpriteDrawable(texture));
        flags.setPosition(gameWidth, height-(200+(width-gameWidth)/10));
        flags.setWidth((width-gameWidth)/2);
        flags.setHeight((width-gameWidth)/6);

        stage.addActor(flags);
    }

    private void initLifeTokens() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/lifeTokens/lifeTokens0.png"));
        Image flags = new Image(new SpriteDrawable(texture));
        flags.setPosition(gameWidth+((width-gameWidth)/2), height-(200+(width-gameWidth)/10));
        flags.setWidth((width-gameWidth)/2);
        flags.setHeight((width-gameWidth)/6);

        stage.addActor(flags);
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
