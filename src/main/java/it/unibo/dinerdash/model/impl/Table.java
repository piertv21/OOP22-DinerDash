package it.unibo.dinerdash.model.impl;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread, therefore doesn't depend on gameentity
 */
public class Table extends GameEntity {

    //private ImageIcon icon;
    //private Pair<Integer, Integer> coordinates;
    private int tableNumber;
    private int peopleSeatedNumber;
    private boolean isAvailable;

    //private int x, y;

    public Table(Pair<Integer, Integer> coordinates, ImageIcon icon) {
        super(new Pair<Integer,Integer>(550, 148), icon);
        //this.x=coordinates.getX();
        //this.y=coordinates.getY();
    }

    public Pair<Integer, Integer> getPosition() {
        return this.getPosition();
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.setPosition(position);
    }
}
