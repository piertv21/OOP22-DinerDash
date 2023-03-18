package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Chef
 */
public class Chef extends GameEntity {

    private float speed;

    public Chef(Pair<Integer, Integer> coordinates) {
        super(coordinates);
        this.setActive(false);
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }    
    
}
