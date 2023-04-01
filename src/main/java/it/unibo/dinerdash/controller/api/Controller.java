package it.unibo.dinerdash.controller.api;

import java.awt.Dimension;
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

    void setFrameSize(Dimension dimension);

    void resizeEntities();

    HashMap<TableViewable, Optional<GameEntityViewable>> getTables();
        
}
