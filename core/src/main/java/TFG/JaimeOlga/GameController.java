package TFG.JaimeOlga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import TFG.JaimeOlga.entities.Player;
import TFG.JaimeOlga.screens.CharacterScreen;
import TFG.JaimeOlga.screens.CharacterSelectionScreen;
import TFG.JaimeOlga.screens.ControlsScreen;
import TFG.JaimeOlga.screens.DeadScreen;
import TFG.JaimeOlga.screens.MenuScreen;
import TFG.JaimeOlga.screens.SettingsMenu;
import TFG.JaimeOlga.screens.SettingsMenuInGame;
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
    public SettingsMenuInGame settingsMenuInGame;
    public BaseScreen baseScreen;
    public DeadScreen deadScreen;
    public ControlsScreen controlsScreen;
    public CharacterSelectionScreen characterSelectionScreen;
    public CharacterScreen characterScreen;

    public Screen currentScreen;
    public Screen lastScreen;
    public Screen lastPlayScreen;

    @Override
    public void create() {

        batch = new SpriteBatch();

        setUpGame();

        setScreen(menuScreen);
    }

    /**
     * Cambia de pantalla sin hacer dispose (permite reutilizar pantallas).
     * Usa super.setScreen() para delegar al sistema de Game de LibGDX.
     */
    public void changeScreen(Screen screen) {
        // Game.setScreen() llama hide() en la anterior y show() en la nueva
        // NO hace dispose() automáticamente, así que las pantallas se reutilizan
        if (player != null && player.isDead()) {
            setUpGame();
            player.setDead(false);
            player.setHealth(player.getMaxHealth());
        }

        lastScreen = currentScreen;
        if (screen instanceof OniricForestScreen ||
                screen instanceof BaseScreen) {
            lastPlayScreen = screen;
        }
        currentScreen = screen;

        super.setScreen(screen);

    }

    public void setUpGame() {

        // Pantallas del menu
        menuScreen = new MenuScreen(this);
        settingsMenu = new SettingsMenu(this);
        settingsMenuInGame = new SettingsMenuInGame(this);
        controlsScreen = new ControlsScreen(this);
        characterSelectionScreen = new CharacterSelectionScreen(this);
        characterScreen = new CharacterScreen(this);

    }

    public void setPlayer(Player player) {
        this.player = player;

        // Establecer jugador
        player.setxPosition(200);
        player.setyPosition(300);
        player.setHealth(player.getMaxHealth());
        player.setDead(false);
    }

    public void setGameScreens() {
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
