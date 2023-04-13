package it.unibo.dinerdash.controller.api;

import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.GameView;

public interface Controller {

    void setView(View view);

    void start(GameView gameView);

    void restart();

    void pause();

    void resume();

    void quit();

    void syncChanges();

    int getCoins();

    int getRemainingTime();

    boolean gameOver();

    void addCustomer();

    void resizeEntities();

    int getRestaurantWidth();

    int getRestaurantHeight();
        
}
