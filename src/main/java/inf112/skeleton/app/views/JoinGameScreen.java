package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.ScreenOrchestrator;

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
        Label ipLabel = new Label("Connect to a host with an ip-address: ", skin);
        TextField ipInput = new TextField("", skin);
        TextButton connectButton = new TextButton("Connect", skin);

        table.add(ipLabel).colspan(2);
        table.row().padTop(10);
        table.add(ipInput).fillX().padLeft(40);
        table.add(connectButton).left().padLeft(10);
        table.row().padTop(10);
        table.add(mainMenu).colspan(2);

        stage.addActor(table);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.MENU);
            }
        });
    }
}
