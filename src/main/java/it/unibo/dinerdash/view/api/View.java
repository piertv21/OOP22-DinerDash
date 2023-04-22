package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.utility.impl.ImageReaderWithCache;

public interface View {

    void showStartView();

    void showGameView();

    void showGameOverView();

    void playAgain();

    void quit();

    Controller getController();

    /**
     * getter for WidthRatio.
     * @return ratio between model's Widtht
     * and main window's Widtht
     */
    double getWidthRatio();

    /**
     * getter for HeightRatio.
     * @return ratio between model's height
     * and main window's height
     */
    double getHeightRatio();

    /**
     * getter for main window's Width.
     * @return Width of the main window
     */
    int getWidth();

    /**
     * getter for main window's Height.
     * @return height of the main window
     */
    int getHeight();

    ImageReaderWithCache getImageCacher();
    
}
