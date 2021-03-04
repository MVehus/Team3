package app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import app.ScreenOrchestrator;

public class MenuScreen extends AbstractScreen {

    public MenuScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));

        TextButton createGameButton = new TextButton("Create Game", skin);
        TextButton joinGameButton = new TextButton("Join Game", skin);
        TextButton preferencesButton = new TextButton("Preferences", skin);
        TextButton rulesButton = new TextButton("Rules", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(createGameButton).fillX().uniformX();
        table.row().padTop(10);
        table.add(joinGameButton).fillX().uniformX();
        table.row().padTop(10);
        table.add(preferencesButton).fillX().uniformX();
        table.row().padTop(10);
        table.add(rulesButton).fillX().uniformX();
        table.row().padTop(10);
        table.add(exitButton).fillX().uniformX();

        stage.addActor(table);

        createGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.CREATEGAME);
            }
        });

        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.JOINGAME);
            }
        });

        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.PREFERENCES);
            }
        });

        rulesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.RULES);
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
