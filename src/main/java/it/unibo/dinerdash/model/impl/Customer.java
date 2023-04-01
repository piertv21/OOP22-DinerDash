package it.unibo.dinerdash.model.impl;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends GameEntityImpl {

    private static final int MOVEMENT_DISTANCE = 5;
    private static final int MIN_EAT_TIME = 4000;
    private static final int MAX_EAT_TIME = 10000;
    private static final int STARTING_X = 0;
    private static final int STARTING_Y = 500;
    private static final int TIME_BEFORE_GETANGRY = 8000;
    private static final int TIME_BEFORE_ORDERING = 4000;
    
    private int numClienti;                     // molteplicità clienti (1 - 4)
    private Timer timerActions = new Timer();
    private CustomerState state;
    private int lineNumber;                                             //number of the person in line
    private ModelImpl model;
    
    private enum CustomerState {
        WAITING,
        ANGRY,
        WALKING,
        THINKING,
        ORDERING
    }
    
    public Customer(Pair<Integer, Integer> coordinates, ModelImpl model) {
        super(coordinates);
        this.model = model;
    }  

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int num) {
         this.lineNumber = num;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }

    public void startAngryTimer(){                                   //avvia il timer per far arrabbiare i clienti in fila
        //timerActions.schedule(angryAction, TIME_BEFORE_GETANGRY, TIME_BEFORE_GETANGRY);
    }

    public void startThinkingTimer(){                                   //avvia il timer per far pensare un clinete seduto
        timerActions.schedule(ThinkingAction, TIME_BEFORE_ORDERING, TIME_BEFORE_ORDERING);
    }

    //TODO Aggiorna
    /* TimerTask angryAction = new TimerTask() {                        //azione programmata per gestire il cliente arrabbiato
        @Override
        public void run() { 
            if(state.equals(CustomerState.ANGRY)) {
                model.getCustomersLineList().removeFirst();                                   // QUI TOLGO IL cliente arrabbiato
                model.getCustomersLineList().forEach(x->{
                    x.setPosition(new Pair<Integer,Integer>(model.getCustomersLinePositionList().get(x.getLineNumber()-1).getX(),
                    model.getCustomersLinePositionList().get(x.getLineNumber()-1).getY()));
                    x.setLineNumber(x.getLineNumber()-1);
                });
                angryAction.cancel();
            }             
           setState(CustomerState.ANGRY);  
        }
    }; */

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
