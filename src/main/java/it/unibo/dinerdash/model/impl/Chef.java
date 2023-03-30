package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Chef
 */
public class Chef extends GameEntityImpl {
    
    private static final int MIN_PREPARATION_TIME = 3000;
    private static final int MAX_PREPARATION_TIME = 10000;

    private float speed;
    private ModelImpl model;

    public Chef(Pair<Integer, Integer> coordinates) {
        super(coordinates);
        this.setActive(false);

        var position = new Pair<>(100, 20);
        this.setPosition(position);
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void prepareDish() {
        //TO-DO
    }
    
}
