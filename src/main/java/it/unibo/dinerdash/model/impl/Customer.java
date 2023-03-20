package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends GameEntity {

    private static final int MOVEMENT_DISTANCE = 5;
    private static final int MIN_EAT_TIME = 4000;
    private static final int MAX_EAT_TIME = 10000;
    private static final int STARTING_X = 0;
    private static final int STARTING_Y = 500;
    private static final int TIME_BEFORE_GETANGRY = 8000;

    private int tableNumber;
    private int numClienti;                     // molteplicità clienti (1 - 4)
    private Timer timerAngry = new Timer();
    private CustomerState state;
    private LinkedList<Customer> customersWaitingInLine;                  //list of customers in line waiting
    private LinkedList<Pair<Integer,Integer>> waitingLineCoordinates;      // list of coordinates of the line customers
    private int lineNumber;                                             //number of the person in line
    
    private enum CustomerState {
        WAITING,
        ANGRY,
        WALKING,
        THINKING,
        ORDERING
    }
    
    public Customer(Pair<Integer, Integer> coordinates,int tableNum,LinkedList<Customer> customersInLine,LinkedList<Pair<Integer,Integer>> waitingLineCoordinate) {
        super(coordinates);
        this.tableNumber = tableNum;
        this.customersWaitingInLine = customersInLine;
        this.waitingLineCoordinates = waitingLineCoordinate;
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
        timerAngry.schedule(angryAction, TIME_BEFORE_GETANGRY, TIME_BEFORE_GETANGRY);
    }

    TimerTask angryAction = new TimerTask() {                        //azione programmata per gestire il cliente arrabbiato
        @Override
        public void run() { 
            if(state.equals(CustomerState.ANGRY)){
                customersWaitingInLine.removeFirst();                                   // QUI TOLGO IL cliente arrabbiato
                customersWaitingInLine.forEach(x->{
                    x.setPosition(new Pair<Integer,Integer>(waitingLineCoordinates.get(x.getLineNumber()-1).getX(),waitingLineCoordinates.get(x.getLineNumber()-1).getY()));
                    x.setLineNumber(x.getLineNumber()-1);
                });
            angryAction.cancel();
            }             
           setState(CustomerState.ANGRY);  
    
        }
    };

    public void handleMovement() {
        //TO-DO
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