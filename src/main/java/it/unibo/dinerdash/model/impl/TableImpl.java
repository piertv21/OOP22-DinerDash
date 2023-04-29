package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

public class TableImpl extends AbstractGameEntity implements Table {

    private final int mintimeforeating = 4;
    private final int maxtimeforeating = 6;
    private Optional<Long> timeFinishEating;

    private int tableNumber;
    private Optional<Customer> customer;
    private TableState state;
    private int seatedPeople;

    public TableImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, final int i) {
        super(coordinates, size);
        this.tableNumber = i;
        this.customer = Optional.empty();
        this.timeFinishEating = Optional.empty();
        this.state = TableState.EMPTY;
    }

    @Override
    public void setState(final TableState tableState) {
        this.state = tableState;
    }

    @Override
    public TableState getState() {
        return this.state;
    }

    @Override
    public void setCustom(final Optional<Customer> cs) {
        this.customer = cs;
    }

    @Override
    public Optional<Customer> getCustomer() {
        return this.customer;
    }

    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    @Override
    public int setseatedPeople(int sppl) {
        return this.seatedPeople = sppl;
    }

    @Override
    public int getPeopleSeatedNumber() {
        return this.seatedPeople;
    }

    @Override
    public void startEating() {
        var currentTime = System.nanoTime();
        var eatingTime = (int) (Math.random() * (maxtimeforeating - mintimeforeating + 1)) + mintimeforeating;
        this.timeFinishEating = Optional.of(currentTime + TimeUnit.SECONDS.toNanos(eatingTime));
    }

    @Override
    public void update() {
        if (this.timeFinishEating.isPresent()) {
            if (System.nanoTime() >= this.timeFinishEating.get()) {
                state = TableState.WANTING_TO_PAY;
                this.timeFinishEating = Optional.empty();
            }
        }
    }

    @Override
    public String getStateInText() {
        return switch (this.state) {
            case ORDERING -> "wantToOrder";
            case WANTING_TO_PAY -> "wantToPay";
            case EATING -> "eating";
            default -> "";
        };
    }

}