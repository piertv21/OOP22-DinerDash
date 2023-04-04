package it.unibo.dinerdash.utility;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/*
 * Simple multipurpose Image Cacher
 */
public class ImageCacher {

    private Map<String, ImageIcon> cachedImages;
    private String root;

    public ImageCacher(String root) {
        this.cachedImages = new HashMap<>();
        this.setRoot(root);
    }

    public void addImage(String name, String fileName) {        
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
    
    private ImageIcon loadImage(String path) {
        final URL imgURL = ClassLoader.getSystemResource(this.root + path);
        final ImageIcon icon = new ImageIcon(imgURL);
        return icon;
    }

}
