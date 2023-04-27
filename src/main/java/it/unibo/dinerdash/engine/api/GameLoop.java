package it.unibo.dinerdash.engine.api;

/**
 * This interface defines a GameLoop, for handling the game execution.
 */
public interface GameLoop {

    /**
     * Start the GameLoop thread.
     */
    void start();

    /**
     * Stop the GameLoop thread.
     */
    void stop();

}
