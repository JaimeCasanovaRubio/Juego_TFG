package TFG.JaimeOlga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import TFG.JaimeOlga.entities.Player;
import TFG.JaimeOlga.screens.MenuScreen;
import TFG.JaimeOlga.screens.SettingsMenu;
import TFG.JaimeOlga.screens.gameScreens.BaseScreen;
import TFG.JaimeOlga.screens.gameScreens.OniricForestScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    public SpriteBatch batch;
    public Player player;

    // Screens (se crean una sola vez y se reutilizan)
    public MenuScreen menuScreen;
    public OniricForestScreen oniricForestScreen;
    public SettingsMenu settingsMenu;
    public BaseScreen baseScreen;

    @Override
    public void create() {

        batch = new SpriteBatch();
        player = new Player(200, 300);
        menuScreen = new MenuScreen(this);
        oniricForestScreen = new OniricForestScreen(this, player);
        settingsMenu = new SettingsMenu(this);
        baseScreen = new BaseScreen(this, player);
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
        if (oniricForestScreen != null)
            oniricForestScreen.dispose();
        if (settingsMenu != null)
            settingsMenu.dispose();
        if (baseScreen != null)
            baseScreen.dispose();
    }
}
