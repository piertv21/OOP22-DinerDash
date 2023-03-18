package it.unibo.dinerdash.view.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.model.impl.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * GameEntity on GUI
 */
public abstract class GameEntityViewable extends GameEntity {

    private ImageIcon icon;

    public GameEntityViewable(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        super(coordinates);
        this.setIcon(icon);
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    

}
