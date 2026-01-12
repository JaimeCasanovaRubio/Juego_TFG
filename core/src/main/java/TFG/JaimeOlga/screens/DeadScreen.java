package TFG.JaimeOlga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.entities.Player;
import TFG.JaimeOlga.screens.gameScreens.BaseScreen;

public class DeadScreen implements Screen {

    private GameController game;
    private BitmapFont font;
    private Player player;

    public DeadScreen(GameController game, Player player) {
        this.game = game;
        this.font = new BitmapFont();
        this.player = player;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        font.draw(game.batch, "Has muerto", 100, 400);
        font.draw(game.batch, "Pulsa ENTER para volver", 100, 300);
        game.batch.end();

        // Detectar input para cambiar de pantalla
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            player.setxPosition(200);
            player.setyPosition(300);
            game.changeScreen(new BaseScreen(game, player));
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
    }
}
