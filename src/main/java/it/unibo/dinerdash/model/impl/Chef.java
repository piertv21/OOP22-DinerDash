package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityMovableImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Chef
 */
public abstract class Chef extends GameEntityMovableImpl implements Runnable {
    
    private static final int CHEF_SPEED = 1;
    private static final int MIN_PREPARATION_TIME = 3000;
    private static final int MAX_PREPARATION_TIME = 10000;
    private ModelImpl model;

    public Chef(Pair<Integer, Integer> coordinates, ModelImpl model) {
        super(coordinates, CHEF_SPEED);
        this.setActive(false);
        this.model = model;
    }

    public void prepareDish() {
        //TODO
    }

    public void run() {
        //TODO
    }
    
}
