package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.utility.Pair;

public abstract class GameEntityImpl implements GameEntity {

    private Pair<Integer, Integer> position;
    private Optional<Pair<Integer, Integer>> destination;
    private boolean active;

    public GameEntityImpl(Pair<Integer, Integer> coordinates) {
        this.setPosition(coordinates);
        this.setDestination(Optional.empty());
        this.setActive(true);
    }
    
    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public Optional<Pair<Integer, Integer>> getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(Optional<Pair<Integer, Integer>> destination) {
        this.destination = destination;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
    
}
