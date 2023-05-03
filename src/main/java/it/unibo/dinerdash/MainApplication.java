package it.unibo.dinerdash;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.controller.impl.ControllerImpl;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.ViewImpl;

/**
 * Main instance of the game.
 */
public final class MainApplication {

    /**
     * Private constructor, so as not to make
     * the class instantiatable.
     */
    private MainApplication() { }

    public static void main(final String[] args) {
        final Controller controller = new ControllerImpl();
        final View view = new ViewImpl(controller);        
        controller.setView(view);
    }
    
}
