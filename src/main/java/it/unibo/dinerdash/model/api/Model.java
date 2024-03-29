package it.unibo.dinerdash.model.api;

import java.util.List;
import java.util.Optional;

import it.unibo.dinerdash.model.api.gameentities.Chef;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.GameState;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * This interface defines the game Model.
 */
public interface Model {

    /**
     * It set the controller.
     * 
     * @param controller is the controller to be set
     */
    void setController(Controller controller);

    /**
     * Getter for Logic-Window's Width.
     * 
     * @return the restaurant Width
     */
    int getWidth();

    /**
     * Getter for Logic-Window's Height.
     * 
     * @return the restaurant Height
     */
    int getHeight();

    /**
     * Clear model structures.
     */
    void clear();

    /**
     * Starts the game execution.
     */
    void start();

    /**
     * Pauses the game.
     */
    void pause();

    /**
     * Ends the game execution.
     */
    void stop();

    /**
     * Adds order to the Countertop.
     * 
     * @param tableNumber is the table number of the order
     */
    void sendOrder(int tableNumber);

    /**
     * Tells if the game is over.
     * 
     * @return true if game is over
     */
    boolean gameOver();

    /**
     * Restart the game execution.
     */
    void restart();

    /**
     * Getter for number of Angry customers who left.
     * 
     * @return number of customers who left
     */
    int getCustomersWhoLeft();

    /**
     * Returns number of customer who left the restaurant.
     * 
     * @return number of customer who left
     */
    int getCustomerWhoCanLeft();

    /**
     * Reduce a counter of Customers who left, cause
     * they have finished to eat ,or they are too angry.
     */
    void customerLeft();

    /**
     * Update the game state.
     * 
     * This function plays the following roles
     * during the course of the game:
     * 
     * - adds customers in line if possible
     * - update chef state
     * - update waitress state
     * - update customers state
     * - remove angry customers and update line customers position
     * - update tables state
     * - update powerups buttons state
     */
    void update();

    /**
     * Give the current game state.
     * 
     * @return the current game state
     */
    GameState getGameState();

    /**
     * Set a game state.
     * 
     * @param gameState is the game state to be set
     */
    void setGameState(GameState gameState);

    /**
     * Decrement by 1 unit the remaining time to play.
     */
    void decrementRemainingTime();

    /**
     * Gives the number of coins.
     * 
     * @return the number of coins
     */
    int getCoins();

    /**
     * Gives the remaining time in seconds.
     * 
     * @return the remaining time in seconds
     */
    int getRemainingTime();

    /**
     * Check if client is the first in line and there is a free table.
     * 
     * @param client client in line looking for a free table
     * @return true if there is a free table to sit,
     * and client is the fist in line waiting
     */
    boolean checkFreeTables(Customer client);

    /**
     * Assign a free table to a client.
     * 
     * @param client to whom i will assign a free table
     */
    void tableAssignament(Customer client);

    /**
     * Check if there are free tables to sit down.
     * 
     * @return true if there is a free table false if not.
     */
    boolean thereAreAvaibleTables();

    /**
     * Return a table from a given position.
     * 
     * @param pos is the position
     * @return is the table from the table list
     */
    Table getTablefromPosition(Pair<Integer, Integer> pos);

    /**
     * Set new state to a table.
     * 
     * @param state is the new state
     * @param numberTable is the table number
     */
    void setTableState(TableState state, int numberTable);

    /**
     * Set new informations in the waitress.
     * 
     * @param indexDishTable is the index of the dish/table in its list
     * @param entityType is the type of the entity (dish or table)
     * @param entityPosition is the position of the entity
     */
    void setWaiterssInfo(int indexDishTable, String entityType, Pair<Integer, Integer> entityPosition);

    /**
     * Set new coin number.
     * 
     * @param val is the coin number to be set
     */
    void setCoins(int val);

    /**
     * Tells is there are dishes not ready.
     * 
     * @return true if there are dish to prepare
     */
    boolean thereAreDishesToPrepare();

    /**
     * Returns the first dish in list not ready.
     * 
     * @return a dish to prepare in order
     */
    Optional<Dish> getDishToPrepare();

    /**
     * Complete the preparation of one dish.
     * 
     * @param dishNumber is the number of the dish that will be ready
     */
    void completeDishPreparation(int dishNumber);

    /**
     * It handles the earn generation when a table finish eating.
     */
    void earnMoneyFromTable();

    /**
     * Set the number of clients sitted at the table number "numberOfTable".
     * 
     * @param numberOfClient number of clients
     * @param numberOfTable number of the table
     */
    void setNumberOfClientsAtTable(int numberOfClient, int numberOfTable);

    /**
     * It tells the controller to add a dish to the view.
     * 
     * @param dish is the dish to be added
     */
    void addDishToView(Dish dish);

    /**
     * It tells the controller to remove a dish from the view.
     * 
     * @param dishIndex is the dish index
     */
    void removeDishInView(int dishIndex);

    /**
     * It enable a powerup for reducing dish preparation time.
     */
    void reduceDishPreparationTime();

    /**
     * It enable a powerup for incrementing waitress speed.
     */
    void increaseWaitressSpeed();

    /**
     * It enable a powerup for incrementing max customers that can leave
     * before the game ends.
     */
    void increaseMaxCustomerThatCanLeave();

    /**
     * It enable a powerup for incrementing the earned coins from a table.
     */
    void increaseGainMultiplier();

    /**
     * Returns the powerup prices.
     * 
     * @return the powerup prices
     */
    int[] getPowerUpsPrices();

    /**
     * Set max Customer who can leave before lose a game.
     * 
     * @param number max customer Number
     */
    void addMaxCustomerThatCanLeave(int number);

    /**
     * It tells the controller to update a dish in the view.
     * 
     * @param index is the dish index in the list
     * @param dish is the dish to be updated
     */
    void updateDishInView(int index, Dish dish);

    /**
     * Gives a dish by a given position.
     * 
     * @param pos is the dish position
     * @return the dish from the countertop
     */
    Optional<Dish> takeDishFromPosition(Pair<Integer, Integer> pos);

    /**
     * Tells if user can activate a powerup from its price,
     * and number of activations is less that a constant.
     * 
     * @param price is the powerup price
     * @return true if user can activate powerup
     */
    boolean canActivatePowerUp(int price);

    /**
     * Returns the list of tables.
     * 
     * @return the tables.
     */
    List<Table> getTableList();

    /**
     * Returns the list of cutomers.
     * 
     * @return the customers inside.
     */
    List<Customer> getCustomersList();

    /**
     * Returns the number of Coin Multiplier.
     * 
     * @return number of Coin multiplier
     */
    int getEnableCoinMultiplier();

    /**
     * Returns game waitress for testing purposes.
     * 
     * @return game waitress
     */
    Waitress getWaitress();

    /**
     * Returns game chef for testing purposes.
     * 
     * @return game chef
     */
    Chef getChef();

    /**
     * Returns the number of the table from a given position.
     * 
     * @param position is the table's position
     * @return Table's number 
     */
    int getTableNumberfromPosition(Pair<Integer, Integer> position);

}
