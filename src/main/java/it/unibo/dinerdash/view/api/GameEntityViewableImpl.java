package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * GameEntity viewable for GUI representation (Chef - Waitress - Dishes)
 */
public class GameEntityViewableImpl extends AbstractGameEntity implements GameEntityViewable {

    private Image icon;

    public GameEntityViewableImpl(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Image icon) {
        super(coordinates, size);
        this.setIcon(icon);
    }

    @Override
    public Image getIcon() {
        return this.icon;
    }

    @Override
    public void setIcon(Image icon) {
        this.icon = icon;
    }

    @Override
    public void update(GameEntity gameEntity) {
        var newPosition = gameEntity.getPosition();
        this.setPosition(newPosition);

        var newActiveStatus = gameEntity.isActive();
        this.setActive(newActiveStatus);
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return super.getPosition();
    }

    @Override
    public Pair<Integer, Integer> getSize() {
        return super.getSize();
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

}
