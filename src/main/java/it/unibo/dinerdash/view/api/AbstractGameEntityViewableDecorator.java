package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.utility.impl.Pair;

/**
 * 
 */
public abstract class AbstractGameEntityViewableDecorator implements GameEntityViewableDecorator {

    protected final GameEntityViewable decorated;

    protected AbstractGameEntityViewableDecorator(final GameEntityViewable decorated) {
        this.decorated = decorated;
    }

    @Override
    public GameEntityViewable getDecorated() {
        return this.decorated;
    }

    @Override
    public Image getIcon() {
        return this.decorated.getIcon();
    }

    @Override
    public void setIcon(final Image icon) {
        this.decorated.setIcon(icon);
    }

    @Override
    public void update(final Pair<Integer, Integer> coordinates, final boolean active) {
        this.decorated.update(coordinates, active);
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.decorated.getPosition();
    }

    @Override
    public Pair<Integer, Integer> getSize() {
        return this.decorated.getSize();
    }

    @Override
    public boolean isActive() {
        return this.decorated.isActive();
    }

    @Override
    public void setPosition(final Pair<Integer, Integer> position) {
        this.decorated.setPosition(position);
    }

    @Override
    public void setSize(final Pair<Integer, Integer> size) {
        this.decorated.setSize(size);
    }

    @Override
    public void setActive(final boolean active) {
        this.decorated.setActive(active);
    }
    
}
