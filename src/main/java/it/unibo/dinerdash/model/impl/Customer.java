package it.unibo.dinerdash.model.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.awt.Image;

import it.unibo.dinerdash.utility.impl.Pair;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer extends GameEntity {

    private static final int STARTING_Y = 500;
    
    private int x = 0, y = STARTING_Y, varX = 0, varY = 0;
    private Thread trd;
    private Timer timerAngry = new Timer();
    private static final int TIME_BEFORE_GETANGRY = 8000;
    private LinkedList<Pair<Integer, Integer>> ordersList;
    private HashMap<Integer, Pair<Integer, Integer>> tablesMap;
    //private LinkedList<Table>listaTavoli;                              //lista contentenente i tavoli
    private LinkedList<Customer> customersWaitingInLine;                  //list of customers in line waiting
    private LinkedList<Pair<Integer, Integer>> waitingLineCoordinates;
    private int numberOfCustom;
    private Image custumersImage; 
    private int lineNumber;                                             //number of the person in line

    /*
     * Create a new customer with initial coordinates passed by Pair and Image selected by path
     */
    public Customer(Pair<Integer, Integer> coordinates, String path) {
        super(coordinates, path);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
