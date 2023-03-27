package it.unibo.dinerdash.view.api;

import java.awt.Image;

import it.unibo.dinerdash.model.impl.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * GameEntity viewable for GUI representation
 */
public class GameEntityViewable extends GameEntity {

    private Image icon;
    private Pair<Integer, Integer> size;

    public GameEntityViewable(Pair<Integer, Integer> coordinates, Image icon) {
        super(coordinates);
        this.setIcon(icon);
    }

    public Image getIcon() {
        return this.icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public Pair<Integer, Integer> getSize() {
        return size;
    }

    public void setSize(Pair<Integer, Integer> size) {
        this.size = size;
    }

}
