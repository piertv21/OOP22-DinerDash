package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Pair;

public abstract class GameEntity {

    private Pair<Integer, Integer> position;
    private Optional<Pair<Integer, Integer>> destination;
    private boolean active;

    public GameEntity(Pair<Integer, Integer> coordinates) {
        this.setPosition(coordinates);
        this.setDestination(Optional.empty());
        this.setActive(true);
    }

    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    public Optional<Pair<Integer, Integer>> getDestination() {
        return this.destination;
    }

    public void setDestination(Optional<Pair<Integer, Integer>> destination) {
        this.destination = destination;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
