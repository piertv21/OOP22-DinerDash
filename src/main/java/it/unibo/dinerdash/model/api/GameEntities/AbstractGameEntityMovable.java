package it.unibo.dinerdash.model.api.GameEntities;

import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Direction;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameEntityMovable.
 */
public class AbstractGameEntityMovable extends AbstractGameEntity implements GameEntityMovable {

    private Optional<Pair<Integer, Integer>> destination;
    private int speed;
    
    /**
     * {@inheritDoc}
     * 
     * Class constructor.
     * 
     * @param speed is the entity movement speed in the restaurant
     */
    public AbstractGameEntityMovable(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final int speed) {
        super(coordinates, size);
        this.destination = Optional.empty();
        this.speed = speed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getDestination() {
        return this.destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDestination(final Optional<Pair<Integer, Integer>> destination) {
        this.destination = destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMovementSpeed() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMovementSpeed(final int speed) {
        this.speed = speed;
    }
    
    private void moveUp() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.UP.getX() * this.speed, oldPosition.getY() + Direction.UP.getY() * this.speed);
        this.setPosition(newPosition);
    }
    
    private void moveDown() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.DOWN.getX() * this.speed, oldPosition.getY() + Direction.DOWN.getY() * this.speed);
        this.setPosition(newPosition);
    }
    
    private void moveRight() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.RIGHT.getX() * this.speed, oldPosition.getY() + Direction.RIGHT.getY() * this.speed);
        this.setPosition(newPosition);
    }
    
    private void moveLeft() {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(oldPosition.getX() + Direction.LEFT.getX() * this.speed, oldPosition.getY() + Direction.LEFT.getY() * this.speed);
        this.setPosition(newPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleMovement(final int range) {
        if (getPosition().getX() < this.getDestination().get().getX() - range) {
            this.moveRight();
        } else if (getPosition().getX() > this.getDestination().get().getX() + range) {
            this.moveLeft();
        } else if (getPosition().getY() > this.getDestination().get().getY() + range) {
            this.moveUp();
        } else if (getPosition().getY() < this.getDestination().get().getY()) {
            this.moveDown();
        }
    }
    
}
