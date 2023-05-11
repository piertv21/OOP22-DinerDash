package it.unibo.dinerdash.view.api;

/**
 * This interface contains a generic game panel.
 */
public interface GamePanel<X> {

    /**
     * 
     * @return
     */
    View getUserInterface();

    /**
     * 
     * @return
     */
    X getComponent();

}