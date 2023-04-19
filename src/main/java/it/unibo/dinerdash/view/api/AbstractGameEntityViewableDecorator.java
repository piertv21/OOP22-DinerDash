package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public abstract class AbstractGameEntityViewableDecorator implements GameEntityViewable {

    protected final GameEntityViewable decorated;

    protected AbstractGameEntityViewableDecorator(GameEntityViewable decorated) {
        this.decorated = decorated;
    }

    public GameEntityViewable getDecorated() {
        return this.decorated;
    }

    @Override
    public Image getIcon() {
        return this.decorated.getIcon();
    }

    @Override
    public void setIcon(Image icon) {
        this.decorated.setIcon(icon);
    }

    @Override
    public void update(GameEntity gameEntity) {
        this.decorated.update(gameEntity);
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
    public void setPosition(Pair<Integer, Integer> position) {
        this.decorated.setPosition(position);
    }

    @Override
    public void setSize(Pair<Integer, Integer> size) {
        this.decorated.setSize(size);
    }

    @Override
    public void setActive(boolean active) {
        this.decorated.setActive(active);
    }
    
}
