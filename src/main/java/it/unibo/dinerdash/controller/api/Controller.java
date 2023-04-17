package it.unibo.dinerdash.controller.api;

import java.util.LinkedList;

import it.unibo.dinerdash.model.impl.Customer;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.View;

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

    void addCustomer(int customNumber,Pair<Integer,Integer> size);

    void removeCustomer(int indexValue);

    void resizeEntities();

    int getRestaurantWidth();

    int getRestaurantHeight();

    void timeIsChanged();

    void setWaitressDestination(Pair<Integer,Integer> destination);

    void callWaitress(int indexOf, String s, Pair<Integer, Integer> position);
}
