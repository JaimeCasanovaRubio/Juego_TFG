package TFG.JaimeOlga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import TFG.JaimeOlga.screens.GameScreen;
import TFG.JaimeOlga.screens.MenuScreen;
import TFG.JaimeOlga.screens.SettingsMenu;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    public SpriteBatch batch;

    // Screens (se crean una sola vez y se reutilizan)
    public MenuScreen menuScreen;
    public GameScreen gameScreen;
    public SettingsMenu settingsMenu;

    @Override
    public void create() {
        batch = new SpriteBatch();
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        settingsMenu = new SettingsMenu(this);
        setScreen(menuScreen);
    }

    /**
     * Cambia de pantalla sin hacer dispose (permite reutilizar pantallas).
     * Usa super.setScreen() para delegar al sistema de Game de LibGDX.
     */
    public void changeScreen(Screen screen) {
        // Game.setScreen() llama hide() en la anterior y show() en la nueva
        // NO hace dispose() automáticamente, así que las pantallas se reutilizan
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Liberar recursos de todas las pantallas al cerrar el juego
        if (menuScreen != null)
            menuScreen.dispose();
        if (gameScreen != null)
            gameScreen.dispose();
        if (settingsMenu != null)
            settingsMenu.dispose();
    }
}
