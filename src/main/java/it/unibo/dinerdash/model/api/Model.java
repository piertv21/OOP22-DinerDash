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

    void clear();

    void start();

    void pause();

    void stop();

    void sendOrder(int tableNumber);

    boolean gameOver();

    void restart();

    int getCustomersWhoLeft();

    int getCustomerWhoCanLeft();

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
     * Check if client is the first in line and there is a free table.
     * 
     * @param client client in line looking for a free table
     * @return true if there is a free table to sit,and
     *         client is the fist in line waiting
     */
    boolean checkFreeTables(Customer client);

    /**
     * Assign the first free spot in line queue.
     * 
     * @param client to whom i will assign a place in line           
     */
    void linePositionAssignament(Customer client);

    /**
     * Assign a free table to a client.
     * 
     * @param client to whom i will assign a free table
     */
    void tableAssignament(Customer client);

    /**
     * Remove a client whom waited in line
     * for too much.
     */
    void removeAngryCustomers();

    /**
     * Check if there are free tables to sit down.
     * 
     * @return true if there is a free table
     *         false if not.
     */
    boolean thereAreAvaibleTables();

    Table getTablefromPositon(Pair<Integer, Integer> pos);

    void setTableState(TableState state, int numberTable);

    void setWaiterssInfo(int indexL, String s, Pair<Integer, Integer> pos);

    void setCoins(int val);

    boolean thereAreDishesToPrepare();

    Optional<Dish> getDishToPrepare();

    void completeDishPreparation(Dish dish);

    void earnMoneyFromTable();

    /**
     *  Set the number of clients sitted at the table number
     *  "numberOfTable".
     * 
     * @param numberOfClienty number of clients 
     * @param numberOfTable   number of the table
     */
    void setNumberOfClientsAtTable(int numberOfClienty, int numberOfTable);

    void addDishToView(Dish dish);

    void removeDishInView(int dishIndex);

    void reduceDishPreparationTime();

    void increaseWaitressSpeed();

    void increaseMaxCustomerThatCanLeave();

    void increaseGainMultiplier();

    int[] getPowerUpsPrices();

    /**
     * Set  max Customer who can leaver before loose a game.
     * 
     * @param number Cax Customer' Number
     */
    void addMaxCustomerThatCanLeave(int number);

    void increaseCoinsMultiplier();

    void updateDishInView(int index, Dish dish);

    Optional<Dish> takeDishFromPosition(Pair<Integer, Integer> pos);

    boolean canActivatePowerUp(int price);

}