package TFG.JaimeOlga.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import TFG.JaimeOlga.controllers.CollisionManager;

import static TFG.JaimeOlga.utils.Cons.*;
import static TFG.JaimeOlga.utils.Cons.Images.*;

public class Player extends Entity {

    // Variables heredadas de Entity: currentAnimation, stateTime, animations, etc.
    protected boolean right, left, up, down;
    protected boolean facingRight = true; // Por defecto mira a la derecha
    protected String lastDirection = "right";

    protected boolean attack = false;
    protected float speed;
    protected float hitAnimationTimer = 0; // Tiempo que dura la animación de daño
    protected float attackTimer = 0; // Tiempo que dura la animación de ataque

    protected float ABILITY_COOLDOWN = 1f;
    protected float ABILITY_DURATION = 0.1f;
    protected float abilityCooldown = 0;
    protected float abilityTimer = 0;
    protected boolean abilityActive = false;
    protected boolean canUseAbility = true;

    // Para habilidades que afectan al movimiento (como dash)
    protected boolean abilityAffectsMovement = false;
    protected float abilityMoveX = 0;
    protected float abilityMoveY = 0;

    // Constructor y carga animaciones
    public Player(float xPosition, float yPosition) {
        super(xPosition, yPosition);

        facingRight = true;
        speed = PLAYER_SPEED;

        float hitboxWidth = 18 * SCALE; // Más estrecho que el width del sprite
        float hitboxHeight = 25 * SCALE; // Casi igual de alto que el height del sprite
        float spriteWidth = PLAYER_WIDTH * SCALE; // Sprite escalado
        float spriteHeight = PLAYER_HEIGHT * SCALE;
        initHitbox(hitboxWidth, hitboxHeight, spriteWidth, spriteHeight);
        loadAnimations();
    }

    public Player() {
        super();
        facingRight = true;
        speed = PLAYER_SPEED;

        float hitboxWidth = 18 * SCALE;
        float hitboxHeight = 25 * SCALE;
        float spriteWidth = PLAYER_WIDTH * SCALE;
        float spriteHeight = PLAYER_HEIGHT * SCALE;
        initHitbox(hitboxWidth, hitboxHeight, spriteWidth, spriteHeight);
    }

    public void loadAnimations() {

        addAnimation(PLAYER_RUN, getSpriteCount(RUN), 0.07f);

        addAnimation(PLAYER_IDLE, getSpriteCount(IDLE), 0.07f);

        addAnimation(PLAYER_HIT, getSpriteCount(HIT), 0.07f);

        addAnimation(PLAYER_JUMP, getSpriteCount(JUMP), 0.07f);

        addAnimation(PLAYER_WALL_JUMP, getSpriteCount(WALL_JUMP), 0.07f);

        addAnimation(PLAYER_DOUBLE_JUMP, getSpriteCount(DOUBLE_JUMP), 0.07f);

        addAnimation(PLAYER_FALL, getSpriteCount(FALL), 0.07f);

    }

    // Update y Draw
    @Override
    public void update(float deltaTime, CollisionManager collisionManager) {

        if (attackTimer > 0) {
            attackTimer -= deltaTime;
            if (attackTimer <= 0) {
                attack = false;
            }
        }

        if (abilityCooldown > 0) {
            abilityCooldown -= deltaTime;
            if (abilityCooldown <= 0) {
                canUseAbility = true;
            }
        }
        if (abilityActive) {
            abilityTimer -= deltaTime;
            onAbilityUpdate(deltaTime); // Permite lógica adicional mientras la habilidad está activa
            if (abilityTimer <= 0) {
                abilityActive = false;
                abilityAffectsMovement = false;
                onAbilityEnd(); // Callback cuando termina la habilidad
            }
        }

        // Actualizar temporizador de animación de daño
        if (hitAnimationTimer > 0) {
            hitAnimationTimer -= deltaTime;
        }

        if (invincible) {
            invincibleTimer -= deltaTime;
            if (invincibleTimer <= 0) {
                invincible = false;
            }
        }

        updateAnimation();
        updatePosition(collisionManager);
        stateTime += deltaTime;

    }

