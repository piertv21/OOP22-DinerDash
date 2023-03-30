package it.unibo.dinerdash.model.impl;

import it.unibo.dinerdash.model.api.GameEntityImpl;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * Table is not a thread
 */
public class Table extends GameEntityImpl {

    private int tableNumber;
    private int peopleSeatedNumber; // Represents correct image number
    private boolean available;
    private TableState state;

    private enum TableState {
        THINKING,
        ORDERING,
        WAITING_MEAL,
        EATING,
        WANTING_TO_PAY,
        EMPTY
    }

    public Table(Pair<Integer, Integer> coordinates) {
        super(coordinates);
        // TODO Auto-generated method stub
    }

    public int getTableNumber() {
        return this.tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getPeopleSeatedNumber() {
        return this.peopleSeatedNumber;
    }

    public void setPeopleSeatedNumber(int peopleSeatedNumber) {
        this.peopleSeatedNumber = peopleSeatedNumber;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }  

    public void setState(TableState TableState){                                     
        this.state = TableState;
    }

    public TableState getState(){                                     
        return this.state;
    }
    
}
