package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.api.GameEntityMovableImpl;
import it.unibo.dinerdash.utility.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends GameEntityMovableImpl {

    private static final int MOVEMENT_DISTANCE = 5;
    private static final int MIN_EAT_TIME = 4000;
    private static final int MAX_EAT_TIME = 10000;
    private static final int TIME_BEFORE_GETANGRY = 16000;
    private static final int TIME_BEFORE_ORDERING = 4000;
    private static final int SPEED = 1;
    
    private Timer timerActions = new Timer();
    private CustomerState state;
    private ModelImpl model;
    private int numClienti;
    
    public Customer(Pair<Integer, Integer> coordinates, ModelImpl model) {
        super(coordinates,SPEED);
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

    public void startAngryTimer() {                                   //avvia il timer per far arrabbiare i clienti in fila
        timerActions.schedule(angryAction, TIME_BEFORE_GETANGRY, TIME_BEFORE_GETANGRY);
    }

     TimerTask angryAction = new TimerTask() {                        //azione programmata per gestire il cliente arrabbiato
        @Override
        public void run() { 
                model.getCustomers().remove(model.getCustomers().stream()
                .filter(p->p.getState().equals(CustomerState.LINE)).findFirst().get());
                           // forse saranno da invertire
                model.getCustomers().stream()
                .filter(p->p.getState().equals(CustomerState.LINE)).forEach((p)->{
                    p.setPosition(new Pair<>(p.getPosition().getX(), p.getPosition().getY()+25));
                });
                angryAction.cancel();          
        }
    }; 

    public void startThinkingTimer() {                                   //avvia il timer per far pensare un clinete seduto
        timerActions.schedule(ThinkingAction, TIME_BEFORE_ORDERING, TIME_BEFORE_ORDERING);
    }

    
    TimerTask ThinkingAction = new TimerTask() {                        //azione programmata per gestire il cliente che pensa
        @Override
        public void run() { 
            state = CustomerState.ORDERING;
            ThinkingAction.cancel();
        }
    };

    public void handleMovement() {                 //manage the movement of customers
        if(state.equals(CustomerState.WALKING)) {
            if(getPosition().getX() < this.getDestination().get().getX())this.right(); 
            else if(getPosition().getY() > this.getDestination().get().getY()){this.up();}
            else if(getPosition().getY() < this.getDestination().get().getY()){this.down();}
            if((getPosition().getX()>=this.getDestination().get().getX())&&((getPosition().getY()<=this.getDestination().get().getY()+4)&&(getPosition().getY()>=this.getDestination().get().getY()-4)))     //creo una hitbox del tavolo
            {                                                                                                   //il cliente raggiunge la sedia e si siede
                                                                                     //elimino l'immagine del cliente
                   //this.listaTavoli.get(tableNumber-1).setState(stateCharacter.OCCUPIED);                                               //occupo il tavolo
                   //this.listaTavoli.get(tableNumber-1).setSitted_customers(numCustom);                             //inserisco il numero di clienti al tavolo
                    state = CustomerState.THINKING;
                    this.setActive(false);                                              //cliente pensa, quindi la sua immagine deve sparire
                    
            }     
        }
        else if(state.equals(CustomerState.THINKING))                                          //il cliente pensa a cosa ordinare
        {
                this.startThinkingTimer();
                //this.listaTavoli.get(tableNumber-1).setState(stateCharacter.IS_ORDERING);
        }
    }
    public void up() {
        
        this.setPosition(new Pair<Integer,Integer>(this.getPosition().getX(), this.getPosition().getY()-MOVEMENT_DISTANCE)); 
    }
    
    
    public void down() {
        this.setPosition(new Pair<Integer,Integer>(this.getPosition().getX(), this.getPosition().getY()+MOVEMENT_DISTANCE)); 
    }
    
    
    public void right() {
        this.setPosition(new Pair<Integer,Integer>(this.getPosition().getX()+MOVEMENT_DISTANCE, this.getPosition().getY()));
    }
    
    
    public void left() {
        this.setPosition(new Pair<Integer,Integer>(this.getPosition().getX()-MOVEMENT_DISTANCE, this.getPosition().getY()));
    }

    
}
