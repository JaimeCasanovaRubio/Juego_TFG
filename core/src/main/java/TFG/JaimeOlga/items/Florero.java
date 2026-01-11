package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;
import static TFG.JaimeOlga.utils.Cons.Images.*;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.entities.Player;

public class Florero extends Item {

    public Florero(float xPosition, float yPosition) {
        super(xPosition, yPosition);
        collectable = false;
    }

    @Override
    protected void loadTexture() {
        texture = new Texture(FLORERO);
    }

    @Override
    public void applyEffect(Player player) {
        // TODO Auto-generated method stub
    }

    @Override
    public void applyEffect(GameController game) {
        // TODO Auto-generated method stub
    }

    @Override
    public void applyEffect(GameController game, Player player) {
        // TODO Auto-generated method stub
    }

}
