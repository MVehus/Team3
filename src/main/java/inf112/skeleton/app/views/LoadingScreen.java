package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import inf112.skeleton.app.RoboRallyGame;

public class LoadingScreen extends AbstractScreen {
    public LoadingScreen(RoboRallyGame roboRallyGame) {
        super(roboRallyGame);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(175/255f, 175/255f, 175/255f, 1);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        parent.changeScreen(RoboRallyGame.MENU);
    }
}
