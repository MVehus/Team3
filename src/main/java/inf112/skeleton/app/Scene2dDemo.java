package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

public class Scene2dDemo implements ApplicationListener {

    private Stage stage;
    private Table table;
    private Skin skin;
    private TextureAtlas atlas;


    public void create () {
        skin = new Skin(Gdx.files.internal("src/assets/skin/glassy-ui.json"));
        stage = new Stage(new ScreenViewport());

        table = new Table();
        //table.setFillParent(true);
        //stage.addActor(table);
        //table.setDebug(true); // This is optional, but enables debug lines for tables.
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        // Add widgets to the table

        TextButton join = new TextButton("Join game", skin, "small");
        //join.setHeight(150);
        //join.setWidth(500);

        TextButton create = new TextButton("Create a new game", skin, "small");
        //create.setHeight(150);
        //create.setWidth(600);

        table.padTop(50);
        table.addActor(join);
        table.addActor(create);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
