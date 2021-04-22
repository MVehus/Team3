package app.views;

import app.ScreenOrchestrator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen extends AbstractScreen {
    private final int width;
    private final int height;

    public MenuScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
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

        Image banner = new Image(new Sprite(new Texture("src/assets/roboRallyBanner.jpg")));
        banner.setWidth((float) (width/2.5));
        banner.setHeight((float) (banner.getWidth()/2.7));
        banner.setPosition(width/2-banner.getWidth()/2, (float) (height-banner.getHeight()*1.2));

        stage.addActor(banner);
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
