package it.unibo.dinerdash.model.impl;

import java.net.URL;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

public abstract class GameEntity {

    private ImageIcon icon;
    private Pair<Integer, Integer> position;

    public GameEntity(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        this.position = coordinates;
        this.icon = icon;
    }

    public void setPosition(Pair<Integer, Integer> coordinates) {
        this.position = coordinates;
    }

    public void setImage(String path) {
        URL imgURL = ClassLoader.getSystemResource(path);
        this.icon = new ImageIcon(imgURL);
    }

    public ImageIcon getImage() {
        return this.icon;
    }

    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

}
