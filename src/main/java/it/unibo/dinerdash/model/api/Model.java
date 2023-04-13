package it.unibo.dinerdash.model.api;

public interface Model {

    int getWidth();

    int getHeight();

    void start();

    void pause();

    void stop();

    void sendOrder(int tableNumber);

    boolean gameOver();

    void restart();

    void quit();

    void addCustomer();

    void customerLeft();

}
