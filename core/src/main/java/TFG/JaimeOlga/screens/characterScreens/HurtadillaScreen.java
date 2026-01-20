package TFG.JaimeOlga.screens.characterScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static TFG.JaimeOlga.utils.Cons.Images.*;

import TFG.JaimeOlga.GameController;

public class HurtadillaScreen implements Screen {

    private GameController game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture hurtadillaTexture;

    // Scene2D
    private Stage stage;
    private Skin skin;

    public HurtadillaScreen(GameController game) {
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
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label hurtadillaLebel = new Label("Hurtadilla", skin);
        hurtadillaLebel.setFontScale(2f);
        table.add(hurtadillaLebel).padBottom(20).row();

        // Cargar imagen del personaje (idle)
        hurtadillaTexture = new Texture(Gdx.files.internal(PLAYER_IDLE));
        // Asumiendo que es un spritesheet horizontal y cada frame es 32x32. Usamos el
        // primer frame.
        TextureRegion playerRegion = new TextureRegion(hurtadillaTexture, 0, 0, 32, 32);

        TextureRegionDrawable drawable = new TextureRegionDrawable(playerRegion);
        drawable.setMinSize(64, 64); // Hacemos que el botón sea más grande (2x) visualmente

        Image hurtadillaImage = new Image(drawable);
        table.add(hurtadillaImage).colspan(2).padBottom(20).row();

        TextButton btnBack = new TextButton("Volver", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.characterScreen);
            }
        });
        table.add(btnBack).width(200).height(50).padTop(30).row();

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
