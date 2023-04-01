package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.utility.Pair;

public interface GameEntity {

    Pair<Integer, Integer> getPosition();

    void setPosition(Pair<Integer, Integer> position);

    Optional<Pair<Integer, Integer>> getDestination();

    void setDestination(Optional<Pair<Integer, Integer>> destination);

    boolean isActive();

    void setActive(boolean active);
    
}
