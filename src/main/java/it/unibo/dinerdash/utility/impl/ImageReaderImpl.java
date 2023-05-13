package it.unibo.dinerdash.utility.impl;

import java.io.IOException;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.api.ImageReader;

/**
 * {@inheritDoc}
 *
 * Implementation of the ImageReader interface.
 */
public class ImageReaderImpl implements ImageReader {

    private String root;

    /**
     * Class constructor.
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
        try (var inputStream = ClassLoader.getSystemResourceAsStream(this.root + name)) {
            return new ImageIcon(inputStream.readAllBytes());
        } catch (IOException | SecurityException e) {
            Runtime.getRuntime().exit(0);
        }
        return null;
    }

}
