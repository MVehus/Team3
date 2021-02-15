package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Roborally");
        int width = Lwjgl3ApplicationConfiguration.getDisplayMode().width;

        int formattedHeight = (int) (width * 0.625);

        /** If you want fullscreenmode, uncomment this line  **/
        //cfg.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

        //cfg.setWindowedMode(width - 250, formattedHeight - 250 );
        cfg.setWindowedMode(700,700);

        new Lwjgl3Application(new Scene2dDemo(), cfg);
    }
}