package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

public abstract class GameEntity {

    private ImageIcon icon;
    private Optional<Pair<Integer, Integer>> position;
    private Optional<Pair<Integer, Integer>> destination;

    public GameEntity(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        this.position = Optional.ofNullable(coordinates);
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public Optional<Pair<Integer, Integer>> getPosition() {
        return this.position;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = Optional.ofNullable(position);
    }

    public Optional<Pair<Integer, Integer>> getDestination() {
        return this.destination;
    }

    public void setDestination(Pair<Integer, Integer> destination) {
        this.destination = Optional.ofNullable(destination);
    }   

}
