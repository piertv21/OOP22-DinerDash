package it.unibo.dinerdash.controller.api;

import it.unibo.dinerdash.view.api.View;

public interface Controller {

    void setView(View view);

    void start();

    void restart();

    void quit();

    int getCoins();

    int getRemainingTime();

    boolean gameOver();

    void addCustomer();

    void resizeEntities();

    int getRestaurantWidth();

    int getRestaurantHeight();
        
}
