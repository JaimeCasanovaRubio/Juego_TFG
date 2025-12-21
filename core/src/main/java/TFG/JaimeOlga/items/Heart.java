package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;

import TFG.JaimeOlga.Main;
import TFG.JaimeOlga.entities.Player;

/**
 * Item que recupera vida del jugador.
 */
public class Heart extends Item {
    private int healAmount;

    public Heart(float xPosition, float yPosition) {
        super(xPosition, yPosition);
        this.healAmount = 1; // Recupera 1 punto de vida
    }

    public Heart(float xPosition, float yPosition, int healAmount) {
        super(xPosition, yPosition);
        this.healAmount = healAmount;
    }

    @Override
    protected void loadTexture() {
        // TODO: Cambiar por la ruta real del sprite
        // texture = new Texture("items/heart.png");
        System.out.println("⚠️ Heart: Falta sprite 'items/heart.png'");
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(healAmount);
        System.out.println("💖 +1 vida");
    }

    @Override
    public void applyEffect(Main game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyEffect'");
    }

    @Override
    public void applyEffect(Main game, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyEffect'");
    }
}
