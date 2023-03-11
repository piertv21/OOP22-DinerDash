package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread, therefore doesn't depend on gameentity
 */
public class Table extends GameEntity {

    private ImageIcon icon;
    private Pair<Integer, Integer> coordinates;
    private int tableNumber;
    private int peopleSeatedNumber;
    private boolean isAvailable;

    public Table(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        super(coordinates, icon);
        //TODO Auto-generated constructor stub
    }
}
