package it.unibo.dinerdash.utility.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.api.ImageReader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Implementation of an Image Reader with Cache.
 */
public class ImageReaderWithCache implements ImageReader {

    private final ImageReader imageReader;
    private final Map<String, ImageIcon> cachedImages;

    /**
     * Class constructor.
     * 
     * @param root Defines the root path
     */
    public ImageReaderWithCache(final String root) {
        imageReader = new ImageReaderImpl(root);
        cachedImages = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoot(final String root) {
        imageReader.setRoot(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageIcon readImage(final String name) {
        final String imageName = extractImageNameFromPath(name);
        return cachedImages.computeIfAbsent(imageName, key -> imageReader.readImage(name));
    }

    private String extractImageNameFromPath(final String path) {
        return Stream.of(path.trim())
            .map(p -> p.substring(0, p.lastIndexOf(".")))
            .map(p -> p.substring(p.lastIndexOf("/") + 1))
            .findFirst()
            .orElse(path);
    }

    /**
     * Returns an image from the cache.
     * 
     * @param name Represents the image name without extension and relative path
     * @return the cached image
     */
    public ImageIcon getCachedImage(final String name) {
        return cachedImages.get(name);
    }

    /**
     * Clears cache of stored images.
     */
    public void clearCache() {
        cachedImages.clear();
    }

}
