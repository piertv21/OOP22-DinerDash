package it.unibo.dinerdash.model.api;

/**
 * Class with shared Contants.
 */
public final class Constants {

    public static final String GAME_NAME = "Diner Dash";
    public static final String SEP = System.getProperty("file.separator");
    public static final int RESTAURANT_WIDTH = 1280;
    public static final int RESTAURANT_HEIGHT = 720;

    private Constants() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }
    
}
