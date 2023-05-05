package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Countertop {

    void addOrder(int tableNumber);

    Optional<Dish> takeDish(Pair<Integer, Integer> coordinates);

    void clear();

    boolean thereAreDishesToPrepare();

    Optional<Dish> getNextDishToPrepare();

    void setDishReady(Dish dish);

}
