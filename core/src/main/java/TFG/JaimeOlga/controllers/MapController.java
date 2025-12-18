package TFG.JaimeOlga.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import static TFG.JaimeOlga.utils.Cons.SCALE;

import java.util.ArrayList;

public class MapController {
    private ArrayList<TiledMap> maps;
    private int currentMapIndex;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TmxMapLoader mapLoader;
    private OrthographicCamera camera;
    private CollisionManager collisionManager;
    private EnemyController enemyController;
    private ItemController itemController;

    public MapController() {
        mapLoader = new TmxMapLoader();
        collisionManager = new CollisionManager();
        enemyController = new EnemyController();
        itemController = new ItemController();
        maps = new ArrayList<>();
        currentMapIndex = 0;
    }

    public void loadMap(String mapPath) {
        if (map != null) {

        }
        if (renderer != null) {
            renderer.dispose();
        }
        maps.add(mapLoader.load(mapPath));
        map = maps.get(currentMapIndex);
        renderer = new OrthogonalTiledMapRenderer(map, SCALE);

        // Cargar colisiones, enemigos e items desde el mapa
        collisionManager.loadCollitions(map);
        enemyController.loadEnemies(map);
        itemController.loadItems(map);
    }

    public void render(OrthographicCamera mainCamera) {
        this.camera = mainCamera;
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        if (map != null) {
            map.dispose();
        }

        if (renderer != null) {
            renderer.dispose();
        }
        enemyController.dispose();
        itemController.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public EnemyController getEnemyController() {
        return enemyController;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public float getMapWidth() {
        int widthInTiles = map.getProperties().get("width", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        return widthInTiles * tileWidth * SCALE;
    }

    public float getMapHeight() {
        int heightInTiles = map.getProperties().get("height", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);
        return heightInTiles * tileHeight * SCALE;
    }
}
