package it.unibo.dinerdash.model.api;

/**
 * Class with shared Contants.
 */
public final class Constants {

    /**
     * Game Name.
     */
    public static final String GAME_NAME = "Diner Dash";

    /**
     * Game Root path.
     */
    public static final String ROOT = "it/unibo/dinerdash/";

    /**
     * Game Assets path.
     */
    public static final String RESOURCES_PATH = "src/main/resources/";

    /**
     * Game Font.
     */
    public static final String GAME_FONT = "Arial";

    /**
     * Logical restaurant width.
     */
    public static final int RESTAURANT_WIDTH = 1280;

    /**
     * Logical restaurant height.
     */
    public static final int RESTAURANT_HEIGHT = 720;

    private Constants() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

}
