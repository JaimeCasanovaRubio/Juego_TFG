package TFG.JaimeOlga.screens.menuScreens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import TFG.JaimeOlga.GameController;

public class MenuScreen extends MenuScreenModel {

    public MenuScreen(GameController game) {
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
}
