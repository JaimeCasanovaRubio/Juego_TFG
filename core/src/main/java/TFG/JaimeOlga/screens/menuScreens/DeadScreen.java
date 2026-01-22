package TFG.JaimeOlga.screens.menuScreens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import TFG.JaimeOlga.GameController;

public class DeadScreen extends MenuScreenModel {

    public DeadScreen(GameController game) {
        super(game);
        createUI();
    }

    @Override
    protected void createUI() {
        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("Has muerto", skin);
        table.add(label).expandX().padBottom(10).row();

        TextButton button = new TextButton("Volver", skin);
        table.add(button).expandX().padTop(10);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.menuScreen);
            }
        });

        stage.addActor(table);
    }
}
