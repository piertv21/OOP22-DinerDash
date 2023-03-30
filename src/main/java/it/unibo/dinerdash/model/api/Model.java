package it.unibo.dinerdash.model.api;

public interface Model {

    void start();

    void serveDish();

    void addOrder(int tableNumber);

    boolean gameOver();

    void restart();

    void addCustomer(int num);

}
