package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public abstract class AbstractGameEntityViewableDecorator implements GameEntityViewable {

    private final GameEntityViewable decorated;

    public AbstractGameEntityViewableDecorator(GameEntityViewable decorated) {
        this.decorated = decorated;
    }

    public GameEntityViewable getDecorated() {
        return decorated;
    }

    public Image getIcon() {
        return this.decorated.getIcon();
    }

    public void setIcon(Image icon) {
        this.decorated.setIcon(icon);
    }

    public void update(GameEntity gameEntity) {
        this.decorated.update(gameEntity);
    }

    public Pair<Integer, Integer> getPosition() {
        return this.decorated.getPosition();
    }

    public Pair<Integer, Integer> getSize() {
        return this.decorated.getSize();
    }

    public boolean isActive() {
        return this.decorated.isActive();
    }
    
}
