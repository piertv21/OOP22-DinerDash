package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import it.unibo.dinerdash.model.api.AbstractGameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

public class Countertop{

    private static final int MAX_COUNTERTOP_DISHES = 4;

    private LinkedList<Dish> dishes;

    public Countertop() {
        this.dishes = new LinkedList<>();
    }

    public void addDish(Dish dish) {
        if(this.dishes.size() < MAX_COUNTERTOP_DISHES) {
            //TODO assegna coord piatto sul bancone

            // e lo aggiunge
            this.dishes.add(dish);
        }
    }

    /*public <Optional<Dish>> takeDish() {
        //TODO da un piatto alla cameriera
    }*/

    public void clear() {
        this.dishes.clear();
    }

    public boolean thereAreAvailableDishes() {
        //TODO dice se ci son piatti ancora con active = false
        return false;
    }

    public Dish getDishInOrder() {
        //TODO restituisce il primo piatto non active allo chef senza rimuoverlo
        return null;
    }

    public void setDishReady(Dish dish) {
        //TODO Dato un dish lo imposta a ready, chiamata dallo Chef
    }

}