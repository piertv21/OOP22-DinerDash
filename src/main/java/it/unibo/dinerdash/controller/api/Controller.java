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

    void syncChanges();

    int getCoins();

    int getRemainingTime();

    String convertToMinutesAndSeconds(int seconds);

    boolean gameOver();

    int getRestaurantWidth();

    int getRestaurantHeight();

    void timeIsChanged();

    void setWaitressDestination(Pair<Integer, Integer> destination);

    void callWaitress(int indexOf, String s, Pair<Integer, Integer> position);


    // ----
    void addCustomerToView(Customer customer);

    void updateCustomersInView(int index, Customer customer);

    void removeCustomerInView(int index);
    // ----

    // ----
    void addChefToView(Chef chef);

    void updateChefInView(Chef chef);
    // ----

    // ----
    void addWaitressToView(Waitress waitress);

    void updateWaitressInView(Waitress waitress);
    // ----

    void addDishToView(Dish dish);

    void updateDishesInView(int index, Dish dish);

    void deleteDishInView(int index);
    // ----

    void addTableToView(Table table);

    void updateTablesInView(int index, Table table);

    void addWaitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numDishes);

}
