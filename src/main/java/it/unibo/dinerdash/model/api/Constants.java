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
     * File separator.
     */
    public static final String SEP = System.getProperty("file.separator");

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
