package it.unibo.dinerdash.utility.api;

import javax.swing.ImageIcon;

/**
 *  This interface defines a simple Image reader.
 */
public interface ImageReader {

    /**
     * Set the basic images path.
     * 
     * @param root Defines the root path
     */
    void setRoot(String root);

    /**
     * Reads an image.
     * 
     * @param name Defines the name of the image to be read
     * @return The read image
     */
    ImageIcon readImage(String name);

}
