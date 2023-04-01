package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityMovableImpl;
import it.unibo.dinerdash.utility.Pair;

/*
 * Chef
 */
public abstract class Chef extends GameEntityMovableImpl {
    
    private static final int CHEF_SPEED = 1;
    private static final int MIN_PREPARATION_TIME = 3000;
    private static final int MAX_PREPARATION_TIME = 10000;
    private ModelImpl model;

    public Chef(Pair<Integer, Integer> coordinates) {
        super(coordinates, CHEF_SPEED);
        this.setActive(false);

        var position = new Pair<>(100, 20);
        this.setPosition(position);
        //TODO Completa e cambia
    }

    public void prepareDish() {
        //TO-DO
    }
    
}
