package it.unibo.dinerdash.view.api;

import java.util.Optional;

import java.awt.Image;

/**
 * This interface defines a decorator that
 * adds a Image field to a GameEntityViewable.
 */
public interface ImageDecorator extends GameEntityViewableDecorator {

    /**
     * Setter for Image.
     * 
     * @param icon is the Image to be set
     */
    void setImage(Optional<Image> icon);

    /**
     * Getter for Image, it returns empty() if is not present.
     * 
     * @return the Optional Image
     */
    Optional<Image> getImage();

}
