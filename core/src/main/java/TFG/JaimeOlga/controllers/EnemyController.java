package TFG.JaimeOlga.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import static TFG.JaimeOlga.utils.Cons.SCALE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import TFG.JaimeOlga.entities.Entity;
import TFG.JaimeOlga.entities.monsters.GroundEnemy;

public class EnemyController {
    private ArrayList<Entity> enemies;

    public EnemyController() {
        this.enemies = new ArrayList<>();
    }

    /**
     * Carga los enemigos desde la capa "enemies" del mapa Tiled.
     * Cada objeto debe tener las propiedades:
     * - type: "ground", "flying", etc.
     * - patrolRange: rango de patrulla en píxeles
     */
    public void loadEnemies(TiledMap map) {
        // Limpiar enemigos anteriores si se carga un nuevo mapa
        enemies.clear();

        MapLayer enemyLayer = map.getLayers().get("enemies");

        if (enemyLayer == null) {
            System.out.println("⚠️ AVISO: No se encontró la capa 'enemies' en el mapa.");
            System.out.println("   Crea una capa de objetos en Tiled llamada 'enemies'.");
            return;
        }

        MapObjects objects = enemyLayer.getObjects();

        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // Leer propiedades personalizadas del objeto en Tiled
                String type = object.getProperties().get("type", String.class);
                int patrolRange = object.getProperties().get("patrolRange", 100, Integer.class);
                int detectionRange = object.getProperties().get("detectionRange", 200, Integer.class);

                // Escalar posición
                float x = rect.x * SCALE;
                float y = rect.y * SCALE;

                // Crear el tipo de enemigo correspondiente
                switch (type.toLowerCase()) {
                    case "ground":
                        enemies.add(new GroundEnemy(x, y, patrolRange, detectionRange));
                        break;
                    // Añadir más tipos aquí:
                    // case "flying":
                    // enemies.add(new FlyingEnemy(x, y));
                    // break;
                    // case "boss":
                    // enemies.add(new BossEnemy(x, y));
                    // break;
                    default:
                        System.out.println("⚠️ Tipo de enemigo desconocido: " + type);
                        break;
                }
            }
        }
        System.out.println("✓ Cargados " + enemies.size() + " enemigos.");
    }

    /**
     * Actualiza todos los enemigos.
     */
    public void update(float delta, CollisionManager collisionManager, Rectangle playerHitbox) {
        Iterator<Entity> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Entity enemy = iterator.next();
            enemy.update(delta, collisionManager, playerHitbox);

            // Eliminar enemigos muertos (opcional)
            if (enemy.isDead()) {
                iterator.remove();
            }
        }
    }

    /**
     * Dibuja todos los enemigos.
     */
    public void draw(SpriteBatch batch) {
        for (Entity enemy : enemies) {
            if (enemy instanceof GroundEnemy) {
                ((GroundEnemy) enemy).draw(batch);
            }
        }
    }

    /**
     * Verifica si el jugador colisiona con algún enemigo.
     * 
     * @return El enemigo con el que colisionó, o null si no hay colisión.
     */
    public Entity checkPlayerCollision(Rectangle playerHitbox) {
        for (Entity enemy : enemies) {
            if (enemy.getHitbox() != null && enemy.getHitbox().overlaps(playerHitbox)) {
                return enemy;
            }
        }
        return null;
    }

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }

    public void dispose() {
        enemies.clear();
    }
}
