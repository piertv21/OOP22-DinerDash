package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Dish used by chef for orders handling
 */
public class Dish extends GameEntity {

    private int dishNumber; // = numeroTavolo
    private boolean ready;

    public Dish(Pair<Integer, Integer> coordinates) {
        super(coordinates);
        //TODO Auto-generated constructor stub
    }

    public boolean isReady() {
        return this.ready;
    }
    
}
