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
import TFG.JaimeOlga.entities.Player;
import TFG.JaimeOlga.items.Coin;
import TFG.JaimeOlga.items.Heart;
import TFG.JaimeOlga.items.Item;
import TFG.JaimeOlga.items.Stone;
import TFG.JaimeOlga.Main;

public class ItemController {
    private ArrayList<Item> items;
    private Main game;

    public ItemController(Main game) {
        this.items = new ArrayList<>();
        this.game = game;
    }

    /**
     * Carga los items desde la capa "items" del mapa Tiled.
     * Cada objeto debe tener la propiedad:
     * - type: "coin", "heart", "speedboost", etc.
     * - value: (opcional) valor del item (puntos, cantidad de vida, etc.)
     */
    public void loadItems(TiledMap map) {
        // Limpiar items anteriores si se carga un nuevo mapa
        items.clear();

        MapLayer itemLayer = map.getLayers().get("items");

        if (itemLayer == null) {
            System.out.println("⚠️ AVISO: No se encontró la capa 'items' en el mapa.");
            System.out.println("   Crea una capa de objetos en Tiled llamada 'items'.");
            return;
        }

        MapObjects objects = itemLayer.getObjects();

        for (MapObject object : objects) {

            if (object instanceof RectangleMapObject) {

                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                // Leer propiedades personalizadas del objeto en Tiled
                String type = object.getProperties().get("type", String.class);
                int value = object.getProperties().get("value", 1, Integer.class);

                // Escalar posición
                float x = rect.x * SCALE;
                float y = rect.y * SCALE;

                // Crear el tipo de item correspondiente
                switch (type.toLowerCase()) {
                    case "coin":
                        items.add(new Coin(x, y, value * 10)); // value * 10 = puntos
                        break;
                    case "heart":
                        items.add(new Heart(x, y, value)); // value = vida a recuperar
                        break;
                    case "stone":
                        items.add(new Stone(x, y));
                        break;
                    // Añadir más tipos aquí:
                    // case "speedboost":
                    // items.add(new SpeedBoost(x, y, value));
                    // break;
                    default:
                        System.out.println("⚠️ Tipo de item desconocido: " + type);
                        break;
                }
            }
        }
        System.out.println("✓ Cargados " + items.size() + " items.");
    }

    /**
     * Comprueba colisiones con el jugador y recoge items.
     */
    public void update(Player player) {
        Rectangle playerHitbox = player.getHitbox();

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();

            // Si el jugador toca el item, recogerlo
            if (!item.isCollected() && item.getHitbox().overlaps(playerHitbox)) {
                item.collect(player, game);

            }

            // Eliminar items recogidos de la lista
            if (item.isCollected()) {
                item.dispose();
                iterator.remove();
            }
        }
    }

    /**
     * Dibuja todos los items no recogidos.
     */
    public void draw(SpriteBatch batch) {
        for (Item item : items) {
            item.draw(batch);
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void dispose() {
        for (Item item : items) {
            item.dispose();
        }
        items.clear();
    }
}
