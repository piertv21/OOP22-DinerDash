package it.unibo.dinerdash.model.api;

import it.unibo.dinerdash.model.impl.Dish;

public interface Model {

    int getWidth();

    int getHeight();

    void start();

    void pause();

    void stop();

    void serveDish();

    void sendOrder(int tableNumber);

    boolean gameOver();

    void restart();

    void quit();

    void addCustomer();

}
