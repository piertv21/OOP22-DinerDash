package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.utility.impl.Pair;
/**
 * This interface defines the Countertop.
 */
public interface Countertop {
    /**
     * This method adds an order for a given table to the countertop.
     *
     * @param tableNumber the number of the table that placed the order.
     */
    void addOrder(int tableNumber);
    /**
     * This method takes a dish from the countertop base on the given pair of coordinates.
     * 
     * @param coordinates indicates the coordinates of the dish to be taken
     * @return an Optional object containing the taken dish,
     * if found an empty Optional otherwise.
     */
    Optional<Dish> takeDish(Pair<Integer, Integer> coordinates);
    /**
     * This method clears the countertop display by removing
     * all the dishes from the list of dishes.
     */
    void clear();
    /**
     * This method checks if there are any dishes on the countertop that need to be prepared.
     * @return true if there are dishes to be prepared.
     */
    boolean thereAreDishesToPrepare();
    /**
     * This method gets the next dish on the countertop that needs to be prepared.
     * @return an Optional object containing the next dish to prepare, if found;
     * an empty Optional otherwise.
     */
    Optional<Dish> getNextDishToPrepare();
    /**
     * This method sets a dish on the countertop as ready.
     * @param dish indicates the dish to be set as ready.
    */
    void setDishReady(Dish dish);

}
