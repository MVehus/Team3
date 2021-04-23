package app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_FULLSCREEN_ENABLED = "fullscreen.enabled";
    private static final String PREFS_NAME = "roboRallyPrefs";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public boolean isFullscreenEnabled() {
        return getPrefs().getBoolean(PREF_FULLSCREEN_ENABLED, false);
    }

    public void setFullscreenEnabled(boolean fullscreenEnabled) {
        getPrefs().putBoolean(PREF_FULLSCREEN_ENABLED, fullscreenEnabled);
        getPrefs().flush();
    }
}
