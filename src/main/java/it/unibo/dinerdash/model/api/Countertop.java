package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntities.Dish;

public interface Countertop {

    void addOrder(int tableNumber);

    Optional<Dish> takeDish(int x, int y);

    void clear();

    boolean thereAreAvailableDishes();

    Optional<Dish> getDishInOrder();

}
