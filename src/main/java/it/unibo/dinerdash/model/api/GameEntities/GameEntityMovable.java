package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Pair;

/**
 * This interface defines a Game Entity Movable.
 */
public interface GameEntityMovable extends GameEntity {

    /**
     * Getter for a MovableEntity' Destination.
     * 
     * @return the coordinates of MovableEntity' Destination or empty()
     */
    Optional<Pair<Integer, Integer>> getDestination();

    /**
     * Setter for a MovableEntity' Destination.
     * 
     * @param destination are the coordinates of the destination
     */
    void setDestination(Optional<Pair<Integer, Integer>> destination);

    /**
     * Getter for speed's multiplier.
     * 
     * @return value of speed's multiplier 
     */
    int getMovementSpeed();

    /**
     * Setter for the speed's multiplier.
     * 
     * @param speed indicate the distance that the entity can do with 1 step
     */
    void setMovementSpeed(int speed);

    /**
     * Handles 1 single movement to the destination.
     * 
     * @param range space used to choose which movement do relatives 
     * to Destination's Point
     */
    void handleMovement(int range);

}