    @Override
    public void updateAnimation() {
        if (dead) {
            setAnimation(2);
            return;
        }
        if (attackTimer > 0) {
            setAnimation(5);
            return;
        }
        // Mostrar animación de daño mientras el temporizador esté activo
        if (hitAnimationTimer > 0) {
            setAnimation(2);
            return; // Importante: no continuar para que no se sobrescriba
        }
        if (abilityActive) {
            setAnimation(4);
            return;
        }
        if (right || left) {
            setAnimation(0);
        } else if (up) {
            setAnimation(3);
        } else if (down) {
            setAnimation(6);
        } else {
            setAnimation(1);
        }
    }

    @Override
    public void updatePosition(CollisionManager collisionManager) {
        // --- MOVEMENT ---
        float nextX = xPosition;
        float nextY = yPosition;

        if (abilityActive && abilityAffectsMovement) {
            // Habilidad que afecta al movimiento (como dash)
            nextX += abilityMoveX;
            nextY += abilityMoveY;
        } else if (!abilityActive) {
            // Movimiento normal (solo si la habilidad no está activa)
            if (right) {
                nextX += speed;
                lastDirection = "right";
                facingRight = true;
            }
            if (left) {
                nextX -= speed;
                lastDirection = "left";
                facingRight = false;
            }
            if (up) {
                lastDirection = "up";
                nextY += speed;
            }
            if (down) {
                lastDirection = "down";
                nextY -= speed;
            }
        }

        // Si no hay sistema de colisiones o hitbox, mover directamente
        if (collisionManager == null || hitbox == null) {
            xPosition = nextX;
            yPosition = nextY;
            updateHitbox();
            return;
        }

        // Check Horizontal Collision
        Rectangle testHitbox = new Rectangle(hitbox);
        testHitbox.setPosition(nextX + hitboxOffsetX, yPosition + hitboxOffsetY);
        if (!collisionManager.checkCollisions(testHitbox)) {
            xPosition = nextX;
        }
        testHitbox.setPosition(xPosition + hitboxOffsetX, nextY + hitboxOffsetY);
        if (!collisionManager.checkCollisions(testHitbox)) {
            yPosition = nextY;
        }

        // Sincronizar hitbox con posición final
        updateHitbox();
    }

    /**
     * Ejecuta la habilidad especial del personaje.
     * Las clases hijas DEBEN sobrescribir este método para definir su propia
     * habilidad.
     * Se llama UNA VEZ cuando se activa la habilidad.
     * 
     * Ejemplos de lo que puede hacer:
     * - Movimiento (dash): setear abilityAffectsMovement = true y abilityMoveX/Y
     * - Curación: llamar a heal(amount)
     * - Escudo: activar invincible = true
     * - Disparo: crear un proyectil
     */
    protected void executeAbility() {
        // Implementación por defecto: no hace nada
    }

    /**
     * Llamado cada frame mientras la habilidad está activa.
     * Útil para efectos continuos durante la duración de la habilidad.
     */
    protected void onAbilityUpdate(float deltaTime) {
        // Por defecto: no hace nada
    }

    /**
     * Llamado cuando la habilidad termina.
     * Útil para limpiar efectos temporales.
     */
    protected void onAbilityEnd() {
        // Por defecto: no hace nada
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

    @Override
    public void takeDamage(int damage) {
        if (!invincible) {
            super.takeDamage(damage);
            hitAnimationTimer = 0.5f; // Mostrar animación de daño durante 0.5 segundos
            stateTime = 0f; // Reiniciar la animación desde el principio
        }
    }

    /**
     * Recupera vida del jugador.
     * 
     * @param amount Cantidad de vida a recuperar
     */
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
        System.out.println("💖 Vida actual: " + health + "/" + maxHealth);
    }

    public void attack() {
        // Solo iniciar ataque si no hay uno en progreso
        if (!attack) {
            attack = true;
            invincible = true;
            invincibleTimer = 0.5f;
            attackTimer = 0.5f;
            stateTime = 0f; // Reiniciar la animación desde el principio
        }
    }

    public void activateAbility() {
        if (canUseAbility) {
            abilityActive = true;
            canUseAbility = false;
            abilityCooldown = ABILITY_COOLDOWN;
            abilityTimer = ABILITY_DURATION;
            stateTime = 0f; // Reiniciar la animación desde el principio
            executeAbility(); // Ejecutar la habilidad específica del personaje
        }
    }

    // Getters y setters Exclusivos

    public float getAbilityCooldown() {
        return abilityCooldown;
    }

    public float getMaxAbilityCooldown() {
        return ABILITY_COOLDOWN;
    }

    public void setAbilityCooldown(float abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public void setAbilityActive(boolean abilityActive) {
        this.abilityActive = abilityActive;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

}