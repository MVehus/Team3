package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.lwjgl.system.CallbackI;
import org.w3c.dom.Text;

/**
 * This is just a demo class, may be deleted later :)
 */
public class Scene2dDemo implements ApplicationListener {

    private Stage stage;
    private Table table;
    private Skin skin;
    private TextButton joinGameButton;
    private TextButton newGameButton;

    public void create () {
        skin = new Skin(Gdx.files.internal("src/assets/skin/glassy-new/glassy-ui.json"));
        stage = new Stage(new ScreenViewport());
        table = new Table();

        //table.setFillParent(true);
        //stage.addActor(table);
        //table.setDebug(true); // This is optional, but enables debug lines for tables.
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);

        table.setPosition(0,Gdx.graphics.getHeight());

        // Add widgets to the table

        joinGameButton = new TextButton("Join game", skin, "default");
        newGameButton = new TextButton("Create game", skin, "default");

        table.padTop(30);
        table.add(joinGameButton);
        table.row();
        table.add(newGameButton);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
