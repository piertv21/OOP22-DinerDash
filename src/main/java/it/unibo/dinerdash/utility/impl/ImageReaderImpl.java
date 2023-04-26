package it.unibo.dinerdash.utility.impl;

import java.net.URL;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.api.ImageReader;

/*
 * Implementation of the {@link it.unibo.dinerdash.utility.api.ImageReader} interface
 */
public class ImageReaderImpl implements ImageReader {

    private String root;

    /*
     * Class constructor
     * 
     * @param root Defines the root path
     */
    public ImageReaderImpl(String root) {
        this.setRoot(root);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void setRoot(String root) {
        this.root = root;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public ImageIcon readImage(String name) {
        final URL imgURL = ClassLoader.getSystemResource(this.root + name);
        final ImageIcon icon = new ImageIcon(imgURL);
        return icon;
    }
    
}
