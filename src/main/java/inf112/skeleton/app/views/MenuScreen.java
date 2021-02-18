package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRallyGame;

public class MenuScreen implements Screen {
    private final Stage stage;
    private RoboRallyGame parent;

    public MenuScreen(RoboRallyGame roboRallyGame) {
        parent = roboRallyGame;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));

        TextButton newGame = new TextButton("New Game", skin);
        TextButton rules = new TextButton("Rules", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(rules).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        stage.addActor(table);

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RoboRallyGame.APPLICATION);
            }
        });

        rules.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RoboRallyGame.RULES);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(175/255f, 175/255f, 175/255f, 1);

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
    }
}
