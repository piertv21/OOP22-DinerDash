package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.controller.api.Controller;

public interface View {

    void showMainMenu();

    void startGame();

    void quit();

    Controller getController();

    FramePanel getGamePanel();
    
}
