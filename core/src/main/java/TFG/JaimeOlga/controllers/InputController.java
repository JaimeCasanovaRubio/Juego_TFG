package TFG.JaimeOlga.controllers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import TFG.JaimeOlga.entities.Player;
import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.utils.KeyBindings;

public class InputController implements InputProcessor {
    private Player player;
    private GameController game;
    private KeyBindings keyBindings;
    public static boolean debugMode = false; // Toggle con F3

    public InputController(Player player, GameController game) {
        this.player = player;
        this.game = game;
        this.keyBindings = KeyBindings.getInstance();
    }

    @Override
    public boolean keyDown(int keycode) {
        // MOVIMIENTO (usando KeyBindings configurables)
        if (keycode == keyBindings.getMoveLeft()) {
            player.setLeft(true);
        } else if (keycode == keyBindings.getMoveRight()) {
            player.setRight(true);
        } else if (keycode == keyBindings.getMoveUp()) {
            player.setUp(true);
        } else if (keycode == keyBindings.getMoveDown()) {
            player.setDown(true);
        } else if (keycode == keyBindings.getAttack()) {
            player.attack(); // Inicia el ataque con su timer
        } else if (keycode == Keys.F3) {
            debugMode = !debugMode;
            System.out.println("Debug mode: " + (debugMode ? "ON" : "OFF"));
        }
        if (keycode == keyBindings.getPause()) {
            if (game.getScreen() == game.oniricForestScreen ||
                    game.getScreen() == game.baseScreen) {
                game.changeScreen(game.settingsMenuInGame);
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == keyBindings.getMoveLeft()) {
            player.setLeft(false);
        } else if (keycode == keyBindings.getMoveRight()) {
            player.setRight(false);
        } else if (keycode == keyBindings.getMoveUp()) {
            player.setUp(false);
        } else if (keycode == keyBindings.getMoveDown()) {
            player.setDown(false);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
