package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntity;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * 
 * 
 */
public class TableImpl extends AbstractGameEntity implements Table {

    private final int mintimeforeating = 4;
    private final int maxtimeforeating = 6;
    protected Optional<Long> timeFinishEating;

    private int tableNumber;
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

    @Override
    public final void setState(final TableState tableState) {
        this.state = tableState;
    }

    @Override
    public final TableState getState() {
        return this.state;
    }

    @Override
    public final void setCustom(final Optional<Customer> cs) {
        this.customer = cs;
    }

    @Override
    public final Optional<Customer> getCustomer() {
        return this.customer;
    }

    @Override
    public final int getTableNumber() {
        return this.tableNumber;
    }

    @Override
    public final int setseatedPeople(final int peopleAreSeated) {
        this.seatedPeople = peopleAreSeated;
        return this.seatedPeople;
    }

    @Override
    public final int getPeopleSeatedNumber() {
        return this.seatedPeople;
    }

    @Override
    public final void startEating() {
        var currentTime = System.nanoTime();
        var eatingTime = (int) (Math.random() * (maxtimeforeating - mintimeforeating + 1)) + mintimeforeating;
        this.timeFinishEating = Optional.of(currentTime + TimeUnit.SECONDS.toNanos(eatingTime));
    }

    @Override
    public final void update() {
        if (this.timeFinishEating.isPresent()) {
            if (System.nanoTime() >= this.timeFinishEating.get()) {
                state = TableState.WANTING_TO_PAY;
                this.timeFinishEating = Optional.empty();
            }
        }
    }

    @Override
    public final String getStateInText() {
        return switch (this.state) {
            case ORDERING -> "wantToOrder";
            case WANTING_TO_PAY -> "wantToPay";
            case EATING -> "eating";
            default -> {
                if (this.state == null) {
                    yield "null";
                } else {
                    yield "";
                }
            }
        };
    }

}