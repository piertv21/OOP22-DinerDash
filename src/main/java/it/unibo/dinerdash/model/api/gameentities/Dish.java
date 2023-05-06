package it.unibo.dinerdash.model.api.gameentities;

/**
 * This interface define the Dish that extends a Game Entity.
 */
public interface Dish extends GameEntity {

    /**
     * Returns the dish number.
     * 
     * @return the dish number which corresponds
     * to the number of the table
     */
    int getDishNumber();

}
