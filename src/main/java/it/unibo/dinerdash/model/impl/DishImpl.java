package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.Dish;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Dish used by chef for orders handling
 */
public class DishImpl extends AbstractGameEntity implements Dish {

    private final int dishNumber;

    public DishImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final int dishNumber) {
        super(coordinates, size);
        this.dishNumber = dishNumber;
        this.setActive(false);
    }

    @Override
    public int getDishNumber() {
        return this.dishNumber;
    }
    
}
