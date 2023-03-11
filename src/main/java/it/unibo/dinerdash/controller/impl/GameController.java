package it.unibo.dinerdash.controller.impl;

import it.unibo.dinerdash.model.impl.GameModel;
import it.unibo.dinerdash.view.impl.GameView;

public class GameController {

    private GameModel model;
    private GameView view;
    
    public GameController() {
        this.model = new GameModel();
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void start() {
        // TO-DO
    }
}
