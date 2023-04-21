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

    void addCustomer();

    void customerLeft();

    Countertop getCounterTop();

    void update(long elapsedUpdateTime);

    GameState getGameState();

    void setGameState(GameState gameState);

    void decrementRemainingTime();

    int getCoins();

    int getRemainingTime();

    void setWaitressTableDestination(Pair<Integer, Integer> destination);

    boolean checkFreeTables(Customer cus);

    void linePositionAssignament(Customer cus);

    void tableAssignament(Customer cus);

    void removeAngryCustomers();

    boolean thereAreAvaibleTables();

    Table getTablefromPositon(Pair<Integer, Integer> pos);

    void setTableState(TableState state, int numberTable);

    List<Table> getTable();

    Waitress getWaitress();

    void setWaiterssInfo(int indexL, String s, Pair<Integer, Integer> pos);

    void setCoins(int val);

    boolean thereAreDishesToPrepare();

    Optional<Dish> getDishToPrepare();

    void completeDishPreparation(Dish dish);

    void earnMoneyFromTable();

    void setNumberOfClientsAtTable(int numberOfClienty, int numberOfTable);
    
}
