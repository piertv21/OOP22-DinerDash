package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Dish used by chef for orders handling
 */
public class DishImpl extends AbstractGameEntity implements Dish {

    private int dishNumber; // = numeroTavolo

    public DishImpl(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int dishNumber) {
        super(coordinates, size);
        this.dishNumber = dishNumber;
        this.setActive(false);
    }

    @Override
    public int getDishNumber() {
        return this.dishNumber;
    }
    
    @Override
    public Pair<Integer, Integer> getPosition() {
        return super.getPosition();
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }
    
}
