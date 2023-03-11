package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Dish used by chef for orders handling
 */
public class Dish extends GameEntity {

    private int dishNumber; // = numeroTavolo
    private boolean ready;

    public Dish(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        super(coordinates, icon);
        //TODO Auto-generated constructor stub
    }

    public boolean isReady() {
        return this.ready;
    }
    
}
