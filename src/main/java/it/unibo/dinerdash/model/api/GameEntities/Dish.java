package it.unibo.dinerdash.model.api.GameEntities;

import it.unibo.dinerdash.utility.impl.Pair;

public interface Dish {

    int getDishNumber();

    Pair<Integer, Integer> getPosition();

    boolean isActive();

    void setActive(boolean active);
    
}
