package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Pair;

public interface GameEntityMovable extends GameEntity {

    Optional<Pair<Integer, Integer>> getDestination();

    void setDestination(Optional<Pair<Integer, Integer>> destination);

    int getMovementSpeed();

    void setMovementSpeed(int speed);

    void handleMovement(int range);

}
