package app;

import app.views.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenOrchestrator extends Game {
    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private ApplicationScreen applicationScreen;
    private EndScreen endScreen;
    private RuleScreen ruleScreen;
    private JoinGameScreen joinGameScreen;
    private CreateGameScreen createGameScreen;
    private AppPreferences preferences;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    public final static int RULES = 4;
    public final static int JOINGAME = 5;
    public final static int CREATEGAME = 6;

    @Override
    public void create() {
        batch = new SpriteBatch();
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        preferences = new AppPreferences();
    }
    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if(applicationScreen == null) applicationScreen = new ApplicationScreen(this);
                this.setScreen(applicationScreen);
                break;
            case ENDGAME:
                endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
            case RULES:
                ruleScreen = new RuleScreen(this);
                this.setScreen(ruleScreen);
                break;
            case JOINGAME:
                joinGameScreen = new JoinGameScreen(this);
                this.setScreen(joinGameScreen);
                break;
            case CREATEGAME:
                createGameScreen = new CreateGameScreen(this);
                this.setScreen(createGameScreen);
                break;
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }

    public AppPreferences getPreferences() {
        return this.preferences;
    }
}