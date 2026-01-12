package TFG.JaimeOlga.controllers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import TFG.JaimeOlga.entities.Player;
import TFG.JaimeOlga.GameController;

public class InputController implements InputProcessor {
    private Player player;
    private GameController game;
    public static boolean debugMode = false; // Toggle con F3

    public InputController(Player player, GameController game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        // MOVIMIENTO
        if (keycode == Keys.A) {
            player.setLeft(true);
        } else if (keycode == Keys.D) {
            player.setRight(true);
        } else if (keycode == Keys.W) {
            player.setUp(true);
        } else if (keycode == Keys.S) {
            player.setDown(true);
        } else if (keycode == Keys.J) {
            player.attack(); // Inicia el ataque con su timer
        } else if (keycode == Keys.F3) {
            debugMode = !debugMode;
            System.out.println("Debug mode: " + (debugMode ? "ON" : "OFF"));
        }
        if (keycode == Keys.ESCAPE) {
            if (game.getScreen() == game.oniricForestScreen ||
                    game.getScreen() == game.baseScreen) {
                game.changeScreen(game.settingsMenu);
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A) {
            player.setLeft(false);
        } else if (keycode == Keys.D) {
            player.setRight(false);
        } else if (keycode == Keys.W) {
            player.setUp(false);
        } else if (keycode == Keys.S) {
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
