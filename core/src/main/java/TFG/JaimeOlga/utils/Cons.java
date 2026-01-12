package TFG.JaimeOlga.utils;

public class Cons {
    public static final float SCALE = 2.0f; // Adjust this value as needed for your game's scale

    // TILED
    public static final int TILE_DEFAULT_SIZE = 32;
    public static final int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public static final int SMALL_WINDOW_WIDTH = TILE_SIZE * 20;
    public static final int SMALL_WINDOW_HEIGHT = TILE_SIZE * 11;

    // PLAYER
    public static final int PLAYER_SPEED = 5;
    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 32;

    // MONSTERS

    public static final int MONSTER_SPEED = 2;

    // IMAGES
    public static class Images {

        // BACKGROUND
        public static final String BACKGROUND = "tileSet/2 Background/Background.png";

        // PLAYER
        public static final String PLAYER_JUMP = "player/jump/Jump (32x32).png";
        public static final String PLAYER_IDLE = "player/idle/Idle (32x32).png";
        public static final String PLAYER_RUN = "player/movement/Run (32x32).png";
        public static final String PLAYER_HIT = "player/hit/Hit (32x32).png";
        public static final String PLAYER_WALL_JUMP = "player/jump/Wall Jump (32x32).png";
        public static final String PLAYER_DOUBLE_JUMP = "player/jump/Double Jump (32x32).png";
        public static final String PLAYER_FALL = "player/jump/Fall (32x32).png";

        public static final int IDLE = 0;
        public static final int JUMP = 1;
        public static final int RUN = 2;
        public static final int HIT = 3;
        public static final int WALL_JUMP = 4;
        public static final int DOUBLE_JUMP = 5;
        public static final int FALL = 6;

        // OBJECTS
        public static final String FLORERO = "tileSet/3 Objects/Boxes/florero.png";
        public static final String STONE = "tileSet/3 Objects/Stones/3.png";
        public static final String SWORD = "tileSet/3 Objects/Boxes/5.png";

        // MAPS
        public static final String MAP_BASE = "maps/base.tmx";
        public static final String MAP_ZONE1 = "maps/zone1.tmx";

        public static final int GAME_WIDTH = 1280;
        public static final int GAME_HEIGHT = 720;

        public static int getSpriteCount(int state) {
            switch (state) {
                case JUMP:
                case FALL:
                    return 1;
                case IDLE:
                    return 11;
                case RUN:
                    return 12;
                case HIT:
                    return 7;
                case WALL_JUMP:
                    return 5;
                case DOUBLE_JUMP:
                    return 6;
                default:
                    return 0;
            }
        }
    }
}
