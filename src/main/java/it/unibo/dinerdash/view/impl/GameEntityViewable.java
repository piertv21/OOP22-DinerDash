package it.unibo.dinerdash.view.impl;

import java.awt.Image;



import it.unibo.dinerdash.model.impl.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * GameEntity on GUI
 */
public abstract class GameEntityViewable extends GameEntity {

    private Image icon;

    public GameEntityViewable(Pair<Integer, Integer> coordinates, Image icon2) {
        super(coordinates);
        this.setIcon(icon2);
    }

    public Image getIcon() {
        return this.icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }   

}
