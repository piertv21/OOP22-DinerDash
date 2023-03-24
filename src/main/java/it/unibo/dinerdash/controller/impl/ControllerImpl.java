package it.unibo.dinerdash.controller.impl;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.impl.Model;
import it.unibo.dinerdash.view.impl.View;

public class ControllerImpl implements Controller {

    private Model model;
    private View view;
    
    public ControllerImpl() {
        this.model = new Model();
    }

    public void start() {
        //TO-DO
    }
    
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    public void restart() {
        //TO-DO
        // clear delle strutture
        
        this.init();
    }

    public void quit() {
        // Close window
        this.view.closeWindow();
     
        // Remove entity images
        // image1.flush();
     
        // Exit the app
        System.exit(0);
    }

    public int getMaxPlayingTime() {
        return this.model.getMaxPlaytime();
    }

}
