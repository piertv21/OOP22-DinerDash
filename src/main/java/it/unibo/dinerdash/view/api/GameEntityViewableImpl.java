package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * GameEntity viewable for GUI representation (Chef - Waitress - Dishes).
 */
public class GameEntityViewableImpl extends AbstractGameEntity implements GameEntityViewable {

    private Image icon;

    public GameEntityViewableImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final boolean active, final Image icon) {
        super(coordinates, size);
        this.setActive(active);
        this.icon = icon;
    }

    @Override
    public Image getIcon() {
        return this.icon;
    }

    @Override
    public void setIcon(final Image icon) {
        this.icon = icon;
    }

    @Override
    public void update(final Pair<Integer, Integer> coordinates, final boolean active) {
        this.setPosition(coordinates);
        this.setActive(active);
    }

}
