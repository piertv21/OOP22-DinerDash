package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the Dish interface.
 */
public class DishImpl extends AbstractGameEntity implements Dish {

    private final int dishNumber;
    /**
     * Costructor of a new DishImpl object.
     * @param coordinates coordinates of the dish on the countertop.
     * @param size of the dish.
     * @param dishNumber number of the dish.
     */
    public DishImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final int dishNumber) {
        super(coordinates, size);
        this.dishNumber = dishNumber;
        this.setActive(false);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getDishNumber() {
        return this.dishNumber;
    }

}
