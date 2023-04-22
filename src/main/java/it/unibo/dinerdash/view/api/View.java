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
     * @return ratio between model's Widtht
     * and main window's Widtht
     * getter for WidthRatio
     */
    double getWidthRatio();

    /**
     * @return ratio between model's height
     * and main window's height
     * getter for HeightRatio
     */
    double getHeightRatio();

    /**
     * @return Width of the main window
     * getter for main window's Width
     */
    int getWidth();

    /**
     * @return height of the main window
     * getter for main window's Height
     */
    int getHeight();

    ImageReaderWithCache getImageCacher();
    
}
