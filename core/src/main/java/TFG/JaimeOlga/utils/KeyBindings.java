package TFG.JaimeOlga.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;

/**
 * Clase que gestiona los controles configurables del juego.
 * Los controles se guardan en las preferencias de LibGDX para persistencia.
 */
public class KeyBindings {

    private static final String PREFS_NAME = "game_controls";

    // Claves para las preferencias
    public static final String KEY_MOVE_LEFT = "move_left";
    public static final String KEY_MOVE_RIGHT = "move_right";
    public static final String KEY_MOVE_UP = "move_up";
    public static final String KEY_MOVE_DOWN = "move_down";
    public static final String KEY_ATTACK = "attack";
    public static final String KEY_ABILITY = "ability";
    public static final String KEY_PAUSE = "pause";

    // Valores por defecto
    private static final int DEFAULT_MOVE_LEFT = Keys.A;
    private static final int DEFAULT_MOVE_RIGHT = Keys.D;
    private static final int DEFAULT_MOVE_UP = Keys.W;
    private static final int DEFAULT_MOVE_DOWN = Keys.S;
    private static final int DEFAULT_ATTACK = Keys.J;
    private static final int DEFAULT_PAUSE = Keys.ESCAPE;
    private static final int DEFAULT_ABILITY = Keys.K;

    private static KeyBindings instance;
    private Preferences prefs;

    // Controles actuales
    private int moveLeft;
    private int moveRight;
    private int moveUp;
    private int moveDown;
    private int attack;
    private int ability;
    private int pause;

    private KeyBindings() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
        loadBindings();
    }

    public static KeyBindings getInstance() {
        if (instance == null) {
            instance = new KeyBindings();
        }
        return instance;
    }

    /**
     * Carga los controles desde las preferencias guardadas.
     */
    private void loadBindings() {
        moveLeft = prefs.getInteger(KEY_MOVE_LEFT, DEFAULT_MOVE_LEFT);
        moveRight = prefs.getInteger(KEY_MOVE_RIGHT, DEFAULT_MOVE_RIGHT);
        moveUp = prefs.getInteger(KEY_MOVE_UP, DEFAULT_MOVE_UP);
        moveDown = prefs.getInteger(KEY_MOVE_DOWN, DEFAULT_MOVE_DOWN);
        attack = prefs.getInteger(KEY_ATTACK, DEFAULT_ATTACK);
        pause = prefs.getInteger(KEY_PAUSE, DEFAULT_PAUSE);
        ability = prefs.getInteger(KEY_ABILITY, DEFAULT_ABILITY);
    }

    /**
     * Guarda los controles actuales en las preferencias.
     */
    public void saveBindings() {
        prefs.putInteger(KEY_MOVE_LEFT, moveLeft);
        prefs.putInteger(KEY_MOVE_RIGHT, moveRight);
        prefs.putInteger(KEY_MOVE_UP, moveUp);
        prefs.putInteger(KEY_MOVE_DOWN, moveDown);
        prefs.putInteger(KEY_ATTACK, attack);
        prefs.putInteger(KEY_PAUSE, pause);
        prefs.putInteger(KEY_ABILITY, ability);
        prefs.flush();
    }

    /**
     * Restaura los controles a sus valores por defecto.
     */
    public void resetToDefaults() {
        moveLeft = DEFAULT_MOVE_LEFT;
        moveRight = DEFAULT_MOVE_RIGHT;
        moveUp = DEFAULT_MOVE_UP;
        moveDown = DEFAULT_MOVE_DOWN;
        attack = DEFAULT_ATTACK;
        ability = DEFAULT_ABILITY;
        pause = DEFAULT_PAUSE;
        saveBindings();
    }

    /**
     * Asigna una nueva tecla a una acción.
     * 
     * @param action  El nombre de la acción (usar las constantes KEY_*)
     * @param keycode El código de la tecla
     */
    public void setBinding(String action, int keycode) {
        switch (action) {
            case KEY_MOVE_LEFT:
                moveLeft = keycode;
                break;
            case KEY_MOVE_RIGHT:
                moveRight = keycode;
                break;
            case KEY_MOVE_UP:
                moveUp = keycode;
                break;
            case KEY_MOVE_DOWN:
                moveDown = keycode;
                break;
            case KEY_ATTACK:
                attack = keycode;
                break;
            case KEY_ABILITY:
                ability = keycode;
                break;
            case KEY_PAUSE:
                pause = keycode;
                break;
        }
        saveBindings();
    }

    /**
     * Obtiene el keycode asignado a una acción.
     */
    public int getBinding(String action) {
        switch (action) {
            case KEY_MOVE_LEFT:
                return moveLeft;
            case KEY_MOVE_RIGHT:
                return moveRight;
            case KEY_MOVE_UP:
                return moveUp;
            case KEY_MOVE_DOWN:
                return moveDown;
            case KEY_ATTACK:
                return attack;
            case KEY_ABILITY:
                return ability;
            case KEY_PAUSE:
                return pause;
            default:
                return -1;
        }
    }

    // Getters para un acceso rápido
    public int getAbility() {
        return ability;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public int getMoveRight() {
        return moveRight;
    }

    public int getMoveUp() {
        return moveUp;
    }

    public int getMoveDown() {
        return moveDown;
    }

    public int getAttack() {
        return attack;
    }

    public int getPause() {
        return pause;
    }

    /**
     * Convierte un keycode a su nombre legible.
     */
    public static String getKeyName(int keycode) {
        return Keys.toString(keycode);
    }
}
