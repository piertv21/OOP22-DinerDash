package it.unibo.dinerdash.model.api;

import java.util.LinkedList;

import it.unibo.dinerdash.model.impl.Countertop;
import it.unibo.dinerdash.model.impl.Customer;
import it.unibo.dinerdash.model.impl.Table;
import it.unibo.dinerdash.utility.impl.Pair;

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

    Countertop getCounterTop();

    void update(long elapsedUpdateTime);

    GameState getGameState();

    void setGameState(GameState gameState);

    void decrementRemainingTime();

    int getCoins();

    int getRemainingTime();

    LinkedList<Customer> getCustomers();

    void setWaitressTableDestination(Pair<Integer,Integer> destination);

    boolean checkFreeTables(Customer cus);

    void assegnoPostoFila(Customer cus);

    void AssegnoTavolo(Customer cus);

    void setTableCustomers(int customersMolteplicity,int numberTable);

    void checkAngryCustomers();

    boolean thereAreAvaibleTables();

    Table getTablefromPositon(Pair<Integer,Integer> pos);

    void setTableState(TableState state,int numberTable);

}
