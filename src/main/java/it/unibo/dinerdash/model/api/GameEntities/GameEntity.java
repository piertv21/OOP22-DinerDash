package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * This interface defines a Game Entity
 */
public interface GameEntity {

    /*
     * Returns the GameEntity position.
     * 
     * @return A pair of Integer
     * containing the x and y coordinates of the GameEntity
     */
    Pair<Integer, Integer> getPosition();

    /*
     * Set the the GameEntity location.
     * 
     * @param position are the coordinates
     * to be set
     */
    void setPosition(Pair<Integer, Integer> position);

    /*
     * Returns the GameEntity size.
     * 
     * @return a pair of Integers
     * with the GameEntity size 
     */
    Pair<Integer, Integer> getSize();

    /*
     * Set the GameEntity size.
     * 
     * @param size are the dimensions
     * (width, height) to be set
     */
    void setSize(Pair<Integer, Integer> size);

    /*
     * Tells if the GameEntity is currently active
     * and will need to appear in the game.
     * 
     * @return true if GameEntity is active
     */
    boolean isActive();

    /*
     * Set the GameEntity active state
     * 
     * @param active is the active state to be set
     */
    void setActive(boolean active);
    
}
