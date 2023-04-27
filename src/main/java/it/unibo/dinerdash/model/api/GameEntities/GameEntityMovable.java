package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Pair;

public interface GameEntityMovable extends GameEntity {

    /**
     * Getter for a MovableEntity' Destination
     * @return coordinates of MovableEntity' Destination or null
     */
    Optional<Pair<Integer, Integer>> getDestination();

    /**
     * Setter for a MovableEntity' Destination
     * @param destination Coordinates of the destination
     */
    void setDestination(Optional<Pair<Integer, Integer>> destination);

    /**
     * Getter for speed's moltiplicator
     * @return value of speed's moltiplicator 
     */
    int getMovementSpeed();

    /**
     * Setter for the speed's moltiplicator
     * @param speed indicate the distance that the entity can do with 1 step
     */
    void setMovementSpeed(int speed);

    /**
     * Make the Customer and Waitress entity moves
     * @param range space used to choose which movement do relatives 
     * to Destination's Point
     */
    void handleMovement(int range);

}
