package app.sound;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class MusicAssetManager {

    public final AssetManager manager = new AssetManager();


    // Music
    public final String playingSong = "src/assets/music/Rolemusic_-_pl4y1ng.mp3";

    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }

}

