package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Chef
 */
public abstract class Chef extends GameEntityImpl implements Runnable {
    
    private static final int MIN_PREPARATION_TIME = 3 * 1000;
    private static final int MAX_PREPARATION_TIME = 10 * 1000;
    private ModelImpl model;

    public Chef(Pair<Integer, Integer> coordinates, ModelImpl model) {
        super(coordinates);
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
