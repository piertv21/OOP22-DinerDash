package it.unibo.dinerdash.model.api;

import java.awt.Dimension;

public interface Model {

    void setRestaurantSize(Dimension dimension);

    void start();

    void serveDish();

    void addOrder(int tableNumber);

    boolean gameOver();

    void restart();

    void quit();

    void addCustomer(int num);

}
