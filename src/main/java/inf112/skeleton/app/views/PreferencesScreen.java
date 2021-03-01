package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.ScreenOrchestrator;

public class PreferencesScreen extends AbstractScreen {

    public PreferencesScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        CheckBox fullscreenCheckbox = new CheckBox(null, skin);
        fullscreenCheckbox.setChecked(parent.getPreferences().isFullscreenEnabled());
        fullscreenCheckbox.addListener(event -> {
            parent.getPreferences().setFullscreenEnabled(fullscreenCheckbox.isChecked());
            if (fullscreenCheckbox.isChecked()) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            else {
                int width = Lwjgl3ApplicationConfiguration.getDisplayMode().width;
                int formattedHeight = (int) (width * 0.625);
                Gdx.graphics.setWindowedMode(width-250,formattedHeight-250);
            }
            return false;
        });

        Slider musicSlider = new Slider(0f, 1f, 0.1f,false, skin);
        musicSlider.setValue(parent.getPreferences().getMusicVolume());
        musicSlider.addListener(event -> {
            parent.getPreferences().setMusicVolume(musicSlider.getValue());
            return false;
        });

        CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(event -> {
            parent.getPreferences().setMusicEnabled(musicCheckbox.isChecked());
            return false;
        });

        Slider soundSlider = new Slider(0f, 1f, 0.1f,false, skin);
        soundSlider.setValue(parent.getPreferences().getSoundVolume());
        soundSlider.addListener(event -> {
            parent.getPreferences().setSoundVolume(soundSlider.getValue());
            return false;
        });

        CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEnabled());
        soundCheckbox.addListener(event -> {
            parent.getPreferences().setSoundEnabled(soundCheckbox.isChecked());
            return false;
        });

        TextButton mainMenu = new TextButton("Main menu", skin);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.MENU);
            }
        });

        Label titleLabel = new Label("Preferences", skin);
        Label musicLabel = new Label("Music volume", skin);
        Label soundLabel = new Label("Sound volume", skin);
        Label musicCheckboxLabel = new Label("Music", skin);
        Label soundCheckboxLabel = new Label("Sound", skin);
        Label fullscreenCheckboxLabel = new Label("Fullscreen", skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(10,5,0,5);
        table.add(fullscreenCheckboxLabel).left();
        table.add(fullscreenCheckbox);
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
