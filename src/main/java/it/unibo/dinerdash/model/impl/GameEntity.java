package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

public abstract class GameEntity {

    private ImageIcon icon;
    private Pair<Integer, Integer> position;
    private Pair<Integer, Integer> destination;

    public GameEntity(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        this.position = coordinates;
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    public Pair<Integer, Integer> getDestination() {
        return this.destination;
    }

    public void setDestination(Pair<Integer, Integer> destination) {
        this.destination = destination;
    }   

}
