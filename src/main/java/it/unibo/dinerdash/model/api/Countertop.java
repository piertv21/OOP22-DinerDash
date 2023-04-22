package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.model.api.GameEntities.Dish;

public interface Countertop {

    void addOrder(int tableNumber);

    Optional<Dish> takeDish(int x, int y);

    void clear();

    boolean thereAreDishesToPrepare();

    Optional<Dish> getNextDishToPrepare();

    void setDishReady(Dish dish);

}
