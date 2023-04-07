package it.unibo.dinerdash;

import it.unibo.dinerdash.controller.impl.ControllerImpl;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.ViewImpl;

public class MainApplication {

    private MainApplication() {}
    
    public static void main(String[] args) {
        ControllerImpl controller = new ControllerImpl();
        View view = new ViewImpl(controller);
        
        controller.setView(view);
    }
    
}