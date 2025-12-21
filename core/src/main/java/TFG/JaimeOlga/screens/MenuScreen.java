package TFG.JaimeOlga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import TFG.JaimeOlga.Main;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MenuScreen implements Screen {

    private Main game;
    private BitmapFont font;

    public MenuScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont();

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
        font.draw(game.batch, "RESTAURANTE ALIENIGENA", 100, 400);
        font.draw(game.batch, "Pulsa ENTER para jugar", 100, 300);
        game.batch.end();

        // Detectar input para cambiar de pantalla
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.changeScreen(game.baseScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
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
