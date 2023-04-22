package it.unibo.dinerdash.controller.api;

import it.unibo.dinerdash.model.api.GameEntities.Chef;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.GameEntities.Waitress;
import it.unibo.dinerdash.utility.impl.Pair;
import it.unibo.dinerdash.view.api.GameView;
import it.unibo.dinerdash.view.api.View;

public interface Controller {

    void setView(View view);

    void start(GameView gameView);

    void restart();

    void pause();

    void resume();

    void quit();

    void gameIsEnded();

    void syncChanges();

    int getCoins();

    int getRemainingTime();

    String convertToMinutesAndSeconds(int seconds);

    boolean gameOver();

    /**
     * getter for model's background's Width
     * @return model's background's Width
     */
    int getRestaurantWidth();

    /**
     * getter for model's background's Height
     * @return model's background's Height
     */
    int getRestaurantHeight();

    void updateView();

    void setWaitressDestination(Pair<Integer, Integer> destination);

    void callWaitress(int indexOf, String s, Pair<Integer, Integer> position);

    // ----
    /**
     * call a view method to add a new customer whom 
     * will get created in the view
     * @param customer customer whom will be added
     */
    void addCustomerToView(Customer customer);

    /**
     * call a view method to update the information 
     * of the customer in the view list using index 
     * to find it , and customer's to get new informations
     * @param index of the customer in the list
     * @param customer whom will get update
     */
    void updateCustomersInView(int index, Customer customer);

    /**
     * call view method to delete the index element
     * @param index of the customer whom need to delete
     */
    void removeCustomerInView(int index);
    
    void addChefToView(Chef chef);

    void updateChefInView(Chef chef);
    
    void addWaitressToView(Waitress waitress);

    void updateWaitressInView(Waitress waitress);
    
    void addDishToView(Dish dish);

    void updateDishesInView(int index, Dish dish);

    void deleteDishInView(int index);

    void addTableToView(Table table);

    void updateTablesInView(int index, Table table);

}
