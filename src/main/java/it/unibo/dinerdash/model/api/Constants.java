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

    /**
     * Logical restaurant height.
     */
    public static final int RESTAURANT_HEIGHT = 720;

    /**
     * Logical restaurant width.
     */
    public static final int RESTAURANT_WIDTH = 1280;

    /**
     * Fist position in line's coordinate x.
     */
    public static final int CUSTOMER_FIRST_LINE_REL_X = (int) (0.04 * RESTAURANT_WIDTH);

    /**
     * Fist position in line's coordinate y.
     */
    public static final int CUSTOMER_FIRST_LINE_REL_Y = (int) (0.67 * RESTAURANT_HEIGHT);

    private Constants() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }
}
