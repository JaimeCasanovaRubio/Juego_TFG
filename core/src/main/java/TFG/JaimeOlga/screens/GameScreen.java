package TFG.JaimeOlga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import TFG.JaimeOlga.Main;
import TFG.JaimeOlga.controllers.CollisionManager;
import TFG.JaimeOlga.controllers.EnemyController;
import TFG.JaimeOlga.controllers.InputController;
import TFG.JaimeOlga.controllers.ItemController;
import TFG.JaimeOlga.controllers.MapController;
import TFG.JaimeOlga.entities.Entity;
import TFG.JaimeOlga.entities.Player;
import static TFG.JaimeOlga.utils.Cons.*;

public class GameScreen implements Screen {

    private Main game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Player player;
    private ItemController itemController;
    private MapController mapController;
    private EnemyController enemyController;
    private InputController inputController;

    // DEBUG
    private ShapeRenderer shapeRenderer;
    private boolean debugMode = false;

    // Resolución del juego (acordar con Olga)
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;

    public GameScreen(Main game) {
        this.game = game;

        player = new Player(GAME_WIDTH / SCALE, GAME_HEIGHT / SCALE);
        inputController = new InputController(player, game);
        mapController = new MapController(game);

        itemController = mapController.getItemController();
        enemyController = mapController.getEnemyController();

        // Cargar el mapa de Tiled
        mapController.loadMap("maps/prueba1.tmx");

        // Debug renderer
        shapeRenderer = new ShapeRenderer();

        // Configurar cámara y viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);
    }

    @Override
    public void show() {
        // Registrar el InputController para que LibGDX escuche los eventos del teclado
        Gdx.input.setInputProcessor(inputController);
    }

    @Override
    public void render(float delta) {
        // 1. Actualizar lógica
        update(delta);

        // 2. Limpiar pantalla
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 3. Dibujar
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Renderizar el mapa de Tiled primero (fondo)
        mapController.render(camera);

        game.batch.begin();

        player.draw(game.batch);
        itemController.draw(game.batch);
        enemyController.draw(game.batch);

        game.batch.end();
    }

    public void update(float delta) {
        // Toggle debug con F3
        if (Gdx.input.isKeyJustPressed(Keys.F3)) {
            debugMode = !debugMode;
            System.out.println("Debug mode: " + (debugMode ? "ON" : "OFF"));
        }

        // Actualizar enemigos (cargados desde Tiled)
        enemyController.update(delta, mapController.getCollisionManager(), player.getHitbox());

        // Actualizar items (detectar colisiones y recoger)
        itemController.update(player);

        // Actualizar jugador
        player.update(delta, mapController.getCollisionManager());

        // Comprobar colisiones jugador-enemigos
        checkPlayerEnemyCollisions();

        camera.position.x = player.getxPosition();
        camera.position.y = player.getyPosition();
        camera.update();
    }

    /**
     * Comprueba colisiones entre el jugador y todos los enemigos.
     */
    private void checkPlayerEnemyCollisions() {
        for (Entity enemy : enemyController.getEnemies()) {
            if (enemy.getHitbox() == null)
                continue;

            if (player.getHitbox().overlaps(enemy.getHitbox())) {
                if (player.isAttack()) {
                    // Jugador atacando -> daño al enemigo
                    enemy.takeDamage(1);
                } else {
                    // Jugador no atacando -> daño al jugador
                    player.takeDamage(1);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}