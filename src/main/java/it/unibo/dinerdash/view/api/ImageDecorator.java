package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

public interface ImageDecorator extends GameEntityViewableDecorator {

    /**
     * Setter for Image of Table and Customer states.
     * 
     * @param icon  Image of Table and Customer states
     */
    void setState(Optional<Image> icon);

    /**
     * Getter for Image of Table and Customer states,
     * that return null if is not present.
     * 
     * @return Image of Table and Customer states
     */
    Optional<Image> getState();

}
