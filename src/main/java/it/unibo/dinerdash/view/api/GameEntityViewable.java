package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public interface GameEntityViewable {

    Image getIcon();

    void setIcon(Image icon);

    void update(GameEntity gameEntity);

    Pair<Integer, Integer> getPosition();

    Pair<Integer, Integer> getSize();

    boolean isActive();
    
}
