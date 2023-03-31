package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.view.impl.GameView;

public interface View {

    void showMainMenu();

    void startGame();

    void quit();

    Controller getController();

    GameView getGamePanel();
    
}
