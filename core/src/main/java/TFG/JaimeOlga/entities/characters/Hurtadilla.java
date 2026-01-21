package TFG.JaimeOlga.entities.characters;

import static TFG.JaimeOlga.utils.Cons.Images.*;

import TFG.JaimeOlga.entities.Player;

public class Hurtadilla extends Player {

    public Hurtadilla(float xPosition, float yPosition) {
        super(xPosition, yPosition);
    }

    public Hurtadilla() {
        super();
        ABILITY_COOLDOWN = 1f;
        ABILITY_DURATION = 0.1f;
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

    /**
     * Habilidad de Hurtadilla: DASH
     * Se mueve rápidamente en la última dirección.
     */
    @Override
    protected void executeAbility() {
        float dashMultiplier = 4f; // Velocidad del dash (4x la velocidad normal)

        // Activar movimiento de habilidad
        abilityAffectsMovement = true;

        // Configurar la dirección del dash
        switch (lastDirection) {
            case "right":
                abilityMoveX = dashMultiplier * speed;
                abilityMoveY = 0;
                facingRight = false;
                break;
            case "left":
                abilityMoveX = -dashMultiplier * speed;
                abilityMoveY = 0;
                facingRight = true;
                break;
            case "up":
                abilityMoveX = 0;
                abilityMoveY = dashMultiplier * speed;
                break;
            case "down":
                abilityMoveX = 0;
                abilityMoveY = -dashMultiplier * speed;
                break;
        }
    }
}
