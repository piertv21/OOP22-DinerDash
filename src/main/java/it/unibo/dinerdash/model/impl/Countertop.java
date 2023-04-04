package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

public class Countertop extends GameEntityImpl {

    private LinkedList<Dish> dishes;    // piatti

    public Countertop(Pair<Integer, Integer> coordinates) {
        super(coordinates);
        //TODO Inizializza
    }

    public void addDish(Dish dish) {
        //TODO
    }

    /*public <Optional<Dish>> takeDish() {
        //TODO

    }*/

    public void clear() {
        //TODO
    }
    
}