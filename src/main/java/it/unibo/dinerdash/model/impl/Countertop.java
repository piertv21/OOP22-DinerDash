package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.Pair;

public class Countertop extends GameEntityImpl {

    private static final int MAX_COUNTERTOP_DISHES = 4;

    private LinkedList<Dish> dishes;    // piatti

    public Countertop(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size) {
        super(coordinates, size);
        this.dishes = new LinkedList<>();
    }

    public void addDish(Dish dish) {
        if(this.dishes.size() < MAX_COUNTERTOP_DISHES) {
            this.dishes.add(dish);
        }
        //TODO Check se serve altro
    }

    /*public <Optional<Dish>> takeDish() {
        //TODO
    }*/

    public void clear() {
        this.dishes.clear();
    }

    public boolean thereAreAvailableDishes() {
        //TODO
        return false;
    }

    public Dish getDishInOrder() {
        //TODO
        return null;
    }

    public void setDishReady(Dish dish) {
        //TODO
    }

}