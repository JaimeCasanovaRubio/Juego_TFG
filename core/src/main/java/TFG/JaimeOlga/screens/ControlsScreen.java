package TFG.JaimeOlga.screens;

import static TFG.JaimeOlga.utils.Cons.Images.GAME_HEIGHT;
import static TFG.JaimeOlga.utils.Cons.Images.GAME_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import TFG.JaimeOlga.GameController;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ControlsScreen implements Screen {

    private GameController game;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Scene2D
    private Stage stage;
    private Skin skin;

    public ControlsScreen(GameController game) {
        this.game = game;

        // Configurar cámara y viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);

        // Crear Stage y cargar Skin
        stage = new Stage(viewport, game.batch);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        createUI();
    }

    private void createUI() {
        // Crear tabla principal para organizar elementos
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        // Título
        Label titleLabel = new Label("Controles", skin);
        titleLabel.setFontScale(2f);
        mainTable.add(titleLabel).padBottom(50).row();

        // Controles
        Label rightMov = new Label("Movimiento Derecha: ", skin);
        rightMov.setFontScale(1.5f);
        mainTable.add(rightMov).padBottom(20).row();

        Label leftMov = new Label("Movimiento Izquierda: ", skin);
        leftMov.setFontScale(1.5f);
        mainTable.add(leftMov).padBottom(20).row();

        Label upMov = new Label("Movimiento Arriba: ", skin);
        upMov.setFontScale(1.5f);
        mainTable.add(upMov).padBottom(20).row();

        Label downMov = new Label("Movimiento Abajo: ", skin);
        downMov.setFontScale(1.5f);
        mainTable.add(downMov).padBottom(20).row();

        Label attackMov = new Label("Ataque: ", skin);
        attackMov.setFontScale(1.5f);
        mainTable.add(attackMov).padBottom(20).row();

        // --- Botón: Volver (a la pantalla anterior) ---
        TextButton btnBack = new TextButton("Volver", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.lastScreen);
            }
        });
        mainTable.add(btnBack).width(200).height(50).padTop(30).row();

        // Añadir tabla al stage
        stage.addActor(mainTable);
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
    }

    @Override
    public void dispose() {
    }
}
