package it.unibo.dinerdash.controller.api;

import it.unibo.dinerdash.model.api.gameentities.Chef;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.GameState;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.View;

/**
 * This interface defines the game Controller,
 * responsible for calling updates on the Model
 * and synchronizing the View.
 */
public interface Controller {

    /**
     * Sets the general View on which it works.
     * 
     * @param view is the View
     */
    void setView(View view);

    /**
     * Start the game on the Game view.
     * 
     * @param gameView is the panel on which the game run
     */
    void start(GameView gameView);

    /**
     * Start the game over by resetting
     * the Model and View structures.
     */
    void restart();

    /**
     * Pauses the game, including the
     * remaining time timer.
     */
    void pause();

    /**
     * Restores the game execution
     * from where it had arrived.
     */
    void resume();

    /**
     * Closes the application in case
     * you have never played the game.
     */
    void quitWithoutPlaying();

    /**
     * Closes the application in case you have played,
     * resetting stored data.
     */
    void quit();

    /**
     * Update the game state in the Model and redraw the View.
     */
    void updateGame();

    /**
     * Stops the game loop, game timer and shows the Game Over view.
     */
    void gameIsEnded();

    /**
     * Returns the number of available coins.
     * 
     * @return number of available coins from Model
     */
    int getCoins();

    /**
     * Get the remaining time.
     * 
     * @return the remaining time from Model
     * formatted in mm:ss
     */
    String getRemainingTime();

    /**
     * Returns the number of clients who left.
     * 
     * @return the number of clients who left
     * with the format: Clients who left / Maximum number of clients who can leave
     */
    String getCustomersWhoLeft();

    /**
     * getter for model's background's Width.
     * 
     * @return model's background's Width
     */
    int getRestaurantWidth();

    /**
     * getter for model's background's Height.
     * 
     * @return model's background's Height
     */
    int getRestaurantHeight();

    /**
     * Call the update on the Game View.
     */
    void updateView();

    /**
     * Call the waitress.
     * 
     * @param indexOf is the index of dishes/tables in the relevant lists
     * @param s is the type of object (table or plate)
     * @param position is the object position
     */
    void callWaitress(int indexOf, String s, Pair<Integer, Integer> position);

    /**
     * call a view method to add a new customer whom 
     * will get created in the view.
     * 
     * @param customer customer whom will be added
     */
    void addCustomerToView(Customer customer);

    /**
     * call a view method to update the information 
     * of the customer in the view list using index 
     * to find it , and customer's to get new informations.
     * 
     * @param index of the customer in the list
     * @param customer whom will get update
     */
    void updateCustomersInView(int index, Customer customer);

    /**
     * call view method to delete the index element.
     * 
     * @param index of the customer whom need to delete
     */
    void removeCustomerInView(int index);

    /**
     * Adds the chef to the view.
     * 
     * @param chef is the Chef from Model
     */
    void addChefToView(Chef chef);

    /**
     * Update the chef in the view.
     * 
     * @param chef is the Chef from Model
     */
    void updateChefInView(Chef chef);

    /**
     * Adds the waitress to the view.
     * 
     * @param waitress is the waitress from Model
     */
    void addWaitressToView(Waitress waitress);

    /**
     * Update the waitress in the view.
     * 
     * @param waitress is the waitress from Model
     */
    void updateWaitressInView(Waitress waitress);

    /**
     * Adds a dish to the view.
     * 
     * @param dish is the dish from Model
     */
    void addDishToView(Dish dish);

    /**
     * Update a dish in the view.
     * 
     * @param index is the dish index from Model list
     * @param dish is the dish from Model
     */
    void updateDishesInView(int index, Dish dish);

    /**
     * Delete a dish in the view.
     * 
     * @param index is the dish index from Model list
     */
    void deleteDishInView(int index);

    /**
     * Adds a table to the view.
     * 
     * @param table is the table from Model
     */
    void addTableToView(Table table);

    /**
     * Update a table in the view.
     * 
     * @param index is the table index from Model list
     * @param table is the table from Model
     */
    void updateTablesInView(int index, Table table);

    /**
     * Activates a powerup.
     * 
     * @param index is the powerup selected
     */
    void enablePowerUp(int index);

    /**
     * Returns the prices of powerups in order.
     * 
     * @return the prices of powerups
     */
    int[] getPowerUpsPrices();

    /**
     * Update powerup buttons in the view by setting them
     * as clickable or non-clickable.
     */
    void updatePowerUpsButtonsInView();

    /**
     * Return the current GameState.
     * 
     * @return the GameState
     */
    GameState getGameState();

}
