package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.utility.impl.Pair;

public class Chef extends GameEntity {

    private float speed;

    public Chef(Pair<Integer, Integer> coordinates) {
        super(coordinates);
        //TODO Auto-generated constructor stub
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }    
    
}
