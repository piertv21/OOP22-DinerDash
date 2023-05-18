package it.unibo.dinerdash.model.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntityMovable;
import it.unibo.dinerdash.model.api.gameentities.Dish;
import it.unibo.dinerdash.model.api.gameentities.Waitress;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.model.api.states.WaitressState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * Create a new element "Waitress" for take and server customer orders and thake
 * their money.
 */
public class WaitressImpl extends AbstractGameEntityMovable implements Waitress {

    private static final int STARTING_SPEED = 2;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5;
    private static final int ADJUST_POSITION = 60;

    private WaitressState state;
    private final Optional<Model> model;

    private final List<Dish> orderList;

    private int serveTable;

    /**
     * Class constructor.
     * 
     * @param coordinates are the waitress coordinates
     * @param size is the waitress size
     * @param model is the reference to the model
     */
    public WaitressImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size,
            final Optional<Model> model) {
        super(coordinates, size, STARTING_SPEED);
        this.state = WaitressState.WAITING;
        this.orderList = new LinkedList<>();
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (!state.equals(WaitressState.WAITING)) {
            this.handleMovement(4);

            if (getPosition().getX() >= this.getDestination().get().getX() - 4
                    && getPosition().getX() <= this.getDestination().get().getX() + 4
                    && getPosition().getY() <= this.getDestination().get().getY() + 4
                    && getPosition().getY() >= this.getDestination().get().getY() - ADJUST_POSITION) {
                if (state.equals(WaitressState.CALLING)) {
                    state = WaitressState.WAITING;
                    model.get().sendOrder(model.get().getTablefromPosition(getDestination().get()).getTableNumber());
                } else if (state.equals(WaitressState.TAKING_DISH)) {
                    orderList.add(model.get().takeDishFromPosition(getDestination().get()).get());
                    state = WaitressState.WAITING;
                } else if (state.equals(WaitressState.SERVING)) {
                    serveTable = model.get().getTablefromPosition(getDestination().get()).getTableNumber();
                    if (this.checkRightTable(serveTable)) {
                        this.model.get().setTableState(TableState.EATING, serveTable);

                        orderList.remove(orderList.stream()
                                .filter(o -> o.getDishNumber() == serveTable)
                                .findFirst().get());
                    }
                    state = WaitressState.WAITING;
                } else if (state.equals(WaitressState.TAKING_MONEY)) {
                    this.model.get().earnMoneyFromTable();
                    state = WaitressState.WAITING;
                    serveTable = model.get().getTablefromPosition(getDestination().get()).getTableNumber();
                    this.model.get().setTableState(TableState.EMPTY, serveTable);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(final WaitressState state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WaitressState getState() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goGetDish(final Pair<Integer, Integer> dishReady) {
        this.setDestination(Optional.of(dishReady));
        this.state = WaitressState.TAKING_DISH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeTableOrder(final Pair<Integer, Integer> position) {
        this.setDestination(Optional.of(position));
        this.state = WaitressState.CALLING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void serveOrder(final Pair<Integer, Integer> position) {
        this.setDestination(Optional.of(position));
        state = WaitressState.SERVING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collectMoney(final Pair<Integer, Integer> position) {
        this.setDestination(Optional.of(position));
        state = WaitressState.TAKING_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrdersNumber() {
        return this.orderList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Dish> getOrderList() {
        return Collections.unmodifiableList(this.orderList);
    }
    
    private boolean checkRightTable(final int tableNumber) {
        return this.orderList.stream().anyMatch(e -> e.getDishNumber() == tableNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementSpeed() {
        this.setMovementSpeed((int) (this.getMovementSpeed() * WAITRESS_SPEED_MULTIPLIER));
    }

}
