package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.RoboRallyGame;

public class PreferencesScreen extends AbstractScreen {
    private Label titleLabel;
    private Label musicLabel;
    private Label soundLabel;
    private Label musicCheckboxLabel;
    private Label soundCheckboxLabel;

    public PreferencesScreen(RoboRallyGame roboRallyGame) {
        super(roboRallyGame);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        Slider musicSlider = new Slider(0f, 1f, 0.1f,false, skin);
        musicSlider.setValue(parent.getPreferences().getMusicVolume());
        musicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(musicSlider.getValue());
                return false;
            }
        });

        CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(event -> {
            parent.getPreferences().setMusicEnabled(musicCheckbox.isChecked());
            return false;
        });

        Slider soundSlider = new Slider(0f, 1f, 0.1f,false, skin);
        soundSlider.setValue(parent.getPreferences().getSoundVolume());
        soundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(soundSlider.getValue());
                return false;
            }
        });

        CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundCheckbox.addListener(event -> {
            parent.getPreferences().setSoundEffectsEnabled(soundCheckbox.isChecked());
            return false;
        });

        TextButton mainMenu = new TextButton("Main menu", skin);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RoboRallyGame.MENU);
            }
        });

        titleLabel = new Label( "Preferences", skin );
        musicLabel = new Label( "Music volume", skin );
        soundLabel = new Label( "Sound volume", skin );
        musicCheckboxLabel = new Label( "Music", skin );
        soundCheckboxLabel = new Label( "Sound", skin );

        table.add(titleLabel).colspan(2);
        table.row().pad(10,5,0,5);
        table.add(musicLabel).left();
        table.add(musicSlider);
        table.row().pad(10,5,0,5);
        table.add(musicCheckboxLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,5,0,5);
        table.add(soundLabel).left();
        table.add(soundSlider);
        table.row().pad(10,5,0,5);
        table.add(soundCheckboxLabel).left();
        table.add(soundCheckbox);
        table.row().pad(10,5,0,5);
        table.add(mainMenu).colspan(2);

        stage.addActor(table);
    }
}
