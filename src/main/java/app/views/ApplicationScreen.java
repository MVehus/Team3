package app.views;

import app.Game;
import app.ScreenOrchestrator;

public class ApplicationScreen extends AbstractScreen {
    private final Game game = new Game();

    public ApplicationScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
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
