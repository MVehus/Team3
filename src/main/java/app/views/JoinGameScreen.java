package app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import app.ScreenOrchestrator;

public class JoinGameScreen extends AbstractScreen{
    public JoinGameScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        TextButton mainMenu = new TextButton("Main menu", skin);
        Label connectLabel = new Label("Connect to a host with an ip-address: ", skin);
        Label ipLabel = new Label("ip:", skin);
        Label portLabel = new Label("port:", skin);
        TextField ipInput = new TextField("", skin);
        TextField portInput = new TextField("", skin);
        TextButton connectButton = new TextButton("Connect", skin);

        table.add(connectLabel).colspan(5);
        table.row().padTop(10);
        table.add(ipLabel);
        table.add(ipInput).width(180).padLeft(10);
        table.add(portLabel).padLeft(10);
        table.add(portInput).width(85).padLeft(10);
        table.add(connectButton).padLeft(10);
        table.row().padTop(10);
        table.add(mainMenu).colspan(5);

        stage.addActor(table);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.MENU);
            }
        });
    }
}
