package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntityMovable;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * Create a new element "Customer" who will move in the restaurant.
 */
public class CustomerImpl extends AbstractGameEntityMovable implements Customer {

    private static final int TIME_BEFORE_GETANGRY = 10;
    private static final int TIME_BEFORE_ORDERING = 4;
    private static final int TIME_BEFORE_LOOSEPATIENCE = 2;
    private static final int MAX_PATIECE = 6;
    private static final int SPEED = 5;
    private CustomerState state;
    private final Model model;
    private final int numberClients;
    private long startThinkTime;
    private Optional<Long> lastPatienceReduce;
    private int patience;

    public CustomerImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size,
            final Model model, final int numCusters) {
        super(coordinates, size, SPEED);
        this.model = model;
        this.state = CustomerState.WALKING;
        this.numberClients = numCusters;
        this.lastPatienceReduce = Optional.empty();
        this.patience=MAX_PATIECE;
        this.setActive(true);
    }

    @Override
    public int getCustomerCount() {
        return this.numberClients;
    }

    @Override
    public void setState(final CustomerState state) {
        this.state = state;
    }

    @Override
    public CustomerState getState() {
        return this.state;
    }

    @Override
    public void update() { // manage the movement of customers
        if (state.equals(CustomerState.WALKING)) {
            this.model.setNeedUpdate(true);
            if (getPosition().getX() < this.getDestination().get().getX()) {
                this.moveRight();
            } else if (getPosition().getY() > this.getDestination().get().getY()) {
                this.moveUp();
            } else if (getPosition().getY() < this.getDestination().get().getY()) {
                this.moveDown();
            }
            if (getPosition().getX() >= this.getDestination().get().getX()
                    && getPosition().getY() <= this.getDestination().get().getY() + 4
                    && getPosition().getY() >= this.getDestination().get().getY() - 4) { // create hitbox
                this.startThinkTime = System.nanoTime();
                state = CustomerState.THINKING;
                this.setPosition(this.getDestination().get());
                this.setActive(false); // cliente think,and become invisible
                final int sittedTable = this.model
                        .getTablefromPositon(getPosition()).getTableNumber();
                this.model.setTableState(TableState.THINKING, sittedTable);
                this.model.setNumberOfClientsAtTable(numberClients, sittedTable);
            }
        } else if (state.equals(CustomerState.THINKING)) { // client think what to order
            if (System.nanoTime() >= TimeUnit.SECONDS.toNanos(TIME_BEFORE_ORDERING) + this.startThinkTime) {
                this.model.setNeedUpdate(true);
                state = CustomerState.ORDERING;
                final int sittedTable = this.model.getTablefromPositon(getPosition()).getTableNumber();
                this.model.setTableState(TableState.ORDERING, sittedTable);
            }
        } else if (state.equals(CustomerState.LINE)) {
            if (this.lastPatienceReduce.isPresent()) {
                if (model.checkFreeTables(this)) {
                    // go to sit at table
                    this.model.tableAssignament(this);
                    this.model.setNeedUpdate(true);
                    this.state = CustomerState.WALKING;
                } else if (this.patience==0) { // client get angry
                    this.state = CustomerState.ANGRY;
                    this.model.setNeedUpdate(true);
                } else if (System.nanoTime() >= TimeUnit.SECONDS.toNanos(TIME_BEFORE_LOOSEPATIENCE)
                + this.lastPatienceReduce.get()) {
                    this.lastPatienceReduce = Optional.of(System.nanoTime());
                    this.patience-- ;
                    this.model.setNeedUpdate(true);
                }
            } else {
                this.lastPatienceReduce = Optional.of(System.nanoTime());
            }
        }
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return super.getPosition();
    }

    @Override
    public void setDestination(final Optional<Pair<Integer, Integer>> destination) {
        super.setDestination(destination);
    }

    @Override
    public Optional<Pair<Integer, Integer>> getDestination() {
        return super.getDestination();
    }

    @Override
    public void setPosition(Pair<Integer, Integer> position) {
        super.setPosition(position);
    }
    
}
