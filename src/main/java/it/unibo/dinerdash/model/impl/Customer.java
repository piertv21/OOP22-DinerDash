package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends GameEntity {

    private static final int STARTING_X = 500; // da modificare
    private static final int STARTING_Y = 500; // da modificare
    private static final int TIME_BEFORE_GETANGRY = 8000;
    
    private Timer timerAngry = new Timer();
    private int tableNumber;
    private int numClienti;                     // molteplicità clienti (1 - 4)
    private Pair<Integer, Integer> destination; // destinazione da settare per movimento (posizione già presente in gameentity)
    private CustomerState state = CustomerState.WALKING;
    private LinkedList<Customer> customersWaitingInLine;                  //list of customers in line waiting
    private LinkedList<Pair<Integer,Integer>>waitingLineCoordinates;      // list of coordinates of the line customers
    private int lineNumber;                                             //number of the person in line
    
    enum CustomerState {
        WAITING,
        ANGRY,
        WALKING,
        THINKING,
        ORDERING,
       // WAITING_MEAL,
        //EATING,
        //WANTING_TO_PAY,
       // LEAVE
    }
    
    /*
    private static final int STARTING_TABLE = 0;
    private LinkedList<Pair<Integer, Integer>> ordersList;
    private HashMap<Integer, Pair<Integer, Integer>> tablesMap;
    private LinkedList<Table> listaTavoli;                              //lista contentenente i tavoli
    private int numberOfCustom;
    private Image custumersImage; 
    private int tableNumber = STARTING_TABLE;                         
    */
    
    public Customer(Pair<Integer, Integer> coordinates) {
        super(coordinates);
        //TODO Auto-generated constructor stub
    }

    

    public int getLineNumber() {
        return this.lineNumber;
    }
    public void setLineNumber(int num) {
         this.lineNumber = num;
    }

    public void setState(CustomerState state) {
        this.state=state;
    }

    public void startAngryTimer(){                                   //avvia il timer per far arrabbiare i clienti in fila
        timerAngry.schedule(angryAction, TIME_BEFORE_GETANGRY, TIME_BEFORE_GETANGRY);
    }

    TimerTask angryAction= new TimerTask() {                        //azione programmata per gestire il cliente arrabbiato
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
   
}
