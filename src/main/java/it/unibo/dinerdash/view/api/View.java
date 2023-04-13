package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.controller.api.Controller;

public interface View {

    void showMainMenu();

    void startGame();

    void playAgain();

    void quit();

    Controller getController();

    double getWidthRatio();

    double getHeightRatio();

    int getWidth();

    int getHeight();
    
}
