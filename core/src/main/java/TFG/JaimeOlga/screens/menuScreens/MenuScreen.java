package TFG.JaimeOlga.screens.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import TFG.JaimeOlga.GameController;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static TFG.JaimeOlga.utils.Cons.Images.*;

public class MenuScreen implements Screen {

    private GameController game;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;

    public MenuScreen(GameController game) {
        this.game = game;
        this.font = new BitmapFont();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);

        // Crear Stage y Skin
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
        Label titleLabel = new Label("Oniric Forest", skin);
        titleLabel.setFontScale(2f);
        mainTable.add(titleLabel).padBottom(50).row();

        // Botón Jugar
        TextButton playButton = new TextButton("Jugar", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.characterSelectionScreen);
            }
        });
        mainTable.add(playButton).padBottom(20).row();

        // Botón Personajes
        TextButton characterButton = new TextButton("Personajes", skin);
        characterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.characterScreen);
            }
        });
        mainTable.add(characterButton).padBottom(20).row();

        // Botón Ajustes
        TextButton settingsButton = new TextButton("Ajustes", skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.settingsMenu);
            }
        });
        mainTable.add(settingsButton).padBottom(20).row();

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
        font.dispose();
    }
}
