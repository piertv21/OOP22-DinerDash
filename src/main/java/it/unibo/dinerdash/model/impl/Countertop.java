package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.dinerdash.utility.impl.Pair;

public class Countertop {

    private static final double START_DISH_REL_X = 0.5;
    private static final double START_DISH_REL_Y = 0.2;
    private static final int DISHES_X_PADDING = 150;
    private static final int MAX_COUNTERTOP_DISHES = 4;

    private LinkedList<Dish> dishes;
    private ModelImpl model;

    public Countertop(ModelImpl model) {
        this.model = model;
        this.dishes = new LinkedList<>();
    }

    public void addDish(Dish dish) {
        if(this.dishes.size() < MAX_COUNTERTOP_DISHES) {

            var coordX = this.getFirstFreeX();
            var coordY = (int)(START_DISH_REL_Y * this.model.getHeight());
            var dishPosition = new Pair<>(coordX, coordY);
            dish.setPosition(dishPosition);

            //lo aggiunge
            this.dishes.add(dish);
        }
    }

    private int getFirstFreeX() {
        // Punto iniziale
        int startPoint = (int)(START_DISH_REL_X * this.model.getWidth());
    
        // Creo una lista ordinata delle coordinate X dei piatti presenti nella lista
        List<Integer> xCoordinates = dishes.stream()
                .map(dish -> dish.getPosition().getX())
                .sorted()
                .collect(Collectors.toList());
    
        // Cerco il primo multiplo mancante partendo dal punto iniziale
        return IntStream.iterate(startPoint, x -> x + DISHES_X_PADDING)
                .filter(x -> !xCoordinates.contains(x))
                .findFirst()
                .orElse(startPoint);
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