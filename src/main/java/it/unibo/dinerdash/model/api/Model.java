package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.model.api.States.GameState;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.model.api.GameEntities.Table;
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

    void update();

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
     * @param client to whom i will assign a place in line
     * assign the first free spot in line queue
     */
    void linePositionAssignament(Customer client);

    /**
     * @param client to whom i will assign a free table
     * assign the first free table to a customer
     */
    void tableAssignament(Customer client);

    /**
     * remove a client whom waited in line
     * for too much 
     */
    void removeAngryCustomers();

    /**
     * @return true if there is a free table
     * check if there is a free table to sit
     */
    boolean thereAreAvaibleTables();

    Table getTablefromPositon(Pair<Integer, Integer> pos);

    void setTableState(TableState state, int numberTable);

    void setWaiterssInfo(int indexL, String s, Pair<Integer, Integer> pos);

    void setCoins(int val);

    boolean thereAreDishesToPrepare();

    Optional<Dish> getDishToPrepare();

    void completeDishPreparation(Dish dish);

    //TODO Aggiungi funzioni dish (pronto preso)

    void earnMoneyFromTable();

    /**
     * @param numberOfClienty number of clients whom are sitted
     * at the table
     * @param numberOfTable number of the table
     * set the number of clients sitted at the table number "numberOfTable"
     */
    void setNumberOfClientsAtTable(int numberOfClienty, int numberOfTable);

}