package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public interface GameEntityViewable extends GameEntity {

    Image getIcon();

    void setIcon(Image icon);

    void update(Pair<Integer, Integer> coordinates, boolean active);
    
}
