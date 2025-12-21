package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;

import TFG.JaimeOlga.Main;
import TFG.JaimeOlga.entities.Player;

public class Stone extends Item {

    public Stone(float x, float y) {
        super(x, y);
    }

    @Override
    protected void loadTexture() {
        texture = new Texture("assets/tileSet/3 Objects/Stones/3.png");

    }

    @Override
    public void applyEffect(Main game) {
        game.changeScreen(game.settingsMenu);
    }

    @Override
    public void applyEffect(Player player) {

    }

}
