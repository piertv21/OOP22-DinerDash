package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.dinerdash.utility.impl.Pair;

public class Countertop {

    private static final double START_DISH_REL_X = 0.5;
    private static final double START_DISH_REL_Y = 0.2;
    private static final int DISHES_X_PADDING = 150;
    private static final int DISH_REL_WIDTH = 70;
    private static final int DISH_REL_HEIGHT = 50;
    private static final int MAX_COUNTERTOP_DISHES = 4;

    private LinkedList<Dish> dishes;
    private ModelImpl model;

    public Countertop(ModelImpl model) {
        this.model = model;
        this.dishes = new LinkedList<>();
    }

    public void addOrder(int tableNumber) {
        if(this.dishes.size() < MAX_COUNTERTOP_DISHES) {

            var coordX = this.getFirstFreeX();
            var coordY = (int)(START_DISH_REL_Y * this.model.getHeight());
            var dishPosition = new Pair<>(coordX, coordY);
            var dishSize = new Pair<>(DISH_REL_WIDTH, DISH_REL_HEIGHT);
            var dish = new Dish(dishPosition, dishSize, tableNumber);

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
    
    public Optional<Dish> takeDish(int x, int y) {
        Optional<Dish> dishToRemove = this.dishes.stream()
                .filter(dish -> dish.getPosition().getX() == x && dish.getPosition().getY() == y)
                .findFirst()
                .map(dish -> {
                    dishes.remove(dish);
                    return dish;
                });
        return dishToRemove;
    }

    public void clear() {
        this.dishes.clear();
    }

    //dice se ci son piatti ancora con active = false
    public boolean thereAreAvailableDishes() {
        return this.dishes.stream().anyMatch(e -> !e.isActive());
    }

    // prossimo dish da preparare (active = false)
    public Optional<Dish> getDishInOrder() {
        return this.dishes.stream()
            .filter(dish -> !dish.isActive())
            .findFirst();
    }

    // Dato un dish lo imposta a ready, chiamata dallo Chef
    public void setDishReady(Dish dish) {
        this.dishes.stream()
            .filter(d -> d.equals(dish))
            .findFirst()
            .ifPresent(d -> d.setActive(true));
    }

}