package it.unibo.dinerdash.controller.impl;

import it.unibo.dinerdash.model.impl.Model;
import it.unibo.dinerdash.view.impl.View;

public class Controller {

    private Model model;
    private View view;
    
    public Controller() {
        this.model = new Model();
    }

    public void setView(View view) {
        this.view = view;
    }

    public void start() {
        //TO-DO
    }

    private void init() {
        //TO-DO
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
        return this.model.getMaxPlaytimeInSeconds();
    }
}
