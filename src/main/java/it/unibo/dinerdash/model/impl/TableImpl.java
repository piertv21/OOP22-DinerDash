package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.gameentities.Table;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * 
 * 
 */
public class TableImpl extends AbstractGameEntity implements Table {

    private static final int MIN_TIME_FOR_EATING = 4;
    private static final int MAX_TIME_FOR_EATING = 6;
    private Optional<Long> timeFinishEating;

    private final int tableNumber;
    private Optional<Customer> customer;
    private TableState state;
    private int seatedPeople;

    /**
     * @param coordinates
     * @param size
     * @param i
     */
    public TableImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final int i) {
        super(coordinates, size);
        this.tableNumber = i;
        this.customer = Optional.empty();
        this.timeFinishEating = Optional.empty();
        this.state = TableState.EMPTY;
    }

    /**
    * 
    * 
    */
    @Override
    public void setState(final TableState tableState) {
        this.state = tableState;
    }

    /**
    * 
    * 
    */
    @Override
    public TableState getState() {
        return this.state;
    }

    /**
    * 
    * 
    */
    @Override
    public void setCustom(final Optional<Customer> cs) {
        this.customer = cs;
    }

    /**
    * 
    * 
    */
    @Override
    public Optional<Customer> getCustomer() {
        return this.customer;
    }

    /**
    * 
    * 
    */
    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    /**
    * 
    * 
    */
    @Override
    public void setSeatedPeople(final int peopleAreSeated) {
        this.seatedPeople = peopleAreSeated;
    }

    /**
    * 
    * 
    */
    @Override
    public int getPeopleSeatedNumber() {
        return this.seatedPeople;
    }

    /**
    * 
    * 
    */
    @Override
    public void startEating() {
        final var currentTime = System.nanoTime();
        final var eatingTime = (int) (Math.random() * (MAX_TIME_FOR_EATING - MIN_TIME_FOR_EATING + 1)) + MIN_TIME_FOR_EATING;
        this.timeFinishEating = Optional.of(currentTime + TimeUnit.SECONDS.toNanos(eatingTime));
    }

    /**
    * 
    * 
    */
    @Override
    public void update() {
        if (this.timeFinishEating.isPresent() && System.nanoTime() >= this.timeFinishEating.get()) {
                state = TableState.WANTING_TO_PAY;
                this.timeFinishEating = Optional.empty();
        }
    }

    /**
    * 
    * 
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
    * 
    * 
    */
    @Override
    public Optional<Long> getTimeFinishEating() {
        return this.timeFinishEating;
    }

}