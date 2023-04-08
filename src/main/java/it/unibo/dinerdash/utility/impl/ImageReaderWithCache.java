package it.unibo.dinerdash.utility.impl;

import javax.swing.ImageIcon;
import it.unibo.dinerdash.utility.api.ImageReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Image Reader with Cache (Proxy Pattern)
 */
public class ImageReaderWithCache implements ImageReader {

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
        return Arrays.stream(path.split("/"))
                .map(fileNameWithExtension -> {
                    int dotIndex = fileNameWithExtension.lastIndexOf(".");
                    return (dotIndex != -1) ? fileNameWithExtension.substring(0, dotIndex) : fileNameWithExtension;
                })
                .reduce((first, second) -> second)
                .orElse("");
    }    

    public ImageIcon getCachedImage(String name) {
        return cachedImages.get(name);
    }

    public void clearCache() {
        cachedImages.clear();
    }

}
