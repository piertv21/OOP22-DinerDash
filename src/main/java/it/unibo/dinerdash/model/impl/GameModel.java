package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;

/*
 * Solo metodi getter e setter sulle entità model
 */
public class GameModel {
    
    private int coins;
    private int remainingTime;
    private LinkedList customers;   // clienti (vengono rappresentati correttamente in base alla posizione + state che hanno)
    private LinkedList tables;      // tavoli (vengono rappresentati correttamente in base alla posizione + icona che hanno)
    private LinkedList dishes;      // lista piatti pronti dello chef

    public GameModel() {
    }

}
