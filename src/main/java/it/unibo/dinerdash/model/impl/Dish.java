package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Dish used by chef for orders handling
 */
public class Dish extends GameEntityImpl {

    private int dishNumber; // = numeroTavolo
    private boolean ready;

    public Dish(Pair<Integer, Integer> coordinates, int dishNumber) {
        super(coordinates);
        this.setDishNumber(dishNumber);
        this.setReady(false);
    }

    public int getDishNumber() {
        return this.dishNumber;
    }

    public void setDishNumber(int dishNumber) {
        this.dishNumber = dishNumber;
    }

    public boolean isReady() {
        return this.ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }   
    
}
