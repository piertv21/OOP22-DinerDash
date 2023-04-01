package it.unibo.dinerdash.model.api;

import it.unibo.dinerdash.utility.Direction;
import it.unibo.dinerdash.utility.Pair;

public class GameEntityMovableImpl extends GameEntityImpl implements GameEntityMovable {

    private int speed;

    public GameEntityMovableImpl(Pair<Integer, Integer> coordinates, int speed) {
        super(coordinates);
        this.setMovementSpeed(speed);
    }

    @Override
    public void setMovementSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void moveUp() {
        var newPosition = new Pair<>(Direction.UP.getX() * this.speed, Direction.UP.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveDown() {
        var newPosition = new Pair<>(Direction.DOWN.getX() * this.speed, Direction.DOWN.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveRight() {
        var newPosition = new Pair<>(Direction.RIGHT.getX() * this.speed, Direction.RIGHT.getY() * this.speed);
        this.setPosition(newPosition);
    }

    @Override
    public void moveLeft() {
        var newPosition = new Pair<>(Direction.LEFT.getX() * this.speed, Direction.LEFT.getY() * this.speed);
        this.setPosition(newPosition);
    }
    
}
