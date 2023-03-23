package it.unibo.dinerdash;

import it.unibo.dinerdash.controller.impl.Controller;
import it.unibo.dinerdash.view.impl.View;

public class MainApplication {

    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new View();

        view.setController(controller);
        controller.setView(view);

        controller.start();
    }
    
}