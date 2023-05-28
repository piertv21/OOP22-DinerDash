package it.unibo.dinerdash.model.api.gameentities;

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
    public AbstractGameEntityMovable(
        final Pair<Integer, Integer> coordinates,
        final Pair<Integer, Integer> size,
        final int speed
    ) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleMovement(final int range) {
        if (getPosition().getX() < this.getDestination().get().getX() - range) {
            this.move(Direction.RIGHT);
        } else if (getPosition().getX() > this.getDestination().get().getX() + range) {
            this.move(Direction.LEFT);
        } else if (getPosition().getY() > this.getDestination().get().getY() + range) {
            this.move(Direction.UP);
        } else if (getPosition().getY() < this.getDestination().get().getY()) {
            this.move(Direction.DOWN);
        }
    }

    private void move(final Direction direction) {
        final var oldPosition = this.getPosition();
        final var newPosition = new Pair<>(
            oldPosition.getX() + direction.getX() * this.speed,
            oldPosition.getY() + direction.getY() * this.speed
        );
        this.setPosition(newPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isArrived(final int range) {
        return this.getPosition().getX() >= this.getDestination().get().getX() - range
            && this.getPosition().getX() <= this.getDestination().get().getX() + range
            && this.getPosition().getY() >= this.getDestination().get().getY() - range
            && this.getPosition().getY() <= this.getDestination().get().getY() + range;
    }

}
