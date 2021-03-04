package app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import app.ScreenOrchestrator;

public class CreateGameScreen extends AbstractScreen{
    public CreateGameScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        Label connectedLabel = new Label("Connected Players:", skin);
        TextButton createGameButton = new TextButton("Create Game", skin);
        TextButton mainMenu = new TextButton("Main menu", skin);
        Label localPlayer = new Label("blahblahipipip", skin); //TODO create connected player list

        table.add(connectedLabel);
        table.row().padTop(10);
        table.add(localPlayer);
        table.row().padTop(10);
        table.add(createGameButton);
        table.row().padTop(10);
        table.add(mainMenu);

        stage.addActor(table);

        createGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.APPLICATION);
            }
        });

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.MENU);
            }
        });
    }
}
