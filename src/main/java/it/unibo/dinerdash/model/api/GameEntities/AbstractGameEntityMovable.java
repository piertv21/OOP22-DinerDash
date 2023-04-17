package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Direction;
import it.unibo.dinerdash.utility.impl.Pair;

public class AbstractGameEntityMovable extends AbstractGameEntity implements GameEntityMovable {

    private Optional<Pair<Integer, Integer>> destination;
    private int speed;

    public AbstractGameEntityMovable(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size,final int speed) {
        super(coordinates, size);
        this.destination = Optional.empty();
        this.setMovementSpeed(speed);
    }
    
    @Override
    public Optional<Pair<Integer, Integer>> getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(final Optional<Pair<Integer, Integer>> destination) {
        this.destination = destination;
    }

    @Override
    public void setMovementSpeed(final int speed) {
        this.speed = speed;
    }

    @Override
    public void moveUp() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.UP.getX() * this.speed, oldPosition.getY() + Direction.UP.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveDown() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.DOWN.getX() * this.speed, oldPosition.getY() + Direction.DOWN.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveRight() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.RIGHT.getX() * this.speed, oldPosition.getY() + Direction.RIGHT.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveLeft() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.LEFT.getX() * this.speed, oldPosition.getY() + Direction.LEFT.getY() * this.speed);
        this.setPosition(newPosition);
    }
    
}
