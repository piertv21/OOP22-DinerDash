package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread
 */
public class TableImpl extends AbstractGameEntity implements Table {

    private int tableNumber;
    private Optional<Customer> customer;
    private TableState state;
    private int SeatedPeople;

    private final int TIME_FOR_EATING = 4;
    private long startEatingTime;

    public TableImpl(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, int i) {
        super(coordinates, size);
        this.tableNumber = i;
        this.SeatedPeople = 0;
        this.customer = Optional.empty();
        this.state = TableState.EMPTY;
    }

    @Override
    public void setState(TableState TableState) {
        this.state = TableState;
    }

    @Override
    public TableState getState() {
        return this.state;
    }

    @Override
    public void setCustom(Optional<Customer> cs) {
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
    public int setSeatedPeople(int sppl) {
        return this.SeatedPeople = sppl;
    }

    @Override
    public int getPeopleSeatedNumber() {
        return this.SeatedPeople;
    }

    @Override
    public void startEating() {
        this.startEatingTime = System.nanoTime();
    }

    @Override
    public void update() {
        if (System.nanoTime() >= TimeUnit.SECONDS.toNanos(TIME_FOR_EATING) + this.startEatingTime) {
            state = TableState.WANTING_TO_PAY;
        }
    }

    @Override
    public String getStateInText() {
        switch (this.state) {
            case ORDERING:
                return "wantToOrder";
            case WANTING_TO_PAY:
                return "wantToPay";
            case EATING:
                return "eating";
            default:
                return "";
        }
    }

}