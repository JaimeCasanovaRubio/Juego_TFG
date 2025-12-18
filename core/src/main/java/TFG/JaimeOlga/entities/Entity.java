package TFG.JaimeOlga.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import TFG.JaimeOlga.controllers.CollisionManager;
import static TFG.JaimeOlga.utils.Cons.*;

public class Entity {
    protected ArrayList<Animation<TextureRegion>> animations;
    protected ArrayList<Texture> textures;
    protected int currentAnimation;
    protected float stateTime;
    protected boolean movingRight;

    protected Rectangle hitbox;
    protected float hitboxOffsetX;
    protected float hitboxOffsetY;

    protected float xPosition;
    protected float yPosition;

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected boolean invincible = false;
    protected float invincibleTimer = 0;

    boolean attack = false;
    int hitAnimationTimer;
    int attackTimer;

    public Entity(float xPosition, float yPosition) {
        this.dead = false;
        this.maxHealth = 3;
        this.health = maxHealth;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.currentAnimation = 0;
        this.stateTime = 0f;
        this.textures = new ArrayList<>();
        this.animations = new ArrayList<>();
    }

    protected void loadAnimations() {

    }

    // Añade una animación desde un spritesheet horizontal
    protected void addAnimation(String path, int frameCount, float frameDuration) {
        Texture sheet = new Texture(path);
        textures.add(sheet);

        int frameWidth = sheet.getWidth() / frameCount;
        int frameHeight = sheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);
        animations.add(new Animation<>(frameDuration, tmp[0]));
    }

    // Cambiar animación por índice
    protected void setAnimation(int index) {
        if (index >= 0 && index < animations.size() && currentAnimation != index) {
            currentAnimation = index;
            stateTime = 0f; // Reiniciar desde el primer frame
        }
    }

    // HITBOX
    /**
     * Inicializa el hitbox con un tamaño específico.
     * Los offsets centran el hitbox dentro del sprite.
     * 
     * @param width        Ancho del hitbox en píxeles del mundo
     * @param height       Alto del hitbox en píxeles del mundo
     * @param spriteWidth  Ancho del sprite escalado
     * @param spriteHeight Alto del sprite escalado
     */
    protected void initHitbox(float width, float height, float spriteWidth, float spriteHeight) {
        // Calcular offset para centrar el hitbox horizontalmente
        this.hitboxOffsetX = (spriteWidth - width) / 2f;
        // Offset Y = 0 para que el hitbox esté anclado en los pies
        // Cuando la altura cambie, solo varía la parte superior
        this.hitboxOffsetY = 1 * SCALE;

        this.hitbox = new Rectangle(
                xPosition + hitboxOffsetX,
                yPosition + hitboxOffsetY,
                width,
                height);
    }

    public void update(float deltaTime, CollisionManager collisionManager) {

        if (dead) {
            System.out.println("Muerto");

        }

        if (attackTimer > 0) {
            attackTimer -= deltaTime;
            if (attackTimer <= 0) {
                attack = false;
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
        updatePosition(collisionManager);
        stateTime += deltaTime;

    }

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

    }

    public void updatePosition(CollisionManager collisionManager) {
    }

    /**
     * Sincroniza la posición del hitbox con la posición de la entidad.
     * IMPORTANTE: Llamar esto DESPUÉS de mover la entidad.
     */
    protected void updateHitbox() {
        if (hitbox != null) {
            hitbox.setPosition(xPosition + hitboxOffsetX, yPosition + hitboxOffsetY);
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    // Gestión daño
    public void takeDamage(int damage) {
        if (!invincible) {
            health -= damage;
            invincible = true;
            invincibleTimer = 1f; // 1 segundos de invencibilidad
            if (health <= 0) {
                dead = true;
            }
        }
    }

    public void update(float delta, CollisionManager collisionManager, Rectangle playerHitbox) {
    }

    public boolean isDead() {
        return dead;
    }

}
