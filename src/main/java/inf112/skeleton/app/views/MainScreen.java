package inf112.skeleton.app.views;

import com.badlogic.gdx.Screen;
import inf112.skeleton.app.Game;
import inf112.skeleton.app.RoboRallyGame;

public class MainScreen extends AbstractScreen {
    private Game game = new Game();

    public MainScreen(RoboRallyGame roboRallyGame) {
        super(roboRallyGame);
    }

    @Override
    public void show() {
        game.create();
    }

    @Override
    public void render(float delta) {
        game.render();
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
