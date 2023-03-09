package it.unibo.dinerdash.model.impl;

/** 
 * Create a new element "Customer" who will move in the restaurant
 */
public class Customer implements Runnable {
    private final int STARTING_Y = 500;
    private int x = 0, y = STARTING_Y, varX = 0, varY = 0;
    private Thread trd;

    public Customer() {
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    
}
