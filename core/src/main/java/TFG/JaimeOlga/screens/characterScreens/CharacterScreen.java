package TFG.JaimeOlga.screens.characterScreens;

import static TFG.JaimeOlga.utils.Cons.Images.GAME_HEIGHT;
import static TFG.JaimeOlga.utils.Cons.Images.GAME_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import TFG.JaimeOlga.GameController;

import static TFG.JaimeOlga.utils.Cons.Images.*;

public class CharacterScreen implements Screen {

    private GameController game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;

    private Texture hurtadillaTexture;

    public CharacterScreen(GameController game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);

        // Crear Stage y Skin
        stage = new Stage(viewport, game.batch);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        createUI();

    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label titleLabel = new Label("Personajes", skin);
        titleLabel.setFontScale(2f);
        table.add(titleLabel).colspan(2).padBottom(50).row();

        // Cargar imagen del personaje (idle)
        hurtadillaTexture = new Texture(Gdx.files.internal(PLAYER_IDLE));
        // Asumiendo que es un spritesheet horizontal y cada frame es 32x32. Usamos el
        // primer frame.
        TextureRegion playerRegion = new TextureRegion(hurtadillaTexture, 0, 0, 32, 32);

        TextureRegionDrawable drawable = new TextureRegionDrawable(playerRegion);
        drawable.setMinSize(64, 64); // Hacemos que el botón sea más grande (2x) visualmente

        ImageButton hurtadillaButton = new ImageButton(drawable);

        hurtadillaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.hurtadillaScreen);
            }
        });
        table.add(hurtadillaButton).colspan(2).padBottom(20).row();

        TextButton backButton = new TextButton("Volver", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.menuScreen);
            }
        });
        table.add(backButton).colspan(2).padBottom(20).row();

        stage.addActor(table);

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
