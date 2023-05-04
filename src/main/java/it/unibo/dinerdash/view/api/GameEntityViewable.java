package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.api.gameentities.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public interface GameEntityViewable extends GameEntity {

    /**
     * Getter for the Image which get draw in the view.
     * 
     * @return Image of the GameEntity 
     */
    Image getIcon();

    /**
     * Setter for the Image which get draw in the view.
     * 
     * @param icon Image which will get draw
     */
    void setIcon(Image icon);

    /**
     * Update the position and image visibility's 
     * informations of view version of GameEntity.
     * 
     * @param coordinates new position's coordinate
     * @param active new visibility's boolean
     */
    void update(Pair<Integer, Integer> coordinates, boolean active);
    
}
