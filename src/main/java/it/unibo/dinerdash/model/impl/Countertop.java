package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import it.unibo.dinerdash.model.api.AbstractGameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public class Countertop{

    private static final int MAX_COUNTERTOP_DISHES = 4;

    private LinkedList<Dish> dishes;    // piatti

    public Countertop() {
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