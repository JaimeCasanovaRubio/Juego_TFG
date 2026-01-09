package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;
import static TFG.JaimeOlga.utils.Cons.Images.*;

import TFG.JaimeOlga.Main;
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
        player.setDamage(2);
    }

    @Override
    public void applyEffect(Main game) {
        // TODO Auto-generated method stub
    }

    @Override
    public void applyEffect(Main game, Player player) {
        // TODO Auto-generated method stub
    }
}
