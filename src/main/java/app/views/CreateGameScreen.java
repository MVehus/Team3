package app.views;

import app.ScreenOrchestrator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import network.Network;
import network.Server;

public class CreateGameScreen extends AbstractScreen{
    public CreateGameScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));
        Table createServerTable = new Table();
        createServerTable.setFillParent(true);
        createServerTable.setDebug(false);

        Label connectPortLabel = new Label("Select a port to create a server", skin);
        Label portLabel = new Label("Port:", skin);
        TextField portInput = new TextField("", skin);
        TextButton createServerButton = new TextButton("Create Server", skin);
        TextButton mainMenu = new TextButton("Main menu", skin);

        createServerTable.add(connectPortLabel).colspan(3);
        createServerTable.row().padTop(10);
        createServerTable.add(portLabel);
        createServerTable.add(portInput).width(85).padLeft(10);
        createServerTable.add(createServerButton).padLeft(10);
        createServerTable.row().padTop(10);
        createServerTable.add(mainMenu).colspan(3);

        stage.addActor(createServerTable);

        Table createGameTable = new Table();
        createGameTable.setFillParent(true);
        createGameTable.setDebug(false);

        Label connectedLabel = new Label("Connected Players:", skin);
        TextButton createGameButton = new TextButton("Create Game", skin);

        createGameTable.add(connectedLabel);
        createGameTable.row().padTop(10);
        createGameTable.add(createGameButton);
        createGameTable.row().padTop(10);

        createServerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Network.startServer(Integer.parseInt(portInput.getText()));
                createServerTable.remove();
                createGameTable.add(mainMenu);
                stage.addActor(createGameTable);
            }
        });

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
