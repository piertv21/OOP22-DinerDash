package it.unibo.dinerdash.utility.api;

import java.net.URL;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Basic class for game entities
 */
public abstract class GameEntity {
    
    private ImageIcon icon;
    Pair<Integer, Integer> coordinates;

    public void setPosition(int x, int y) {
        this.coordinates = new Pair<Integer,Integer>(x, y);
    }

    public void setImage(String path) {
        URL imgURL = ClassLoader.getSystemResource(path);
        this.icon = new ImageIcon(imgURL);
    }

}
