package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameEntity interface.
 */
public abstract class AbstractGameEntity implements GameEntity {

    private Pair<Integer, Integer> position;
    private Pair<Integer, Integer> size;
    private boolean active;

    /**
     * Class constructor.
     * 
     * @param coordinates is the entity coordinate pair
     * @param size is the entity size pair
     */
    public AbstractGameEntity(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size) {
        this.setPosition(coordinates);
        this.setSize(size);
        this.setActive(true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getSize() {
        return this.size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSize(Pair<Integer, Integer> size) {
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

}
