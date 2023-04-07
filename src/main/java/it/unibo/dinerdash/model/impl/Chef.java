package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Chef
 */
public class Chef extends GameEntityImpl {
    
    private static final int MIN_PREPARATION_TIME = 3 * 1000;
    private static final int MAX_PREPARATION_TIME = 10 * 1000;
    private Optional<Dish> currentDish;
    private long timeStarted;
    private ModelImpl model;

    public Chef(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, ModelImpl model) {
        super(coordinates, size);
        this.setActive(false);
        this.currentDish = Optional.empty();
        this.timeStarted = 0;
        this.model = model;        
    }

    public void update(long elapsedUpdateTime) {
        //TODO
    }

    public void prepareDish() {
        //TODO
    }
    
}
