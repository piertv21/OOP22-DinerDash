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
     * Game Font.
     */
    public static final String GAME_FONT = "Arial";

    private Constants() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    /**
     * Logical restaurant height.
     */
    public static final int RESTAURANT_HEIGHT = 720;

    /**
     * Logical restaurant width.
     */
    public static final int RESTAURANT_WIDTH = 1280;

}
