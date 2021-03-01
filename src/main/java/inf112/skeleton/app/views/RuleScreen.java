package inf112.skeleton.app.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.ScreenOrchestrator;

public class RuleScreen extends AbstractScreen {
    private SpriteBatch batch;
    private Sprite sprite;

    public RuleScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("src/assets/floor-guide.jpg"));
        sprite = new Sprite(texture);
        sprite.setCenterX(Gdx.graphics.getWidth() >> 1);
        sprite.setCenterY(Gdx.graphics.getHeight() >> 1);
        Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        TextButton mainMenu = new TextButton("Main menu", skin);
        table.add(mainMenu).fillX().uniformX();
        table.padTop(texture.getHeight()+60);
        stage.addActor(table);



        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenOrchestrator.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(175/255f, 175/255f, 175/255f, 1);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
