package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.gameentities.AbstractGameEntityMovable;
import it.unibo.dinerdash.model.api.gameentities.Customer;
import it.unibo.dinerdash.model.api.states.CustomerState;
import it.unibo.dinerdash.model.api.states.TableState;
import it.unibo.dinerdash.utility.impl.Pair;

/**
 * Create a new element "Customer" who will move in the restaurant, Or will 
 * wait in Line for a free table.
 */
public final class CustomerImpl extends AbstractGameEntityMovable implements Customer {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int MAX_ORDERING_TIME = 4;
    private static final int TIME_BEFORE_LOOSEPATIENCE = 4;
    private static final int MAX_PATIECE = 7;
    private static final int SPEED = 5;
    private static final int HITBOX_SPACE = 4;
    private CustomerState state;
    private final Optional<Model> model;
    private final int numberofClients;
    private long startThinkTime;
    private Optional<Long> lastPatienceReduce;
    private int patience;
    private final int timeBeforeOrder;

    /**
     * Create a new Customer.
     * 
     * @param coordinates starting customer's position
     * @param size customer's image size (height, width)
     * @param model reference to modeImpl
     * @param numberOfClients customer's moltiplicity 
     */
    public CustomerImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size,
            final Optional<Model> model, final int numberOfClients) {
        super(coordinates, size, SPEED);
        this.model = model;
        this.state = CustomerState.WALKING;
        this.numberofClients = numberOfClients;
        this.lastPatienceReduce = Optional.empty();
        this.patience = MAX_PATIECE;
        this.timeBeforeOrder = (int) (Math.random() * (MAX_ORDERING_TIME) + ONE);
        this.setActive(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCustomerCount() {
        return this.numberofClients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(final CustomerState state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerState getState() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() { 
        switch (this.state) {
            case WALKING: this.walkingAction(); break;
            case THINKING: this.thinkingAction(); break;
            case LINE: this.lineAction(); break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCustomerPatience() {
        return this.patience;
    }

    private void walkingAction() {
        this.handleMovement(HITBOX_SPACE);

        if (this.isArrived(HITBOX_SPACE)) { 
            this.startThinkTime = System.nanoTime();
            this.state = CustomerState.THINKING;
            this.setPosition(this.getDestination().get());
            this.setActive(false); 
            final int sittedTable = this.model.get().getTableNumberfromPosition(this.getPosition());
            this.model.get().setTableState(TableState.THINKING, sittedTable);
            this.model.get().setNumberOfClientsAtTable(numberofClients, sittedTable);
        }
    }

    private void thinkingAction() {
        if (System.nanoTime() >= TimeUnit.SECONDS.toNanos(this.timeBeforeOrder) + this.startThinkTime) {
            this.state = CustomerState.ORDERING;
            final int sittedTable = this.model.get().getTableNumberfromPosition(this.getPosition());
            this.model.get().setTableState(TableState.ORDERING, sittedTable);
        }
    }

    private void lineAction() {
        if (this.lastPatienceReduce.isPresent()) {
            if (model.get().checkFreeTables(this)) {
                this.model.get().tableAssignament(this);
                this.patience = -ONE;
                this.state = CustomerState.WALKING;
            } else if (this.patience == ZERO) { 
                this.state = CustomerState.ANGRY;
            } else if (System.nanoTime() >= TimeUnit.SECONDS.toNanos(TIME_BEFORE_LOOSEPATIENCE)
            + this.lastPatienceReduce.get()) {
                this.lastPatienceReduce = Optional.of(System.nanoTime());
                this.patience--;
            }
        } else {
            this.lastPatienceReduce = Optional.of(System.nanoTime());
            this.patience--;
        }
    }
}
