package it.unibo.dinerdash.utility.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.model.api.Constants;
import it.unibo.dinerdash.utility.api.ImageReader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Implementation of an Image Reader with Cache.
 */
public class ImageReaderWithCache implements ImageReader {

    private ImageReader imageReader;
    private Map<String, ImageIcon> cachedImages;

    /**
     * Class constructor.
     * 
     * @param root Defines the root path
     */
    public ImageReaderWithCache(String root) {
        imageReader = new ImageReaderImpl(root);
        cachedImages = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoot(String root) {
        imageReader.setRoot(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageIcon readImage(String name) {
        String imageName = extractImageNameFromPath(name);
        return cachedImages.computeIfAbsent(imageName, key -> imageReader.readImage(name));
    }

    private String extractImageNameFromPath(String path) {
        return Stream.of(path.trim())
            .map(p -> p.substring(0, p.lastIndexOf(".")))
            .map(p -> p.substring(p.lastIndexOf(Constants.SEP) + 1))
            .findFirst()
            .orElse(path);
    }    

    /**
     * Returns an image from the cache.
     * 
     * @param name Represents the image name without extension and relative path
     */
    public ImageIcon getCachedImage(String name) {
        return cachedImages.get(name);
    }

    /**
     * Clears cache of stored images.
     */
    public void clearCache() {
        cachedImages.clear();
    }

}
