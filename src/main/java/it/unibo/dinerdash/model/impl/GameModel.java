package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class GameModel {

    private static final int MAX_CUSTOMERS_THAT_CAN_LEAVE = 10;
    private static final double CHEF_SPEED_MULTIPLIER = 1.2;
    private static final double WAITRESS_SPEED_MULTIPLIER = 1.5;
    private static final double PROFIT_MULTIPLIER = 2.0;
    
    private int coins;
    private int remainingTime;
    private int customersWhoLeft;
    private LinkedList<Customer> customers;   // clienti (vengono rappresentati correttamente in base alla posizione + state che hanno)
    private LinkedList<Table> tables;      // tavoli (vengono rappresentati correttamente in base alla posizione + icona che hanno)
    private LinkedList<Dish> dishes;      // lista piatti pronti dello chef

    public GameModel() {
    }
    
    public void serveDish() {
        //TO-DO
    }

    public void insertOrder(int tableNumber) {
        var position = new Pair<>(0, 0);
        this.dishes.add(new Dish(position, tableNumber));
        //TO-DO
    }

    public boolean gameOver() {
        return this.remainingTime == 0 ||
            this.customersWhoLeft == MAX_CUSTOMERS_THAT_CAN_LEAVE;
    }

    private void init() {
        //TO-DO
        // inizializza le strutture
    }

    public void restart() {
        //TO-DO
        // clear delle strutture + init()
    }

    public void quit() {
        //TO-DO
        // pulizia e chiusura
    }

}
