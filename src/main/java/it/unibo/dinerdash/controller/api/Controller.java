package it.unibo.dinerdash.controller.api;

import java.util.HashMap;
import java.util.Optional;

import it.unibo.dinerdash.view.api.GameEntityViewable;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.TableViewable;

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

    HashMap<TableViewable, Optional<GameEntityViewable>> getTables();

    int getRestaurantWidth();

    int getRestaurantHeight();
        
}
