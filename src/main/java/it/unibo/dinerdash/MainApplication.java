package it.unibo.dinerdash;

import it.unibo.dinerdash.controller.impl.GameController;
import it.unibo.dinerdash.view.impl.GameView;

public class MainApplication {

    public static void main(String[] args) {
        GameController controller = new GameController();
        GameView view = new GameView();

        view.setController(controller);
        controller.setView(view);

        controller.start();
    }
    
}