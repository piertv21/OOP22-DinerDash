package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * GameEntity viewable for GUI representation (Chef - Waitress - Dishes)
 */
public class GameEntityViewable extends AbstractGameEntity {

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
    
    public void update(GameEntity gameEntity) {
        var newPosition = gameEntity.getPosition();
        this.setPosition(newPosition);

        var newActiveStatus = gameEntity.isActive();
        this.setActive(newActiveStatus);
    }

}
