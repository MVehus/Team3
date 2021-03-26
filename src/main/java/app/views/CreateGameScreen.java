package app.views;

import Utilities.IpChecker;
import app.ScreenOrchestrator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import network.Network;

public class CreateGameScreen extends AbstractScreen{
    int port;

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

        TextButton startGameButton = new TextButton("Start Game", skin);

        createServerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                createServerTable.remove();
                port = Integer.parseInt(portInput.getText());
                Network.startServer(port);

                Table createGameTable = new Table();
                createGameTable.setFillParent(true);
                createGameTable.setDebug(false);

                Label connectedLabel = new Label("To connect to the server, give your friends this information:", skin);
                Label connectionInfoLabel = new Label("IP: " + IpChecker.getIp() + " - Port: " + port, skin);

                createGameTable.add(connectedLabel);
                createGameTable.row().padTop(10);
                createGameTable.add(connectionInfoLabel);
                createGameTable.row().padTop(10);
                createGameTable.add(startGameButton);
                createGameTable.row().padTop(10);
                createGameTable.add(mainMenu);

                stage.addActor(createGameTable);

                Network.makeNewClient("localhost", port);
            }
        });

        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Network.startGame();
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
