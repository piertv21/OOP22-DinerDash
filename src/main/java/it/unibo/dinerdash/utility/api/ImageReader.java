package it.unibo.dinerdash.utility.api;

import javax.swing.ImageIcon;

public interface ImageReader {

    void setRoot(String root);

    ImageIcon readImage(String name);

}
