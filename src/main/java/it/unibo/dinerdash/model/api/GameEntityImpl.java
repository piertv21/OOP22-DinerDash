package it.unibo.dinerdash.model.api;

import it.unibo.dinerdash.utility.impl.Pair;

public abstract class GameEntityImpl implements GameEntity {

    private Pair<Integer, Integer> position;
    private Pair<Integer, Integer> size;
    private boolean active;

    public GameEntityImpl(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size) {
        this.setPosition(coordinates);
        this.setSize(size);
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
    public Pair<Integer, Integer> getSize() {
        return this.size;
    }

    @Override
    public void setSize(Pair<Integer, Integer> size) {
        this.size = size;
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
