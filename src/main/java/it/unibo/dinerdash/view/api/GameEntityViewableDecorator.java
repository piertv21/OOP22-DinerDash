package it.unibo.dinerdash.view.api;

/**
 * This interface defines a decorator for a GameEntityViewable.
 */
public interface GameEntityViewableDecorator extends GameEntityViewable {

    /**
     * Gives the decorated object.
     * 
     * @return the decirated GameEntityViewable
     */
    GameEntityViewable getDecorated();

}
