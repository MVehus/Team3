package app.views;

import app.ScreenOrchestrator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import network.Network;

public class JoinGameScreen extends AbstractScreen{
    public JoinGameScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));
        Table joinGameTable = new Table();
        joinGameTable.setFillParent(true);
        joinGameTable.setDebug(false);

        TextButton mainMenu = new TextButton("Main menu", skin);
        Label connectLabel = new Label("Connect to a host with an IP-address: ", skin);
        Label ipLabel = new Label("IP:", skin);
        Label portLabel = new Label("Port:", skin);
        TextField ipInput = new TextField("", skin);
        TextField portInput = new TextField("", skin);
        TextButton connectButton = new TextButton("Connect", skin);

        joinGameTable.add(connectLabel).colspan(5);
        joinGameTable.row().padTop(10);
        joinGameTable.add(ipLabel);
        joinGameTable.add(ipInput).width(180).padLeft(10);
        joinGameTable.add(portLabel).padLeft(10);
        joinGameTable.add(portInput).width(85).padLeft(10);
        joinGameTable.add(connectButton).padLeft(10);
        joinGameTable.row().padTop(10);
        joinGameTable.add(mainMenu).colspan(5);

        stage.addActor(joinGameTable);

        Table connectedTable = new Table();
        connectedTable.setFillParent(true);
        connectedTable.setDebug(false);

        Label connectedLabel = new Label("Connected - waiting for host to start the game", skin);
        Label connectionFailedLabel = new Label("Something went wrong, try again", skin);

        connectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                joinGameTable.remove();
                if (Network.makeNewClient(ipInput.getText(), Integer.parseInt(portInput.getText()))) {
                    connectedTable.add(connectedLabel);
                }
                else {
                    connectedTable.add(connectionFailedLabel);
                }
                connectedTable.row().padTop(10);
                connectedTable.add(mainMenu);
                stage.addActor(connectedTable);
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
