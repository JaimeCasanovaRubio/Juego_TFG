package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;
import static TFG.JaimeOlga.utils.Cons.Images.*;

public class Florero extends Item {

    public Florero(float xPosition, float yPosition) {
        super(xPosition, yPosition);
        collectable = false;
    }

    @Override
    protected void loadTexture() {
        texture = new Texture(FLORERO);
    }

}
