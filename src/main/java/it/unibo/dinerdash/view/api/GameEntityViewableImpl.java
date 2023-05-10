package it.unibo.dinerdash.view.api;

import java.awt.Image;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameEntityViewable interface.
 */
public class GameEntityViewableImpl extends AbstractGameEntity implements GameEntityViewable {

    private Image icon;

    /**
     * Class constructor.
     * 
     * @param coordinates are the initial coordinates of the graphic entity
     * @param size is the initial size of the graphic entity
     * @param active is the initial active state of the graphic entity
     * @param icon is the icon of the the graphic entity
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Image is required and is not externally modified")
    public GameEntityViewableImpl(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final boolean active,
        final Image icon
    ) {
        super(coordinates, size);
        this.setActive(active);
        this.icon = icon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "It is necessary to return the image in order to use it")
    public Image getIcon() {
        return this.icon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "It is necessary to store the new image in case it needs"
        + "to be changed during execution")
    public void setIcon(final Image icon) {
        this.icon = icon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Pair<Integer, Integer> coordinates, final boolean active) {
        this.setPosition(coordinates);
        this.setActive(active);
    }

}
