package TFG.JaimeOlga.screens.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import TFG.JaimeOlga.Main;
import TFG.JaimeOlga.controllers.InputController;
import TFG.JaimeOlga.controllers.ItemController;
import TFG.JaimeOlga.controllers.MapController;
import TFG.JaimeOlga.entities.Player;

import static TFG.JaimeOlga.utils.Cons.Images.MAP_BASE;

public class BaseScreen implements Screen {

    private Main game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Player player;
    private ItemController itemController;
    private MapController mapController;
    private InputController inputController;

    // Resolución del juego (acordar con Olga)
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;

    public BaseScreen(Main game, Player player) {
        this.game = game;

        this.player = player;
        inputController = new InputController(player, game);
        mapController = new MapController(game);

        itemController = mapController.getItemController();

        // Cargar el mapa de Tiled
        mapController.loadMap(MAP_BASE);

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

        game.batch.end();
    }

    public void update(float delta) {

        // Actualizar items (detectar colisiones y recoger)
        itemController.update(player);

        // Actualizar jugador
        player.update(delta, mapController.getCollisionManager());

        camera.position.x = player.getxPosition();
        camera.position.y = player.getyPosition();
        camera.update();
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