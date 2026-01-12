package TFG.JaimeOlga.screens.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.controllers.EnemyController;
import TFG.JaimeOlga.controllers.InputController;
import TFG.JaimeOlga.controllers.ItemController;
import TFG.JaimeOlga.controllers.MapController;
import TFG.JaimeOlga.entities.Entity;
import TFG.JaimeOlga.entities.Player;

import static TFG.JaimeOlga.utils.Cons.Images.*;

public class OniricForestScreen implements Screen {

    private GameController game;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera; // Cámara fija para el HUD
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private Player player;
    private ItemController itemController;
    private MapController mapController;
    private EnemyController enemyController;
    private InputController inputController;

    public OniricForestScreen(GameController game, Player player) {
        this.game = game;

        this.player = player;
        inputController = new InputController(player, game);
        mapController = new MapController(game);

        itemController = mapController.getItemController();
        enemyController = mapController.getEnemyController();

        // Cargar el mapa de Tiled
        mapController.loadMap(MAP_ZONE1);

        // Configurar cámara y viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);

        // Configurar cámara HUD (fija, no se mueve con el jugador)
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);

        // Inicializar ShapeRenderer para dibujar formas (barras de vida)
        shapeRenderer = new ShapeRenderer();
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

        draw();
    }

    public void update(float delta) {

        // IMPORTANTE: Actualizar jugador PRIMERO para que su hitbox esté actualizada
        player.update(delta, mapController.getCollisionManager());

        // Actualizar enemigos (cargados desde Tiled) - ahora usan la hitbox actualizada
        // del jugador
        enemyController.update(delta, mapController.getCollisionManager(), player.getHitbox());

        // Actualizar items (detectar colisiones y recoger)
        itemController.update(player);

        // Comprobar colisiones jugador-enemigos
        checkPlayerEnemyCollisions();

        if (player.isDead()) {
            game.setScreen(game.deadScreen);
        }

        camera.position.x = player.getxPosition();
        camera.position.y = player.getyPosition();
        camera.update();
    }

    private void draw() {
        game.batch.begin();

        player.draw(game.batch);
        itemController.draw(game.batch);
        enemyController.draw(game.batch);

        game.batch.end();

        // Dibujar hitboxes de debug si F3 está activado
        if (InputController.debugMode) {
            drawDebugHitboxes();
        }

        // Dibujar HUD (vida del jugador) después de todo lo demás
        drawPlayerHealth();
    }

    /**
     * Dibuja las hitboxes de debug cuando F3 está activado.
     */
    private void drawDebugHitboxes() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Hitbox del jugador (verde)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(
                player.getHitbox().x,
                player.getHitbox().y,
                player.getHitbox().width,
                player.getHitbox().height);

        // Hitboxes de enemigos (rojo)
        shapeRenderer.setColor(Color.RED);
        for (Entity enemy : enemyController.getEnemies()) {
            if (enemy.getHitbox() != null) {
                shapeRenderer.rect(
                        enemy.getHitbox().x,
                        enemy.getHitbox().y,
                        enemy.getHitbox().width,
                        enemy.getHitbox().height);
            }
        }

        // Hitboxes de items (azul)
        shapeRenderer.setColor(Color.BLUE);
        for (var item : itemController.getItems()) {
            if (item.getHitbox() != null) {
                shapeRenderer.rect(
                        item.getHitbox().x,
                        item.getHitbox().y,
                        item.getHitbox().width,
                        item.getHitbox().height);
            }
        }

        // Colisiones del mapa (morado)
        shapeRenderer.setColor(Color.PURPLE);
        for (var rect : mapController.getCollisionManager().getCollisionRects()) {
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }

        shapeRenderer.end();
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
                    enemy.takeDamage(player.getDamage());
                } else {
                    // Jugador no atacando -> daño al jugador
                    player.takeDamage(enemy.getDamage());
                }
            }
        }
    }

    /**
     * Dibuja la barra de vida del jugador en la esquina superior izquierda.
     * Usa ShapeRenderer con una cámara HUD fija.
     */
    private void drawPlayerHealth() {
        // Usar la cámara HUD para que la vida esté fija en pantalla
        shapeRenderer.setProjectionMatrix(hudCamera.combined);

        // Configuración de la barra de vida
        float barX = 20; // Posición X (margen izquierdo)
        float barY = GAME_HEIGHT - 40; // Posición Y (arriba con margen)
        float barWidth = 200; // Ancho total de la barra
        float barHeight = 20; // Alto de la barra

        // Calcular el porcentaje de vida actual
        float healthPercent = (float) player.getHealth() / player.getMaxHealth();

        // Dibujar fondo de la barra (gris oscuro)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        // Dibujar la vida actual (rojo)
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX, barY, barWidth * healthPercent, barHeight);
        shapeRenderer.end();

        // Dibujar borde de la barra (blanco)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.end();
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
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}