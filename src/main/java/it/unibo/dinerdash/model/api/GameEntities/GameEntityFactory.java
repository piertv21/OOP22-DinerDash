package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * This interface defines a Factory for Game Entities
 */
public interface GameEntityFactory {

    /**
     * Create a Chef instance.
     * 
     * {@inheritDoc}
     * 
     * @return a Chef instance
     */
    Chef createChef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Optional<Model> model);

    /**
     * Create a Customer instance.
     * 
     * {@inheritDoc}
     * 
     * @return a Customer instance
     */
    Customer createCustomer(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model, int numCust);

    /**
     * Create a Dish instance
     * 
     * {@inheritDoc}
     * 
     * @return a Dish instance
     */
    Dish createDish(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int dishNumber);

    /**
     * Create a Table instance
     * 
     * {@inheritDoc}
     * 
     * @return a Table instance
     */
    Table createTable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int i);

    /**
     * Create a Waitress instance
     * 
     * {@inheritDoc}
     * 
     * @return a Waitress instance
     */
    Waitress createWaitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Model model);
    
}
