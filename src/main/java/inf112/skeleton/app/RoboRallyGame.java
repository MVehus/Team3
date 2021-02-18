package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.views.*;

public class RoboRallyGame extends Game {
    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;
    private RuleScreen ruleScreen;
    private AppPreferences preferences;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    public final static int RULES = 4;

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
                if(mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
            case RULES:
                ruleScreen = new RuleScreen(this);
                this.setScreen(ruleScreen);
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
