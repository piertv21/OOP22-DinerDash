package it.unibo.dinerdash;
import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.controller.impl.ControllerImpl;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.ViewImpl;

public class MainApplication {

    public static void main(String[] args) {
        Controller controller = new ControllerImpl();
        View view = new ViewImpl(controller);
        
        controller.setView(view);
    }
    
}