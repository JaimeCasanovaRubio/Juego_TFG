package TFG.JaimeOlga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import TFG.JaimeOlga.GameController;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static TFG.JaimeOlga.utils.Cons.Images.*;

public class MenuScreen implements Screen {

    private GameController game;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;

    public MenuScreen(GameController game) {
        this.game = game;
        this.font = new BitmapFont();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);

    }

    @Override
    public void show() {
        // Se llama cuando la pantalla se muestra
    }

    @Override
    public void render(float delta) {
        // Limpiar pantalla con color oscuro
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        font.draw(game.batch, "Oniric Forest", 100, 400);
        font.draw(game.batch, "Pulsa ENTER para jugar", 100, 300);
        game.batch.end();

        // Detectar input para cambiar de pantalla
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.changeScreen(game.baseScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
