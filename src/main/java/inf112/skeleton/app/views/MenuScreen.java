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
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));

        TextButton newGameButton = new TextButton("New Game", skin);
        TextButton preferencesButton = new TextButton("Preferences", skin);
        TextButton rulesButton = new TextButton("Rules", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(newGameButton).fillX().uniformX();
        table.row().padTop(10);
        table.add(preferencesButton).fillX().uniformX();
        table.row().padTop(10);
        table.add(rulesButton).fillX().uniformX();
        table.row().padTop(10);
        table.add(exitButton).fillX().uniformX();

        stage.addActor(table);

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RoboRallyGame.APPLICATION);
            }
        });

        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RoboRallyGame.PREFERENCES);
            }
        });

        rulesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RoboRallyGame.RULES);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
}
