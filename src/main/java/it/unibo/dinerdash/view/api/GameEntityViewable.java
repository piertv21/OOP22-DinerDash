package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntity;
import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * GameEntity viewable for GUI representation
 */
public class GameEntityViewable extends GameEntityImpl {

    private Image icon;
    
    public GameEntityViewable(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Image icon) {
        super(coordinates, size);
        this.setIcon(icon);
    }

    public Image getIcon() {
        return this.icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    // Sync Viewable Entity with Model Entity
    public void update(GameEntity gameEntity) {
        var newPosition = gameEntity.getPosition();
        this.setPosition(newPosition);

        var newActiveStatus = gameEntity.isActive();
        this.setActive(newActiveStatus);
    }

}
