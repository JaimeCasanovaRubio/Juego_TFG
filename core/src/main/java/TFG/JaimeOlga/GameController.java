package TFG.JaimeOlga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import TFG.JaimeOlga.entities.Player;
import TFG.JaimeOlga.screens.DeadScreen;
import TFG.JaimeOlga.screens.MenuScreen;
import TFG.JaimeOlga.screens.SettingsMenu;
import TFG.JaimeOlga.screens.gameScreens.BaseScreen;
import TFG.JaimeOlga.screens.gameScreens.OniricForestScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class GameController extends Game {
    public SpriteBatch batch;
    public Player player;

    // Screens (se crean una sola vez y se reutilizan)
    public MenuScreen menuScreen;
    public OniricForestScreen oniricForestScreen;
    public SettingsMenu settingsMenu;
    public BaseScreen baseScreen;
    public DeadScreen deadScreen;

    public Screen currentScreen;
    public Screen lastScreen;

    @Override
    public void create() {

        batch = new SpriteBatch();
        player = new Player(200, 300);

        createScreens();

        setScreen(menuScreen);
    }

    /**
     * Cambia de pantalla sin hacer dispose (permite reutilizar pantallas).
     * Usa super.setScreen() para delegar al sistema de Game de LibGDX.
     */
    public void changeScreen(Screen screen) {
        // Game.setScreen() llama hide() en la anterior y show() en la nueva
        // NO hace dispose() automáticamente, así que las pantallas se reutilizan
        if (player.isDead()) {
            createScreens();
            player.setDead(false);
            player.setHealth(player.getMaxHealth());
        }

        lastScreen = currentScreen;
        currentScreen = screen;
        super.setScreen(screen);

    }

    private void createScreens() {
        // Pantallas del menu
        menuScreen = new MenuScreen(this);
        settingsMenu = new SettingsMenu(this);

        // Pantallas del juego
        oniricForestScreen = new OniricForestScreen(this, player);
        baseScreen = new BaseScreen(this, player);
        deadScreen = new DeadScreen(this, player);
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
