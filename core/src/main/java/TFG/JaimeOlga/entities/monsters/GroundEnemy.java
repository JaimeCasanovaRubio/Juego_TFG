package TFG.JaimeOlga.entities.monsters;

import TFG.JaimeOlga.entities.Entity;
import static TFG.JaimeOlga.utils.Cons.*;
import static TFG.JaimeOlga.utils.Cons.Images.*;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import TFG.JaimeOlga.controllers.CollisionManager;

public class GroundEnemy extends Entity {
    private float leftBound, rightBound, upBound, downBound;
    private boolean facingRight = true;

    public GroundEnemy(float xPosition, float yPosition, int patrolRange) {

        super(xPosition, yPosition);
        this.maxHealth = 5;
        this.health = maxHealth;
        this.leftBound = xPosition - patrolRange;
        this.rightBound = xPosition + patrolRange;

        // Initialize hitbox (using similar dimensions to player for now)
        float hitboxWidth = 18 * SCALE;
        float hitboxHeight = 25 * SCALE;
        float spriteWidth = 32 * SCALE; // Assuming 32x32 sprites
        float spriteHeight = 32 * SCALE;
        initHitbox(hitboxWidth, hitboxHeight, spriteWidth, spriteHeight);

        loadAnimations();
    }

    protected void loadAnimations() {

        addAnimation(PLAYER_IDLE, getSpriteCount(IDLE), 0.07f);
    }

    public void update(float delta, CollisionManager collisionManager, Rectangle playerHitbox) {
        super.update(delta, collisionManager);

        checkZone(playerHitbox);
        patrol(delta, collisionManager);

        // Actualizar el hitbox después de mover
        updateHitbox();
    }

    public void patrol(float delta, CollisionManager collisionManager) {
        // Mover en la dirección actual
        if (movingRight && !collisionManager.checkCollisions(hitbox)) {
            facingRight = true;
            xPosition += MONSTER_SPEED;
            if (xPosition >= rightBound) {
                movingRight = false; // Dar la vuelta
            }
        } else {
            facingRight = false;
            if (!collisionManager.checkCollisions(hitbox)) {
                xPosition -= MONSTER_SPEED;
                if (xPosition <= leftBound) {
                    movingRight = true; // Dar la vuelta
                }
            }
        }
        if (movingUp && !collisionManager.checkCollisions(hitbox)) {
            yPosition += MONSTER_SPEED;
            if (yPosition >= upBound) {
                movingUp = false; // Dar la vuelta
            }
        } else {
            if (!collisionManager.checkCollisions(hitbox)) {
                yPosition -= MONSTER_SPEED;
                if (yPosition <= downBound) {
                    movingUp = true; // Dar la vuelta
                }
            }
        }
    }

    /**
     * Detecta si el jugador está en la zona de visión del enemigo.
     * 
     * @param playerHitbox El hitbox del jugador
     * @return true si el jugador fue detectado
     */
    public void checkZone(Rectangle playerHitbox) {
        // Zona a la izquierda del enemigo (desde leftBound hasta la posición del
        // enemigo)
        float leftZoneWidth = xPosition - leftBound;
        Rectangle leftZone = new Rectangle(leftBound, yPosition, leftZoneWidth, hitbox.getHeight());

        // Zona a la derecha del enemigo (desde la posición del enemigo hasta
        // rightBound)
        float rightZoneWidth = rightBound - xPosition;
        Rectangle rightZone = new Rectangle(xPosition + hitbox.getWidth(), yPosition, rightZoneWidth,
                hitbox.getHeight());

        // Zona arriba del enemigo (desde la posición del enemigo hasta upBound)
        float upZoneWidth = yPosition - upBound;
        Rectangle upZone = new Rectangle(xPosition, upBound, hitbox.getWidth(), upZoneWidth);

        // Zona abajo del enemigo (desde la posición del enemigo hasta downBound)
        float downZoneWidth = downBound - yPosition;
        Rectangle downZone = new Rectangle(xPosition, downBound, hitbox.getWidth(), downZoneWidth);

        // Comprobar si el jugador está en alguna zona
        if (leftZone.overlaps(playerHitbox)) {
            movingRight = false; // Ir hacia la izquierda donde está el jugador
        }
        if (rightZone.overlaps(playerHitbox)) {
            movingRight = true; // Ir hacia la derecha donde está el jugador
        }
        if (upZone.overlaps(playerHitbox)) {
            movingUp = true; // Ir hacia arriba donde está el jugador
        }
        if (downZone.overlaps(playerHitbox)) {
            movingUp = false; // Ir hacia abajo donde está el jugador
        }
    }

    public void draw(SpriteBatch batch) {
        Animation<TextureRegion> anim = animations.get(currentAnimation);
        TextureRegion frame = anim.getKeyFrame(stateTime, true);

        float width = frame.getRegionWidth() * SCALE;
        float height = frame.getRegionHeight() * SCALE;
        // Invertir el frame si mira a la izquierda y no está ya invertido, o viceversa
        boolean needsFlip = (facingRight && frame.isFlipX()) || (!facingRight && !frame.isFlipX());
        if (needsFlip) {
            frame.flip(true, false);
        }
        batch.draw(frame, xPosition, yPosition, width, height);
    }

}
