package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.entities.Player;

/**
 * Item que da puntos al jugador.
 */
public class Coin extends Item {
    private int value;

    public Coin(float xPosition, float yPosition) {
        super(xPosition, yPosition);
        this.value = 10; // Valor por defecto
    }

    public Coin(float xPosition, float yPosition, int value) {
        super(xPosition, yPosition);
        this.value = value;
    }

    @Override
    protected void loadTexture() {
        // TODO: Cambiar por la ruta real del sprite
        // texture = new Texture("items/coin.png");
        System.out.println("⚠️ Coin: Falta sprite 'items/coin.png'");
    }

    @Override
    public void applyEffect(Player player) {
        // TODO: Añadir método addScore(int) a Player
        // player.addScore(value);
        System.out.println("🪙 +" + value + " puntos");
    }

    @Override
    public void applyEffect(GameController game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyEffect'");
    }

    @Override
    public void applyEffect(GameController game, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyEffect'");
    }

}
