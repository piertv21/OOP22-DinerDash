package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;

/**
 * This interface defines a view
 * that can contain game panels.
 */
public interface View {

    /**
     * Shows the main game panel.
     */
    void showGameView();

    /**
     * Shows Game Over panel.
     */
    void showGameOverView();

    /**
     * Shows the exit dialog before exiting the program.
     */
    void showExitDialog();

    /**
     * Start the game over.
     */
    void playAgain();

    /**
     * Handle view closing.
     */
    void quit();

    /**
     * Give a reference to controller.
     * 
     * @return the controller
     */
    Controller getController();

    /**
     * Getter for WidthRatio.
     * 
     * @return ratio between model's Widtht
     * and main window's Widtht
     */
    double getWidthRatio();

    /**
     * Getter for HeightRatio.
     * 
     * @return ratio between model's height
     * and main window's height
     */
    double getHeightRatio();

    /**
     * Getter for Game's Image Storage.
     * 
     * @return ImageCacher with all Images saved
     */
    ImageReaderWithCache getImageCacher();

    /**
     * Update the view with a new panel and draw it.
     */
    void refreshView();

}
