package TFG.JaimeOlga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import TFG.JaimeOlga.GameController;

public class SettingsMenu implements Screen {
    private GameController game;
    private BitmapFont font;

    public SettingsMenu(GameController game) {
        this.game = game;
        this.font = new BitmapFont();
    }

    @Override
    public void show() {
        // Limpiar pantalla con color oscuro
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        font.draw(game.batch, "Ajustes", 500, 400);
        game.batch.end();
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

    }
}
