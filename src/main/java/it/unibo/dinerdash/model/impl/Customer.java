package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends GameEntity implements Runnable {

    private static final int STARTING_X = 500; // da modificare
    private static final int STARTING_Y = 500; // da modificare
    private static final int TIME_BEFORE_GETANGRY = 8000;
    
    // private int x = 0, y = STARTING_Y, varX = 0, varY = 0;
    // private Thread trd;
    //private Timer timerAngry = new Timer();
    private int tableNumber;
    private int numClienti;                     // molteplicità clienti (1 - 4)
    private Pair<Integer, Integer> destination; // destinazione da settare per movimento (posizione già presente in gameentity)

    enum CustomerState {
        WAITING,
        ANGRY,
        WALKING,
        THINKING,
        ORDERING,
        WAITING_MEAL,
        EATING,
        WANTING_TO_PAY,
        LEAVE
    }
    
    /*
    private static final int STARTING_TABLE = 0;
    private LinkedList<Pair<Integer, Integer>> ordersList;
    private HashMap<Integer, Pair<Integer, Integer>> tablesMap;
    private LinkedList<Table> listaTavoli;                              //lista contentenente i tavoli
    private LinkedList<Customer> customersWaitingInLine;                  //list of customers in line waiting
    private LinkedList<Pair<Integer, Integer>> waitingLineCoordinates;
    private int numberOfCustom;
    private Image custumersImage; 
    private int lineNumber;                                             //number of the person in line
    private int tableNumber = STARTING_TABLE;                         
    */
    
    public Customer(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        super(coordinates, icon);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    /*
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    */
}
