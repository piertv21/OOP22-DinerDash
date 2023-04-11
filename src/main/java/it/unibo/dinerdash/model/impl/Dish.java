package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.AbstractGameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Dish used by chef for orders handling
 */
public class Dish extends AbstractGameEntity {

    private int dishNumber; // = numeroTavolo

    public Dish(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int dishNumber) {
        super(coordinates, size);
        this.dishNumber = dishNumber;
        this.setReady(false);
    }

    public int getDishNumber() {
        return this.dishNumber;
    }

    public boolean isReady() {
        return this.isActive();
    }

    public void setReady(boolean ready) {
        this.setActive(true);
    }   
    
}
