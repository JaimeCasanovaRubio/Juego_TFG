package TFG.JaimeOlga.screens.menuScreens;

import static TFG.JaimeOlga.utils.Cons.Images.GAME_HEIGHT;
import static TFG.JaimeOlga.utils.Cons.Images.GAME_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import static TFG.JaimeOlga.utils.Cons.Images.PLAYER_IDLE;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.entities.characters.Hurtadilla;

public class CharacterSelectionScreen implements Screen {

    private GameController game;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;
    private Texture playerTexture;

    public CharacterSelectionScreen(GameController game) {
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
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label titleLabel = new Label("Selecciona tu personaje", skin);
        titleLabel.setFontScale(2f);
        table.add(titleLabel).colspan(2).padBottom(50).row();

        // Cargar imagen del personaje (idle)
        playerTexture = new Texture(Gdx.files.internal(PLAYER_IDLE));
        // Asumiendo que es un spritesheet horizontal y cada frame es 32x32. Usamos el
        // primer frame.
        TextureRegion playerRegion = new TextureRegion(playerTexture, 0, 0, 32, 32);

        TextureRegionDrawable drawable = new TextureRegionDrawable(playerRegion);
        drawable.setMinSize(64, 64); // Hacemos que el botón sea más grande (2x) visualmente

        ImageButton hurtadillaButton = new ImageButton(drawable);

        hurtadillaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Iniciar el juego
                Hurtadilla hurtadilla = new Hurtadilla();

                game.setPlayer(hurtadilla);
                game.setGameScreens();

                game.changeScreen(game.baseScreen);
            }
        });

        table.add(hurtadillaButton).size(128, 128).padBottom(20).row();

        // --- Botón: Volver (a la pantalla anterior) ---
        TextButton btnBack = new TextButton("Volver", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.lastScreen == game.controlsScreen) {
                    if (game.lastPlayScreen == null) {
                        game.changeScreen(game.menuScreen);
                    } else {
                        game.changeScreen(game.lastPlayScreen);
                    }
                } else {
                    if (game.lastPlayScreen == null) {
                        game.changeScreen(game.menuScreen);
                    } else {
                        game.changeScreen(game.lastPlayScreen);
                    }
                }
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
        if (playerTexture != null) {
            playerTexture.dispose();
        }

    }
}
