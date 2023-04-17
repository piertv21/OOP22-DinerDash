package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.Model;
import it.unibo.dinerdash.model.api.TableState;
import it.unibo.dinerdash.model.api.GameEntities.AbstractGameEntityMovable;
import it.unibo.dinerdash.model.api.GameEntities.Customer;
import it.unibo.dinerdash.model.api.States.CustomerState;
import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant.
 */
public class CustomerImpl extends AbstractGameEntityMovable implements Customer  {

    private static final int TIME_BEFORE_GETANGRY = 10;
    private static final int TIME_BEFORE_ORDERING = 4;
    private static final int SPEED = 5;
    private CustomerState state;
    private final Model model;
    private final int numClienti;
    private long startThinkTime;
    private Optional<Long> startAngryTime;
    public CustomerImpl(final Pair<Integer, Integer> coordinates, final Pair<Integer, Integer> size, 
    final Model model, final int numCust) {
        super(coordinates, size, SPEED);
        this.model = model;
        this.state = CustomerState.WALKING;
        this.numClienti = numCust;
        this.startAngryTime = Optional.empty();
        this.setActive(true);
    }

    @Override
    public int getCustomerCount() {
        return this.numClienti;
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
    public void update() {                 //manage the movement of customers     
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
            && getPosition().getY() >= this.getDestination().get().getY() - 4) { //create hitbox
                this.startThinkTime = System.nanoTime();                                           
                state = CustomerState.THINKING;
                this.setPosition(this.getDestination().get());
                this.setActive(false);  //cliente think,and become invisible
                final int sittedTable = this.model
                .getTablefromPositon(getPosition()).getTableNumber();
                this.model.setTableCustomers(numClienti, sittedTable);         
                this.model.setTableState(TableState.THINKING, sittedTable);      
            }
        } else if (state.equals(CustomerState.THINKING)) {      //il cliente pensa a cosa ordinare
            if (System.nanoTime() >= TimeUnit.SECONDS.toNanos(TIME_BEFORE_ORDERING) + this.startThinkTime) {
                this.model.setNeedUpdate(true);
                state = CustomerState.ORDERING;
                final int sittedTable = this.model.getTablefromPositon(getPosition()).getTableNumber();
                this.model.setTableState(TableState.ORDERING, sittedTable);   
            }
        } else if (state.equals(CustomerState.LINE)) {
            if (this.startAngryTime.isPresent()) {
                if (model.checkFreeTables(this)) {
                    // vado a sedermi al tavolo
                    this.model.setNeedUpdate(true);
                    this.model.tableAssignament(this);
                    this.state = CustomerState.WALKING;
                } else if (System.nanoTime() 
                >= TimeUnit.SECONDS.toNanos(TIME_BEFORE_GETANGRY) + this.startAngryTime.get()) {  //client get angry
                    this.state = CustomerState.ANGRY;
                }
            } else { 
                this.startAngryTime = Optional.of(System.nanoTime()); 
            }
        }
    }
    }
