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
    /*
     * seconds used to decrease a customer's patience.
     */
    private static final int TIME_BEFORE_LOOSEPATIENCE = 4;
    private static final int MAX_PATIECE = 7;
    private static final int SPEED = 5;
    private static final int HITBOX_SPACE = 4;
    private CustomerState state;
    private final Model model;
    private final int numberClients;
    private long startThinkTime;
    /**
     * The last time when a customer reduced his
     * patience level.
     */
    private Optional<Long> lastPatienceReduce;
    private int patience;
    /*
     * random Time in seconds before make a order.
     */
    private final int timeBeforeOrder;

    /**
     * Create a new Customer.
     * 
     * @param coordinates starting customer's position
     * @param size customer's image size (height, width)
     * @param model reference to modeImpl
     * @param numCusters customer's moltiplicity 
     */
    public CustomerImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size,
            final Model model, final int numCusters) {
        super(coordinates, size, SPEED);
        this.model = model;
        this.state = CustomerState.WALKING;
        this.numberClients = numCusters;
        this.lastPatienceReduce = Optional.empty();
        this.patience = MAX_PATIECE;
        this.timeBeforeOrder = (int) (Math.random() * (MAX_ORDERING_TIME) + ONE);
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
    public void update() { 
        if (state.equals(CustomerState.WALKING)) {
            this.handleMovement(HITBOX_SPACE);

            if (getPosition().getX() >= this.getDestination().get().getX() - HITBOX_SPACE
                    && getPosition().getY() <= this.getDestination().get().getY() + HITBOX_SPACE
                    && getPosition().getY() >= this.getDestination().get().getY() - HITBOX_SPACE) { // create hitbox
                this.startThinkTime = System.nanoTime();
                state = CustomerState.THINKING;
                this.setPosition(this.getDestination().get());
                this.setActive(false); // cliente think,and become invisible
                final int sittedTable = this.model.getTablefromPositon(getPosition()).getTableNumber();
                this.model.setTableState(TableState.THINKING, sittedTable);
                this.model.setNumberOfClientsAtTable(numberClients, sittedTable);
            }
        } else if (state.equals(CustomerState.THINKING)) { // client think what to order
            if (System.nanoTime() >= TimeUnit.SECONDS.toNanos(timeBeforeOrder) + this.startThinkTime) {
                state = CustomerState.ORDERING;
                final int sittedTable = this.model.getTablefromPositon(getPosition()).getTableNumber();
                this.model.setTableState(TableState.ORDERING, sittedTable);
            }
            // actions executed only by Customers in line
        } else if (state.equals(CustomerState.LINE)) {
            if (this.lastPatienceReduce.isPresent()) {
                if (model.checkFreeTables(this)) {
                    // go to sit at table
                    this.model.tableAssignament(this);
                    this.patience = -ONE;
                    this.state = CustomerState.WALKING;
                } else if (this.patience == ZERO) { // client get angry
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

    @Override
    public int getCustomerPatience() {
        return this.patience;
    }
}
