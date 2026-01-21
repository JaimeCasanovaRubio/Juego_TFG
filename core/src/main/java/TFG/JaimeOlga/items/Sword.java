package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;
import static TFG.JaimeOlga.utils.Cons.Images.*;

import TFG.JaimeOlga.entities.Player;

public class Sword extends Item {

    public Sword(float xPosition, float yPosition) {
        super(xPosition, yPosition);
    }

    @Override
    protected void loadTexture() {
        texture = new Texture(SWORD);
    }

    @Override
    public void applyEffect(Player player) {
        player.setDamage(player.getDamage() + 1);
    }

}
