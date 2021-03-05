package app;

import com.badlogic.gdx.Gdx;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FileTest {

    @Test
    public void playerPNGExists(){
        assertTrue(" player.png exists ",
                Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json").exists());
    }

}
