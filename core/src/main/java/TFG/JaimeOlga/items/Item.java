package TFG.JaimeOlga.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import TFG.JaimeOlga.Main;
import TFG.JaimeOlga.entities.Player;
import static TFG.JaimeOlga.utils.Cons.SCALE;

/**
 * Clase base abstracta para todos los items coleccionables.
 * Los items se cargan desde la capa "items" del mapa Tiled.
 */
public abstract class Item {
    protected float xPosition;
    protected float yPosition;
    protected float width;
    protected float height;
    protected Rectangle hitbox;
    protected Texture texture;
    protected boolean collected = false;
    public boolean collectable = true;

    public Item(float xPosition, float yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        loadTexture(); // Cargar textura primero
        // Usar las dimensiones reales de la textura
        if (texture != null) {
            this.width = texture.getWidth() * SCALE;
            this.height = texture.getHeight() * SCALE;
        } else {
            this.width = 16 * SCALE; // Tamaño por defecto si no hay textura
            this.height = 16 * SCALE;
        }
        initHitbox();
    }

    /**
     * Cada item carga su propia textura.
     */
    protected abstract void loadTexture();

    /**
     * Inicializa el hitbox del item.
     */
    protected void initHitbox() {
        hitbox = new Rectangle(xPosition, yPosition, width, height);
    }

    /**
     * Aplica el efecto del item al jugador.
     * Cada tipo de item implementa su propio efecto.
     */
    public abstract void applyEffect(Player player);

    /**
     * Aplica el efecto del item al juego, como para cambiar mapa o pantalla.
     * Cada tipo de item implementa su propio efecto.
     */
    public abstract void applyEffect(Main game);

    public abstract void applyEffect(Main game, Player player);

    /**
     * Llamado cuando el jugador colisiona con el item.
     */
    public void collect(Player player, Main game) {
        if (!collected) {
            applyEffect(player);
            applyEffect(game);
            applyEffect(game, player);
            collected = true;
            System.out.println("✓ Item recogido!");
        }
    }

    /**
     * Dibuja el item si no ha sido recogido.
     */
    public void draw(SpriteBatch batch) {
        if (!collected && texture != null) {
            batch.draw(texture, xPosition, yPosition, width, height);
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isCollected() {
        return collected;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

}
