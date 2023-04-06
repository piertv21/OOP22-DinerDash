package it.unibo.dinerdash.model.api;

import it.unibo.dinerdash.utility.Pair;

public interface GameEntity {

    Pair<Integer, Integer> getPosition();

    void setPosition(Pair<Integer, Integer> position);

    Pair<Integer, Integer> getSize();

    void setSize(Pair<Integer, Integer> size);

    boolean isActive();

    void setActive(boolean active);
    
}
