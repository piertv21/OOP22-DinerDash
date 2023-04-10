package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.api.GameEntityMovableImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends GameEntityMovableImpl  {

    private static final int MOVEMENT_DISTANCE = 5;
    private static final long TIME_BEFORE_GETANGRY = 16;
    private static final long TIME_BEFORE_ORDERING = 4;
    private static final int SPEED = 1;
    private CustomerState state;
    private ModelImpl model;
    private int numClienti;
    private int angryCounter=0;
    private long startThinkTime;
    private long startAngryTime=0;
    
    public Customer(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, ModelImpl model) {
        super(coordinates, size, SPEED);
        this.model = model;
    }

    public int getCustomerMultiplicity() {
        return this.numClienti;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }

    public CustomerState getState() {
        return this.state ;
    }

    public void handleMovement() {                 //manage the movement of customers
        if(state.equals(CustomerState.WALKING)) {
            if(getPosition().getX() < this.getDestination().get().getX()) this.moveRight(); 
            else if(getPosition().getY() > this.getDestination().get().getY()) this.moveUp();
            else if(getPosition().getY() < this.getDestination().get().getY()) this.moveDown();
            if((getPosition().getX()>=this.getDestination().get().getX())&&
            ((getPosition().getY()<=this.getDestination().get().getY()+4)&&
            (getPosition().getY()>=this.getDestination().get().getY()-4)))     //creo una hitbox del tavolo
            {                          
                this.startThinkTime=System.nanoTime();                                                 
                    state = CustomerState.THINKING;
                    this.setPosition(this.getDestination().get());
                    this.setActive(false);                                              //cliente pensa, quindi la sua immagine deve sparire       
            }     
        }
        else if(state.equals(CustomerState.THINKING))                                          //il cliente pensa a cosa ordinare
        {
            if(System.nanoTime()==this.startThinkTime+TIME_BEFORE_ORDERING ) state = CustomerState.ORDERING;
               
        }
        else if(state.equals(CustomerState.LINE)){
            if(this.startAngryTime!=0){
                if(model.checkFreeTables(this)){
                    // vado a sedermi al tavolo
                    this.state=CustomerState.WALKING;
                    this.model.AssegnoTavolo(this);
                    
                }
                if(System.nanoTime()>= this.startAngryTime+TIME_BEFORE_GETANGRY){         //il cliente si arrabbia e se ne va
                    this.state=CustomerState.ANGRY;
                    model.leaveRestaurant(this);
                }
            }else{this.startAngryTime=System.nanoTime();}
        }
    }

    
    }
    

