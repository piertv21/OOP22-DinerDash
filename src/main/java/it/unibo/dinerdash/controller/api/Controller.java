package it.unibo.dinerdash.controller.api;

import it.unibo.dinerdash.view.impl.View;

public interface Controller {

    void setView(View view);

    void start();

    void init();

    void quit();

    void restart();
    
}
