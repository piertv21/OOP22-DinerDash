package it.unibo.dinerdash.utility.impl;

import javax.swing.ImageIcon;
import it.unibo.dinerdash.utility.api.ImageReader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/*
 * Image Reader with Cache (Proxy Pattern)
 */
public class ImageReaderWithCache implements ImageReader {

    private static final String SEP = System.getProperty("file.separator");

    private ImageReader imageReader;
    private Map<String, ImageIcon> cachedImages;

    public ImageReaderWithCache(String root) {
        imageReader = new ImageReaderImpl(root);
        cachedImages = new HashMap<>();
    }

    @Override
    public void setRoot(String root) {
        imageReader.setRoot(root);
    }

    @Override
    public ImageIcon readImage(String name) {
        String imageName = extractImageNameFromPath(name);
        return cachedImages.computeIfAbsent(imageName, key -> imageReader.readImage(name));
    }

    private String extractImageNameFromPath(String path) {
        return Stream.of(path.trim())
                .map(p -> p.substring(0, p.lastIndexOf(".")))
                .map(p -> p.substring(p.lastIndexOf(SEP) + 1))
                .findFirst()
                .orElse(path);
    }    

    public ImageIcon getCachedImage(String name) {
        return cachedImages.get(name);
    }

    public void clearCache() {
        cachedImages.clear();
    }

}
