package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRallyGame;

public class RuleScreen implements Screen {
    private final Stage stage;
    private RoboRallyGame parent;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Skin skin;

    public RuleScreen(RoboRallyGame roboRallyGame) {
        parent = roboRallyGame;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("src/assets/floor-guide.jpg"));
        sprite = new Sprite(texture);
        sprite.setCenterX(Gdx.graphics.getWidth() >> 1);
        sprite.setCenterY(Gdx.graphics.getHeight() >> 1);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));
        TextButton mainMenu = new TextButton("Main menu", skin);
        table.add(mainMenu).fillX().uniformX();
        table.padTop(texture.getHeight()+60);
        stage.addActor(table);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RoboRallyGame.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(175/255f, 175/255f, 175/255f, 1);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
