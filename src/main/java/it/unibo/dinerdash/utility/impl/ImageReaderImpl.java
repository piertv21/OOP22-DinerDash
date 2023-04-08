package it.unibo.dinerdash.utility.impl;

import java.net.URL;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.api.ImageReader;

/*
 * Simple Image Reader
 */
public class ImageReaderImpl implements ImageReader {

    private String root;

    public ImageReaderImpl(String root) {
        this.setRoot(root);
    }

    @Override
    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    public ImageIcon readImage(String name) {
        final URL imgURL = ClassLoader.getSystemResource(this.root + name);
        final ImageIcon icon = new ImageIcon(imgURL);
        return icon;
    }
    
}
