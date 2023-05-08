package it.unibo.dinerdash.view.api;

import it.unibo.dinerdash.utility.impl.Pair;

/**
 * This interface represents the main game panel in the View.
 */
public interface GameView {

    /**
     * Cleans up the graphical game structures.
     */
    void clear();

    /**
     * Draw a game frame representing both entities
     * and labels with game data.
     */
    void render();

    /**
     * Sets up game structures for restarting.
     */
    void restart();

    /**
     * Add new customer in view list.
     * 
     * @param coordinates Position of the new customer
     * @param size image size of the new customer
     * @param active visibility of the new customer
     * @param multiplicity number of Customer
     * @param patience level of patience of the new customer
     */
    void addCustomerViewable(
        Pair<Integer, Integer> coordinates,
        Pair<Integer, Integer> size,
        boolean active, int multiplicity,
        int patience
    );

    /**
     * Update a customer's information in view list.
     * 
     * @param index element of the view list to update 
     * @param coordinates new Position of customer
     * @param active new visibility of customer
     * @param patience new patience level of customer
     */
    void updateCustomersViewable(int index, Pair<Integer, Integer> coordinates, boolean active, int patience);

    /**
     * Remove a customer from the view list.
     * 
     * @param index of the element to delete
     */
    void removeCustomerViewable(int index);

    /**
     * Add new Chef in view list.
     * 
     * @param coordinates is the Position of the Chef
     * @param size is the image size of the Chef
     * @param active is the visibility of the Chef
     */
    void addChefViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active);

    /**
     * Update the Chef in the game view.
     * 
     * @param active tells if chef must be drawn
     */
    void updateChefViewable(boolean active);

    /**
     * Adds a waitress to view.
     * 
     * @param coordinates is the waitress position
     * @param size is the waitress size
     * @param active is the waitress state
     * @param numDishes number of dishes the waitress is bringing
     */
    void addWaitressViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numDishes);

    /**
     * Update waitress state in view.
     * 
     * @param coordinates are the new coordinates
     * @param numDishes is the new number of dishes the waitress is bringing
     */
    void updateWaitressViewable(Pair<Integer, Integer> coordinates, int numDishes);

    /**
     * Add a dish to the view list.
     * 
     * @param coordinates are the dish coordinates
     * @param size is the dish size
     * @param active is the dish state (true if ready)
     * @param numTable is the dish number, it represents the table
     * the dish belong to
     */
    void addDishViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, boolean active, int numTable);

    /**
     * Update a dish in view list.
     * 
     * @param index is the dish index
     * @param active is the dish state (true if ready)
     */
    void updateDishesViewable(int index, boolean active);

    /**
     * Delete a dish in the view list.
     * 
     * @param index is the dish index
     */
    void deleteDishViewable(int index);

    /**
     * Add a table to the view list.
     * 
     * @param coordinates are the table coordinates
     * @param size is the table size
     * @param active is the table state
     * @param peopleNumer is the number of people sitting at the table
     * @param state is the table state
     */
    void addTableViewable(
        Pair<Integer, Integer> coordinates,
        Pair<Integer, Integer> size,
        boolean active, int peopleNumer,
        String state
    );

    /**
     * Update a table in view list.
     * 
     * @param index is the table index
     * @param peopleNumber is the new number of people sitting at the table
     * @param state is the new table state
     */
    void updateTablesViewable(int index, int peopleNumber, String state);

    /**
     * Update the powerup buttons in view.
     * 
     * @param index is the button index
     * @param active is the button state (true if enabled)
     */
    void updatePowerUpButton(int index, boolean active);

}
