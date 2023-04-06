package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.utility.Pair;

public interface GameEntityMovable extends GameEntity {

    Optional<Pair<Integer, Integer>> getDestination();

    void setDestination(Optional<Pair<Integer, Integer>> destination);

    void setMovementSpeed(int speed);

    void moveUp();

    void moveDown();

    void moveRight();

    void moveLeft();

}
