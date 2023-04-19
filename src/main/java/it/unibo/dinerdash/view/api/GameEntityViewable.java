package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.GameEntities.GameEntity;

public interface GameEntityViewable extends GameEntity {

    Image getIcon();

    void setIcon(Image icon);

    void update(GameEntity gameEntity);
    
}
