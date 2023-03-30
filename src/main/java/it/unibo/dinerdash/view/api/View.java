package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.controller.api.Controller;

public interface View {

    void showMainMenu();

    void startGame();

    void closeWindow();

    Controller getController();

    FramePanel getGamePanel();
    
}
