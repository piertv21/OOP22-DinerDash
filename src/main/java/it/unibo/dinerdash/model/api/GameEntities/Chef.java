package it.unibo.dinerdash.model.api.GameEntities;

/**
 * This interface defines a Chef that extends Game Entity
 */
public interface Chef extends GameEntity {
    
    /**
     * This method updates the chef according to
     * his status.
     * 
     * It checks if there are any dishes to be prepared
     * and if there is at least 1 start preparing it.
     * If he already has a dish he continues to prepare it
     * while if it is ready he tells the Countertop that it is ready.
     */
    void update();

    /**
     * Method called by the Model, upon activation of PowerUp,
     * to reduce the preparation time of dishes.
     */
    void reducePreparationTime();

}
