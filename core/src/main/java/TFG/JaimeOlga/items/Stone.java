package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.entities.Player;
import static TFG.JaimeOlga.utils.Cons.Images.*;

public class Stone extends Item {

    public Stone(float x, float y) {
        super(x, y);
    }

    @Override
    protected void loadTexture() {
        texture = new Texture(STONE);

    }

    @Override
    public void applyEffect(GameController game, Player player) {
        player.setxPosition(600);
        player.setyPosition(400);
        game.changeScreen(game.oniricForestScreen);
    }

}
