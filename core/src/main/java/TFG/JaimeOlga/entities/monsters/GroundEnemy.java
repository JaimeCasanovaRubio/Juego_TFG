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
    private float leftBound, rightBound, upBound, downBound, leftDetection, rightDetection, upDetection, downDetection;
    private boolean facingRight = true;

    public GroundEnemy(float xPosition, float yPosition, int patrolRange, int detectionRange) {

        super(xPosition, yPosition);
        this.maxHealth = 4;
        this.health = maxHealth;

        // MOVIMIENTO
        this.leftBound = xPosition - patrolRange;
        this.rightBound = xPosition + patrolRange;
        this.upBound = yPosition + patrolRange;
        this.downBound = yPosition - patrolRange;

        // DETECCIÓN
        this.leftDetection = xPosition - detectionRange;
        this.rightDetection = xPosition + detectionRange;
        this.upDetection = yPosition + detectionRange;
        this.downDetection = yPosition - detectionRange;

        // Inicializar dirección de movimiento
        this.movingRight = true;
        this.movingUp = true;

        // Initialize hitbox (using similar dimensions to player for now)
        float hitboxWidth = 18 * SCALE;
        float hitboxHeight = 25 * SCALE;
        float spriteWidth = 32 * SCALE; // Assuming 32x32 sprites
        float spriteHeight = 32 * SCALE;
        initHitbox(hitboxWidth, hitboxHeight, spriteWidth, spriteHeight);

        loadAnimations();
    }

    protected void loadAnimations() {
        // Índice 0: RUN (para cuando se mueve)
        addAnimation(PLAYER_RUN, getSpriteCount(RUN), 0.07f);
        // Índice 1: IDLE (cuando está quieto)
        addAnimation(PLAYER_IDLE, getSpriteCount(IDLE), 0.07f);
        // Índice 2: HIT (cuando recibe daño)
        addAnimation(PLAYER_HIT, getSpriteCount(HIT), 0.07f);
    }

    public void update(float delta, CollisionManager collisionManager, Rectangle playerHitbox) {
        super.update(delta, collisionManager);

        // Actualizar la animación según el estado actual
        updateAnimation();

        checkZone(playerHitbox);
        patrol(delta, collisionManager);

        // Actualizar el hitbox después de mover
        updateHitbox();
    }

    @Override
    public void updateAnimation() {
        if (dead) {
            setAnimation(2); // Usar animación HIT para muerte
            return;
        }
        // Mostrar animación de daño mientras el temporizador esté activo
        if (hitAnimationTimer > 0) {
            setAnimation(2); // HIT
            return;
        }
        // Si se está moviendo, usar animación de correr
        setAnimation(0); // RUN (el enemigo siempre está patrullando)
    }

    public void patrol(float delta, CollisionManager collisionManager) {
        // Movimiento horizontal
        if (!collisionManager.checkCollisions(hitbox)) {
            if (movingRight) {
                facingRight = true;
                xPosition += MONSTER_SPEED;
                if (xPosition >= rightBound) {
                    movingRight = false; // Dar la vuelta
                }
            } else {
                facingRight = false;
                xPosition -= MONSTER_SPEED;
                if (xPosition <= leftBound) {
                    movingRight = true; // Dar la vuelta
                }
            }
        }

        // Movimiento vertical
        if (!collisionManager.checkCollisions(hitbox)) {
            if (movingUp) {
                yPosition += MONSTER_SPEED;
                if (yPosition >= upBound) {
                    movingUp = false; // Dar la vuelta
                }
            } else {
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
        // Centro del enemigo para referencia
        float enemyCenterX = xPosition + hitbox.getWidth() / 2;
        float enemyCenterY = yPosition + hitbox.getHeight() / 2;

        // Zona a la izquierda del enemigo (desde leftBound hasta el enemigo)
        // Cubre toda la altura de la zona de patrullaje
        float leftZoneWidth = enemyCenterX - leftDetection;
        float zoneHeight = upDetection - downDetection; // Altura total de la zona de patrullaje
        Rectangle leftZone = new Rectangle(leftDetection, downDetection, leftZoneWidth, zoneHeight);

        // Zona a la derecha del enemigo (desde el enemigo hasta rightBound)
        float rightZoneWidth = rightDetection - enemyCenterX;
        Rectangle rightZone = new Rectangle(enemyCenterX, downDetection, rightZoneWidth, zoneHeight);

        // Zona arriba del enemigo (desde el centro del enemigo hasta upBound)
        // Cubre toda la anchura de la zona de patrullaje
        float zoneWidth = rightDetection - leftDetection;
        float upZoneHeight = upDetection - enemyCenterY;
        Rectangle upZone = new Rectangle(leftDetection, enemyCenterY, zoneWidth, upZoneHeight);

        // Zona abajo del enemigo (desde downBound hasta el centro del enemigo)
        float downZoneHeight = enemyCenterY - downDetection;
        Rectangle downZone = new Rectangle(leftDetection, downDetection, zoneWidth, downZoneHeight);

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

    @Override
    public void takeDamage(int damage) {
        if (!invincible) {
            hitAnimationTimer = 0.5f; // Mostrar animación de daño durante 0.5 segundos
            super.takeDamage(damage);
            stateTime = 0f; // Reiniciar la animación desde el principio
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
