package it.unibo.dinerdash.utility.impl;

import java.net.URL;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.api.ImageReader;

/**
 * {@inheritDoc}
 *
 * Implementation of the ImageReader interface
 */
public class ImageReaderImpl implements ImageReader {

    private String root;

    /**
     * Class constructor
     * 
     * @param root Defines the root path
     */
    public ImageReaderImpl(final String root) {
        this.root = root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoot(final String root) {
        this.root = root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageIcon readImage(final String name) {
        final URL imgURL = ClassLoader.getSystemResource(this.root + name);
        return new ImageIcon(imgURL);
    }
    
}
