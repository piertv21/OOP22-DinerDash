package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.utility.impl.Pair;

public interface GameView {

    void clear();

    void render();

    void restart();
    
    /**
     * add new customer in view list.
     * @param coordinates Position of the new customer
     * @param size image size of the new customer
     * @param active visibility of the new customer
     * @param multiplicity number of Customer
     * @param patience level of patience of the new customer
     */
    void addCustomerViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int multiplicity, int patience);

    /**
     * update a customer's information in view list.
     * @param index element of the view list to update 
     * @param coordinates new Position of customer
     * @param active new visibility of customer
     * @param patience new patience level of customer
     */
    void updateCustomersViewable(int index, Pair<Integer, Integer> coordinates, boolean active, int patience);

    /**
     * remove a customer from the view list.
     * @param index of the element to delete
     */
    void removeCustomerViewable(int index);
    
    void addChefViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active);

    void updateChefViewable(boolean active);
    
    void addWaitressViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numDishes);

    void updateWaitressViewable(Pair<Integer, Integer> coordinates, int numDishes);
    
    void addDishViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numTable);

    void updateDishesViewable(int index, boolean active);

    void deleteDishViewable(int index);
    
    void addTableViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int peopleNumer, String state);

    void updateTablesViewable(int index, int peopleNumber, String state);

    void updatePowerUpButton(int index, boolean active);

}
