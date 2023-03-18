package it.unibo.dinerdash.model.impl;

import java.util.Optional;

import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread
 */
public class Table extends GameEntity {

    private int tableNumber;
    private int peopleSeatedNumber;
    private boolean isAvailable;

    public Table(Pair<Integer, Integer> coordinates) {
        super(new Pair<Integer,Integer>(550, 148));
        // TODO Auto-generated method stub
    }

    public Optional<Pair<Integer, Integer>> getPosition() {
        return this.getPosition();
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.setPosition(position);
    }
}
