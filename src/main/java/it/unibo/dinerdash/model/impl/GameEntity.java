package it.unibo.dinerdash.model.impl;

import java.net.URL;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Basic class for game entities
 */
public abstract class GameEntity implements Runnable {
    
    private ImageIcon icon;
    Pair<Integer, Integer> coordinates;

    public void setPosition(Pair<Integer, Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public void setImage(String path) {
        URL imgURL = ClassLoader.getSystemResource(path);
        this.icon = new ImageIcon(imgURL);
    }

    public GameEntity(Pair<Integer, Integer> coordinates, String path) {
        this.setImage(path);
        this.setPosition(coordinates);
    }    

}
