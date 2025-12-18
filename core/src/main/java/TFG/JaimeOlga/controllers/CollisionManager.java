package TFG.JaimeOlga.controllers;

import java.util.ArrayList;
import static TFG.JaimeOlga.utils.Cons.SCALE;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {
    private ArrayList<Rectangle> collisionRects;

    public CollisionManager() {
        this.collisionRects = new ArrayList<>();
    }

    public void loadCollitions(TiledMap map) {
        MapLayer collisionLayer = map.getLayers().get("collision");

        if (collisionLayer == null) {
            System.out.println("⚠️ AVISO: No se encontró la capa 'collision' en el mapa.");
            System.out.println("   Crea una capa de objetos en Tiled llamada 'collision'.");
            return;
        }

        // Obtener todos los objetos de la capa
        MapObjects objects = collisionLayer.getObjects();

        for (MapObject object : objects) {

            // Solo nos interesan los rectángulos
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // IMPORTANTE: Escalar el rectángulo
                // En Tiled los valores están en píxeles del tile,
                // pero en el juego usamos escala
                Rectangle scaledRect = new Rectangle(
                        rect.x * SCALE,
                        rect.y * SCALE,
                        rect.width * SCALE,
                        rect.height * SCALE);

                collisionRects.add(scaledRect);
            }
        }

        System.out.println("✓ Cargados " + collisionRects.size() + " rectángulos de colisión.");
    }

    public boolean checkCollisions(Rectangle hitbox) {
        for (Rectangle rect : collisionRects) {
            if (hitbox.overlaps(rect)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Rectangle> getCollisionRects() {
        return collisionRects;
    }
}