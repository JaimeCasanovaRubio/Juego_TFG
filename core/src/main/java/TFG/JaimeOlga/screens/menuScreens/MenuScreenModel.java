package TFG.JaimeOlga.screens.menuScreens;

import static TFG.JaimeOlga.utils.Cons.Images.GAME_HEIGHT;
import static TFG.JaimeOlga.utils.Cons.Images.GAME_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import TFG.JaimeOlga.GameController;

/**
 * Clase abstracta base para todas las pantallas de menú.
 * Centraliza la lógica común como configuración de cámara, viewport, stage y
 * skin.
 * Las subclases solo necesitan implementar el método createUI() para definir su
 * interfaz.
 */
public abstract class MenuScreenModel implements Screen {

    protected GameController game;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected Stage stage;
    protected Skin skin;

    /**
     * Método abstracto que cada pantalla debe implementar para crear su UI
     * específica.
     * Se llama automáticamente en el constructor después de inicializar stage y
     * skin.
     */
    protected abstract void createUI();

    public MenuScreenModel(GameController game) {
        this.game = game;

        // Configurar cámara y viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);

        // Crear Stage y cargar Skin
        stage = new Stage(viewport, game.batch);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // NOTA: No se llama a createUI() aquí para evitar problemas de orden de
        // inicialización.
        // Las clases hijas deben llamar a createUI() en su constructor después de
        // inicializar
        // sus propias dependencias.
    }

    @Override
    public void show() {
        // Registrar el Stage como InputProcessor para que reciba eventos de
        // mouse/teclado
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Limpiar pantalla con color oscuro
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        // Actualizar y dibujar el Stage (todos los widgets)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // Limpiar el InputProcessor cuando salimos de esta pantalla
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
    }
}
