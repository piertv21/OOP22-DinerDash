package it.unibo.dinerdash.model.api.gameentities;
/**
 * This interface define the Dish and extends a Game Entity.
 */
public interface Dish extends GameEntity {
    /**
     * Returns the total number of dishes currently on the countertop. 
     * @return the total number of dishes on the countertop.
     */
    int getDishNumber();

}
