package it.unibo.dinerdash.model.api;

import java.util.List;
import java.util.Optional;

import it.unibo.dinerdash.model.api.States.GameState;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.GameEntities.Waitress;
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

    /**
     * used to create a new Customer
     */
    void addCustomer();

    /**
     * reduce a counter of Customers who left,cause
     * they have finished to eat ,or they are too angry
     */
    void customerLeft();

    Countertop getCounterTop();

    void update(long elapsedUpdateTime);

    GameState getGameState();

    void setGameState(GameState gameState);

    void decrementRemainingTime();

    int getCoins();

    int getRemainingTime();

    void setWaitressTableDestination(Pair<Integer, Integer> destination);

    /**
     * @param client client in line looking for a free table
     * @return true if there is a free table to sit,and
     * client is the fist in line waiting
     */
    boolean checkFreeTables(Customer client);

    /**
     * @param client
     */
    void linePositionAssignament(Customer client);

    void tableAssignament(Customer client);

    void removeAngryCustomers();

    boolean thereAreAvaibleTables();

    Table getTablefromPositon(Pair<Integer, Integer> pos);

    void setTableState(TableState state, int numberTable);

    Waitress getWaitress();

    void setWaiterssInfo(int indexL, String s, Pair<Integer, Integer> pos);

    void setCoins(int val);

    boolean thereAreDishesToPrepare();

    Optional<Dish> getDishToPrepare();

    void completeDishPreparation(Dish dish);

    void earnMoneyFromTable();

    void setNumberOfClientsAtTable(int numberOfClienty, int numberOfTable);

}