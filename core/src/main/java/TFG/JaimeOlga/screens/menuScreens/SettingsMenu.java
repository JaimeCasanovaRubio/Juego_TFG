package TFG.JaimeOlga.screens.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import TFG.JaimeOlga.GameController;

public class SettingsMenu extends MenuScreenModel {

    // Volumen actual (0 a 1)
    private float volumeLevel = 0.5f;

    public SettingsMenu(GameController game) {
        super(game);
        createUI();
    }

    @Override
    protected void createUI() {
        // Crear tabla principal para organizar elementos
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        // Título
        Label titleLabel = new Label("AJUSTES", skin);
        titleLabel.setFontScale(2f);
        mainTable.add(titleLabel).padBottom(50).row();

        // --- Control de Volumen ---
        Label volumeLabel = new Label("Volumen:", skin);
        mainTable.add(volumeLabel).padBottom(5).row();

        // Crear slider horizontal (min 0, max 1, step 0.1, horizontal)
        final Slider volumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSlider.setValue(volumeLevel);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                volumeLevel = volumeSlider.getValue();
                // Aquí puedes aplicar el volumen a tu sistema de audio
                // Por ejemplo: MusicManager.setVolume(volumeLevel);
                Gdx.app.log("Settings", "Volumen: " + (int) (volumeLevel * 100) + "%");
            }
        });
        mainTable.add(volumeSlider).width(200).height(30).padBottom(20).row();

        // Label que muestra el porcentaje de volumen
        final Label volumeValueLabel = new Label((int) (volumeLevel * 100) + "%", skin);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                volumeValueLabel.setText((int) (volumeSlider.getValue() * 100) + "%");
            }
        });
        mainTable.add(volumeValueLabel).padBottom(20).row();

        // --- Botón: Configurar Controles ---
        TextButton btnControls = new TextButton("Configurar Controles", skin);
        btnControls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.controlsScreen);
            }
        });
        mainTable.add(btnControls).width(200).height(50).padBottom(20).row();

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
        mainTable.add(btnBack).width(200).height(50).padTop(30).row();

        // Añadir tabla al stage
        stage.addActor(mainTable);
    }
}
