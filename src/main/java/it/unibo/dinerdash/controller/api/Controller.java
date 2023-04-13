package it.unibo.dinerdash.controller.api;

import it.unibo.dinerdash.utility.impl.Pair;
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

    String convertToMinutesAndSeconds(int seconds);

    boolean gameOver();

    void addCustomer();

    void resizeEntities();

    int getRestaurantWidth();

    int getRestaurantHeight();

    void timeIsChanged();

    void setWaitresDestination(Pair<Integer,Integer> destination);
        
}
