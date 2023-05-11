package it.unibo.dinerdash.view.api;

/**
 * This interface represents a generic view component.
 *
 * @param <X> is the view component type
 */
public interface GamePanel<X> {

    /**
     * Returns the generic view component.
     * 
     * @return the view component
     */
    X getComponent();

}
