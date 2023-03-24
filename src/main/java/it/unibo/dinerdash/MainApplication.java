package it.unibo.dinerdash;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.controller.impl.ControllerImpl;
import it.unibo.dinerdash.view.impl.View;

public class MainApplication {

    public static void main(String[] args) {
        Controller controller = new ControllerImpl();
        View view = new View(controller);
        
        controller.setView(view);
    }
    
}