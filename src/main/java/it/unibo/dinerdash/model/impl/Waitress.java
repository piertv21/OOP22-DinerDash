package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityMovableImpl;
import it.unibo.dinerdash.utility.impl.Pair;

public class Waitress extends GameEntityMovableImpl {

    private static int SPEED = 1;

    public Waitress(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size) {
        super(new Pair<Integer,Integer>(550, 148), size, SPEED);
        
    }

    public void handleMovement() {
       
    }
    
}
