package it.unibo.dinerdash.view.api;

import java.awt.Image;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameEntityViewableDecorator interface.
 */
public abstract class AbstractGameEntityViewableDecorator implements GameEntityViewableDecorator {

    private final GameEntityViewable decorated;

    /**
     * Class constructor.
     * 
     * @param decorated is the GameEntityViewable to be decorated
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "It is necessary to store a GameEntityViewable"
        + "for the creation of the decorator")
    public AbstractGameEntityViewableDecorator(final GameEntityViewable decorated) {
        this.decorated = decorated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "It is necessary to have a reference to the decorated object"
        + "in order to access it")
    public GameEntityViewable getDecorated() {
        return this.decorated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getIcon() {
        return this.decorated.getIcon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIcon(final Image icon) {
        this.decorated.setIcon(icon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Pair<Integer, Integer> coordinates, final boolean active) {
        this.decorated.update(coordinates, active);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.decorated.getPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getSize() {
        return this.decorated.getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return this.decorated.isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Pair<Integer, Integer> position) {
        this.decorated.setPosition(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSize(final Pair<Integer, Integer> size) {
        this.decorated.setSize(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActive(final boolean active) {
        this.decorated.setActive(active);
    }

}
