package TFG.JaimeOlga.screens.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.utils.KeyBindings;

public class ControlsScreen extends MenuScreenModel {

    private KeyBindings keyBindings;

    // Para capturar la siguiente tecla
    private boolean waitingForKey = false;
    private String currentAction = null;
    private TextButton currentButton = null;

    // Botones de controles
    private TextButton btnMoveLeft;
    private TextButton btnMoveRight;
    private TextButton btnMoveUp;
    private TextButton btnMoveDown;
    private TextButton btnAttack;
    private TextButton btnAbility;

    public ControlsScreen(GameController game) {
        super(game);
        this.keyBindings = KeyBindings.getInstance();
        createUI();
    }

    @Override
    protected void createUI() {
        // Crear tabla principal para organizar elementos
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        // Título
        Label titleLabel = new Label("Controles", skin);
        titleLabel.setFontScale(2f);
        mainTable.add(titleLabel).colspan(2).padBottom(50).row();

        // Controles con botones para modificar
        mainTable.add(new Label("Movimiento Izquierda:", skin)).padRight(20).padBottom(15);
        btnMoveLeft = new TextButton(KeyBindings.getKeyName(keyBindings.getMoveLeft()), skin);
        btnMoveLeft.addListener(createKeyBindingListener(KeyBindings.KEY_MOVE_LEFT, btnMoveLeft));
        mainTable.add(btnMoveLeft).width(100).height(40).padBottom(15).row();

        mainTable.add(new Label("Movimiento Derecha:", skin)).padRight(20).padBottom(15);
        btnMoveRight = new TextButton(KeyBindings.getKeyName(keyBindings.getMoveRight()), skin);
        btnMoveRight.addListener(createKeyBindingListener(KeyBindings.KEY_MOVE_RIGHT, btnMoveRight));
        mainTable.add(btnMoveRight).width(100).height(40).padBottom(15).row();

        mainTable.add(new Label("Movimiento Arriba:", skin)).padRight(20).padBottom(15);
        btnMoveUp = new TextButton(KeyBindings.getKeyName(keyBindings.getMoveUp()), skin);
        btnMoveUp.addListener(createKeyBindingListener(KeyBindings.KEY_MOVE_UP, btnMoveUp));
        mainTable.add(btnMoveUp).width(100).height(40).padBottom(15).row();

        mainTable.add(new Label("Movimiento Abajo:", skin)).padRight(20).padBottom(15);
        btnMoveDown = new TextButton(KeyBindings.getKeyName(keyBindings.getMoveDown()), skin);
        btnMoveDown.addListener(createKeyBindingListener(KeyBindings.KEY_MOVE_DOWN, btnMoveDown));
        mainTable.add(btnMoveDown).width(100).height(40).padBottom(15).row();

        mainTable.add(new Label("Ataque:", skin)).padRight(20).padBottom(15);
        btnAttack = new TextButton(KeyBindings.getKeyName(keyBindings.getAttack()), skin);
        btnAttack.addListener(createKeyBindingListener(KeyBindings.KEY_ATTACK, btnAttack));
        mainTable.add(btnAttack).width(100).height(40).padBottom(15).row();

        mainTable.add(new Label("Habilidad:", skin)).padRight(20).padBottom(15);
        btnAbility = new TextButton(KeyBindings.getKeyName(keyBindings.getAbility()), skin);
        btnAbility.addListener(createKeyBindingListener(KeyBindings.KEY_ABILITY, btnAbility));
        mainTable.add(btnAbility).width(100).height(40).padBottom(15).row();

        // --- Botón: Restablecer por defecto ---
        TextButton btnReset = new TextButton("Restablecer", skin);
        btnReset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyBindings.resetToDefaults();
                updateButtonLabels();
            }
        });
        mainTable.add(btnReset).colspan(2).width(200).height(50).padTop(20).row();

        // --- Botón: Volver (a la pantalla anterior) ---
        TextButton btnBack = new TextButton("Volver", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyBindings.saveBindings();
                game.changeScreen(game.lastScreen);
            }
        });
        mainTable.add(btnBack).colspan(2).width(200).height(50).padTop(10).row();

        // Añadir tabla al stage
        stage.addActor(mainTable);
    }

    private ClickListener createKeyBindingListener(final String action, final TextButton button) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Entrar en modo de espera de tecla
                waitingForKey = true;
                currentAction = action;
                currentButton = button;
                button.setText("...");
            }
        };
    }

    private void updateButtonLabels() {
        btnMoveLeft.setText(KeyBindings.getKeyName(keyBindings.getMoveLeft()));
        btnMoveRight.setText(KeyBindings.getKeyName(keyBindings.getMoveRight()));
        btnMoveUp.setText(KeyBindings.getKeyName(keyBindings.getMoveUp()));
        btnMoveDown.setText(KeyBindings.getKeyName(keyBindings.getMoveDown()));
        btnAttack.setText(KeyBindings.getKeyName(keyBindings.getAttack()));
        btnAbility.setText(KeyBindings.getKeyName(keyBindings.getAbility()));
    }

    @Override
    public void show() {
        // Usar InputMultiplexer para manejar tanto Stage como captura de teclas
        InputMultiplexer multiplexer = new InputMultiplexer();

        // Primero añadir el capturador de teclas (tiene prioridad)
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (waitingForKey && currentAction != null && currentButton != null) {
                    // Asignar la nueva tecla
                    keyBindings.setBinding(currentAction, keycode);
                    currentButton.setText(KeyBindings.getKeyName(keycode));

                    // Salir del modo de espera
                    waitingForKey = false;
                    currentAction = null;
                    currentButton = null;
                    return true;
                }
                return false;
            }
        });

        // Después el Stage para los botones
        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);

        // Actualizar etiquetas por si los controles cambiaron
        updateButtonLabels();
    }
}
