package it.unibo.dinerdash.model.api.gameentities;

import java.util.Optional;

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

    /**
     * Chef begins the preparation of the passed dish
     * @param dish is the dish that needs to be prepared
     */
    void startPreparingDish(Dish dish);

    /**
     * Complete the current dish preparation
     */
    void completeCurrentDish();

    /**
     * Get the dish the chef is preparing, if present
     * @return the current dish
     */
    Optional<Dish> getCurrentDish();

    /**
     * Returns the preparation time of a dish if the chef is preparing one
     * @return the dish preparation time
     */
    Optional<Long> getTimeDishReady();

    /**
     * Returns the number of powerups activated
     * @return number of powerups
     */
    int getEnabledPowerUps();

}
