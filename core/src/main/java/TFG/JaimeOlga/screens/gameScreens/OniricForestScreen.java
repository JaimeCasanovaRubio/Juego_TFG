package TFG.JaimeOlga.screens.gameScreens;

import TFG.JaimeOlga.GameController;
import TFG.JaimeOlga.entities.Player;

import static TFG.JaimeOlga.utils.Cons.Images.*;

/**
 * Pantalla del Bosque Onírico (Zona 1).
 * Hereda toda la lógica del juego de GameScreenModel y solo define su mapa
 * específico.
 */
public class OniricForestScreen extends GameScreenModel {

    public OniricForestScreen(GameController game, Player player) {
        super(game, player);
    }

    @Override
    protected String getMapPath() {
        return MAP_ZONE1;
    }
}