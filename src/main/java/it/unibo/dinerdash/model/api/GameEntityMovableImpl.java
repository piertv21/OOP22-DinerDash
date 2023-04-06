package it.unibo.dinerdash.model.api;

import java.util.Optional;

import it.unibo.dinerdash.utility.Direction;
import it.unibo.dinerdash.utility.Pair;

public class GameEntityMovableImpl extends GameEntityImpl implements GameEntityMovable {

    private Optional<Pair<Integer, Integer>> destination;
    private int speed;

    public GameEntityMovableImpl(Pair<Integer, Integer> coordinates, int speed) {
        super(coordinates);
        this.setMovementSpeed(speed);
    }
    
    @Override
    public Optional<Pair<Integer, Integer>> getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(Optional<Pair<Integer, Integer>> destination) {
        this.destination = destination;
    }

    @Override
    public void setMovementSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void moveUp() {
        var oldPosition = this.getPosition();
        var newPosition = new Pair<>(oldPosition.getX() + Direction.UP.getX() * this.speed, oldPosition.getY() + Direction.UP.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveDown() {
        var oldPosition = this.getPosition();
        var newPosition = new Pair<>(oldPosition.getX() + Direction.DOWN.getX() * this.speed, oldPosition.getY() + Direction.DOWN.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveRight() {
        var oldPosition = this.getPosition();
        var newPosition = new Pair<>(oldPosition.getX() + Direction.RIGHT.getX() * this.speed, oldPosition.getY() + Direction.RIGHT.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveLeft() {
        var oldPosition = this.getPosition();
        var newPosition = new Pair<>(oldPosition.getX() + Direction.LEFT.getX() * this.speed, oldPosition.getY() + Direction.LEFT.getY() * this.speed);
        this.setPosition(newPosition);
    }
    
}
