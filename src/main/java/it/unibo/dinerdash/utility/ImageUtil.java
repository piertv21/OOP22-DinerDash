package it.unibo.dinerdash.utility;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/*
 * Simple Image reader with built-in cacher
 */
public class ImageUtil {

    private Map<String, ImageIcon> cachedImages;
    private String root;

    public ImageUtil(String root) {
        this.cachedImages = new HashMap<>();
        this.setRoot(root);
    }

    public ImageIcon loadImage(String name) {
        final URL imgURL = ClassLoader.getSystemResource(root + name);
        final ImageIcon icon = new ImageIcon(imgURL);
        return icon;
    }

    public void cacheImage(String name, String fileName) {        
        this.cachedImages.put(name, loadImage(fileName));
    }

    public ImageIcon getCachedImage(String name) {
        return this.cachedImages.get(name);
    }

    public void clear() {
        this.cachedImages.clear();
    }

    public void setRoot(String root) {
        this.root = root;
    }

}
