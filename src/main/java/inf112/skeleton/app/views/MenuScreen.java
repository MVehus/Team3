package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.RoboRallyGame;

public class MenuScreen extends AbstractScreen {

    public MenuScreen(RoboRallyGame roboRallyGame) {
        super(roboRallyGame);
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
}
