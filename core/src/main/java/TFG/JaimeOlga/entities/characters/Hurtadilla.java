package TFG.JaimeOlga.entities.characters;

import static TFG.JaimeOlga.utils.Cons.Images.*;

import TFG.JaimeOlga.entities.Player;

public class Hurtadilla extends Player {
    private final float ABILITY_COOLDOWN = 2f;

    public Hurtadilla(float xPosition, float yPosition) {
        super(xPosition, yPosition);
    }

    public Hurtadilla() {
        super();
        loadAnimations();
    }

    @Override
    public void loadAnimations() {

        addAnimation(PLAYER_RUN, getSpriteCount(RUN), 0.07f);

        addAnimation(PLAYER_IDLE, getSpriteCount(IDLE), 0.07f);

        addAnimation(PLAYER_HIT, getSpriteCount(HIT), 0.07f);

        addAnimation(PLAYER_JUMP, getSpriteCount(JUMP), 0.07f);

        addAnimation(PLAYER_WALL_JUMP, getSpriteCount(WALL_JUMP), 0.07f);

        addAnimation(PLAYER_DOUBLE_JUMP, getSpriteCount(DOUBLE_JUMP), 0.07f);

        addAnimation(PLAYER_FALL, getSpriteCount(FALL), 0.07f);
    }
}
