package inf112.skeleton.app.views;

import com.badlogic.gdx.Screen;
import inf112.skeleton.app.RoboRallyGame;

public class LoadingScreen implements Screen {
    private RoboRallyGame parent;

    public LoadingScreen(RoboRallyGame roboRallyGame) {
        parent = roboRallyGame;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        parent.changeScreen(RoboRallyGame.MENU);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
