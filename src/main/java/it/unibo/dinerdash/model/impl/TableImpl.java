package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * Create a new element "Table" for people to sit or whait for it to clear.
 */
public class TableImpl extends AbstractGameEntity implements Table {

    private static final int MIN_TIME_FOR_EATING = 4;
    private static final int MAX_TIME_FOR_EATING = 6;
    private Optional<Long> timeFinishEating;

    private final int tableNumber;
    private Optional<Customer> customer;
    private TableState state;
    private int seatedPeople;
    private final Random random = new Random();

    /**
     * Create a new Table.
     * 
     * @param coordinates starting table's position
     * @param size        table's image size (height, width)
     * @param i           table's number for order
     */
    public TableImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final int i) {
        super(coordinates, size);
        this.tableNumber = i;
        this.customer = Optional.empty();
        this.timeFinishEating = Optional.empty();
        this.state = TableState.EMPTY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(final TableState tableState) {
        this.state = tableState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableState getState() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCustom(final Optional<Customer> cs) {
        this.customer = cs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Customer> getCustomer() {
        return this.customer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSeatedPeople(final int peopleAreSeated) {
        this.seatedPeople = peopleAreSeated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPeopleSeatedNumber() {
        return this.seatedPeople;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startEating() {
        final var currentTime = System.nanoTime();
        final var eatingTime = random
                .nextInt(MAX_TIME_FOR_EATING - MIN_TIME_FOR_EATING + 1) + MIN_TIME_FOR_EATING;
        this.timeFinishEating = Optional.of(currentTime + TimeUnit.SECONDS.toNanos(eatingTime));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (this.timeFinishEating.isPresent() && System.nanoTime() >= this.timeFinishEating.get()) {
            state = TableState.WANTING_TO_PAY;
            this.timeFinishEating = Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStateInText() {
        return switch (this.state) {
            case ORDERING -> "wantToOrder";
            case WANTING_TO_PAY -> "wantToPay";
            case EATING -> "eating";
            default -> "";
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Long> getTimeFinishEating() {
        return this.timeFinishEating;
    }

}
