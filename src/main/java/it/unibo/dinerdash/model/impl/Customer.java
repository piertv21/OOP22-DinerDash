package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.api.TableState;
import it.unibo.dinerdash.model.api.AbstractGameEntityMovable;
import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends AbstractGameEntityMovable  {

    private static final int MOVEMENT_DISTANCE = 5;
    private static final int TIME_BEFORE_GETANGRY = 16;
    private static final int TIME_BEFORE_ORDERING = 4;
    private static final int SPEED = 1;
    private CustomerState state;
    private ModelImpl model;
    private int numClienti;
    private long startThinkTime;
    private Optional<Long> startAngryTime;
    private Pair<Integer, Integer> size;
    
    public Customer(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, ModelImpl model,int numCust) {
        super(coordinates, size, SPEED);
        this.model = model;
        this.state = CustomerState.WALKING;
        this.numClienti = numCust;
        this.size = size;
        this.startAngryTime = Optional.empty();
    }

    public int getCustomerCount() {
        return this.numClienti;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }

    public CustomerState getState() {
        return this.state ;
    }

    public void update(long elapsedUpdateTime) {                 //manage the movement of customers     
        if(state.equals(CustomerState.WALKING)) {
            if(getPosition().getX() < this.getDestination().get().getX()) this.moveRight(); 
            else if(getPosition().getY() > this.getDestination().get().getY()) this.moveUp();
            else if(getPosition().getY() < this.getDestination().get().getY()) this.moveDown();
            if((getPosition().getX() >= this.getDestination().get().getX()) &&
            ((getPosition().getY() <= this.getDestination().get().getY() + 4) &&
            (getPosition().getY() >= this.getDestination().get().getY() - 4)))     //creo una hitbox del tavolo
            {                          
                this.startThinkTime = System.nanoTime();                                                 
                    state = CustomerState.THINKING;
                    this.setPosition(this.getDestination().get());
                    this.setActive(false);                                              //cliente pensa, quindi la sua immagine deve sparire       
            }     
        }
        else if(state.equals(CustomerState.THINKING))                                          //il cliente pensa a cosa ordinare
        {
            int sittedTable=this.model.getTablefromPositon(getPosition()).getTableNumber();
            //this.model.setTableCustomers(numClienti, sittedTable);         decommenta
             // this.model.setTableState(TableState.THINKING, sittedTable);   decoomenta 
            if(System.nanoTime() >= TimeUnit.SECONDS.toNanos(TIME_BEFORE_ORDERING) + this.startThinkTime) {
                state = CustomerState.ORDERING;
            } 
        }
        else if(state.equals(CustomerState.LINE)) {
            if(this.startAngryTime.isPresent()) {
                if(model.checkFreeTables(this)) {
                    // vado a sedermi al tavolo
                    this.state=CustomerState.WALKING;
                    this.model.AssegnoTavolo(this);
                }
                else if(System.nanoTime() >= TimeUnit.SECONDS.toNanos(TIME_BEFORE_GETANGRY) + this.startAngryTime.get()) {         //il cliente si arrabbia e se ne va
                    this.state=CustomerState.ANGRY;
                    model.leaveRestaurant(this);
                }
            }else { this.startAngryTime = Optional.of(System.nanoTime()); }
        }
    }

    
    }
    

